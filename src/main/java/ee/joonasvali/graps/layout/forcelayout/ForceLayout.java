package ee.joonasvali.graps.layout.forcelayout;

import java.awt.Point;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.Layout;
import ee.joonasvali.graps.simulator.DummyNode;
import ee.joonasvali.graps.util.ClickableEdgeUtil;
import ee.joonasvali.graps.util.FlagManager;
import ee.joonasvali.graps.util.GraphUtil;

public class ForceLayout implements Layout {
	private LinkedList<PhysicalNode> nodes = new LinkedList<PhysicalNode>();
	private LinkedList<UpdateListener> listeners = new LinkedList<UpdateListener>();	
	private Executor executor = Executors.newSingleThreadExecutor();
	private boolean run;
	private Point offset = new Point(0, 0);
	private ForceLayoutConfiguration configuration;
	private final LinkedList<Runnable> todo = new LinkedList<Runnable>();
	private ExecutorService workers = Executors.newCachedThreadPool();
	
	public ForceLayout(){
		this.configuration = new ForceLayoutConfiguration();			
	}
	
	public ForceLayout(ForceLayoutConfiguration configuration){
		this.configuration = configuration;
	}
	
	public void addListener(UpdateListener listener) {
		listeners.add(listener);
	}

	public void removeListener(UpdateListener listener) {
		listeners.remove(listener);
	}

	public void execute(Graph graph) {
		run = true;
		nodes.clear();
		for (Node n : graph.getNodes()) {
			nodes.add(new PhysicalNode(n));
		}

		executor.execute(new Runnable() {
			public void run() {
				place();
			}
		});
	}

	private void place() {	
		do {
			while(configuration.isPause()){
				try {
	        TimeUnit.MILLISECONDS.sleep(configuration.getPauseReactionTime());
        }
        catch (InterruptedException ignore) { }
			}
			
			synchronized(todo){
				while(todo.size() > 0){
					todo.removeFirst().run();
				}
			}
			
			final double damping = configuration.getDamping();			
			final CountDownLatch latch = new CountDownLatch(nodes.size());			
			
			for (final PhysicalNode node : nodes) {
				boolean exclude = FlagManager.getInstance(Node.class).get(node.getNode(), EXCLUDE);
				if (exclude) {
					latch.countDown();
					continue;					
				}				
				
				workers.execute(
					new Runnable(){
						public void run() {
							Force netForce = new Force();
							for (PhysicalNode other : nodes) {
								
								if (!DummyNode.isDummyNode(other.getNode()) && !node.equals(other)) {
									netForce.add(coulombRepulsion(node, other));
								}
							}
	
							for (Node other : node.getForeignNodes()) {
								netForce.add(hookeAttraction(node, other));
							}
							
							if(configuration.centerForcePullStrength() > 0){
								netForce.add(centerPullForce(node));
							}
							
							node.getVelocity().x = (node.getVelocity().x + (netForce.x)) * damping;
							node.getVelocity().y = (node.getVelocity().y + (netForce.y)) * damping;
							latch.countDown();
	          }					
					}
				);
			}
			
			try {
	      latch.await();
      }
      catch (InterruptedException e1) {      	
	      e1.printStackTrace();
	      System.exit(1);
      }
			
			for (PhysicalNode node : nodes) {
				boolean exclude = FlagManager.getInstance(Node.class).get(
				    node.getNode(), EXCLUDE);
				if (exclude) {
					continue;
				}

				node.setLocation(new Point((int) (node.getNode().getLocation().x
				    + node.getVelocity().x + offset.x), (int) (node.getNode()
				    .getLocation().y + node.getVelocity().y + offset.y)));
			}
			
			int sleep = configuration.getSleepTimeBetweenIterations();
			if(sleep > 0){				
				try {
	        TimeUnit.MILLISECONDS.sleep(sleep);
        }
        catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }				
			}

			int edgeMargins = configuration.getEdgeMargins();
			Point minvals = GraphUtil.calculateMinPosition(nodes);
			offset.x = -(minvals.x) + edgeMargins;
			offset.y = -(minvals.y) + edgeMargins;
			notifyListeners();
		}
		while (run);
	}

	private Force centerPullForce(PhysicalNode node) {
		double xdiff = node.getLocation().x - 200;
		double ydiff = node.getLocation().y - 200;
		double centerPull = configuration.centerForcePullStrength();
	  return new Force(xdiff < 0 ? centerPull : -centerPull, ydiff < 0 ? centerPull : -centerPull);
  }

	public Point getOffset() {
		return offset;
	}
	
	/**
	 * Add task to do before next iteration. For example renew configuration.
	 * Thread safe.
	 * @param runnable the task to be fulfilled by the looping thread.
	 */
	public void addBeforeNextIteration(Runnable runnable){
		synchronized(todo){
			todo.add(runnable);
		}
	}
	
	public ForceLayoutConfiguration getConfiguration(){
		return configuration;
	}

	private void notifyListeners() {
		for (UpdateListener l : listeners) {
			l.update();
		}
	}

	private Force hookeAttraction(PhysicalNode node, Node other) {
		return hookeAttraction(node, other, configuration.getStringStrength());
	}
	
	private Force hookeAttraction(PhysicalNode node, Node other, double strength) {
		Point edgePosNode = ClickableEdgeUtil.edgeFor(node.getNode(), other);
		Point edgePosOther = ClickableEdgeUtil.edgeFor(other, node.getNode());
		double xdiff = (edgePosOther.x - edgePosNode.x);
		double ydiff = (edgePosOther.y - edgePosNode.y);
		return new Force(xdiff * strength, ydiff * strength);
	}

	private Force hookeAttraction(PhysicalNode node, PhysicalNode other) {
		return hookeAttraction(node, other.getNode());
	}
	
	private Force hookeAttraction(PhysicalNode node, PhysicalNode other, double strength) {
		return hookeAttraction(node, other.getNode(), strength);
	}

	private Force coulombRepulsion(PhysicalNode node, PhysicalNode other) {
		if (node.equals(other)) return new Force(0, 0);
		Point edgePosNode = ClickableEdgeUtil.edgeFor(node.getNode(),
		    other.getNode());
		Point edgePosOther = ClickableEdgeUtil.edgeFor(other.getNode(),
		    node.getNode());
		double xdiff = edgePosNode.x - edgePosOther.x;
		double ydiff = edgePosNode.y - edgePosOther.y;
		
		double coloumbMax = configuration.getCoulombForceMaxDistance();		
		if(coloumbMax > 0 && (xdiff > coloumbMax || ydiff > coloumbMax)){
			return new Force(0,0);
		}
		double sqrdistance = xdiff * xdiff + ydiff * ydiff;
		if (sqrdistance == 0) {
			return new Force(Math.random() - 0.5d, Math.random() - 0.5d);
		}

		double massMultiplier = Math.max(node.getMass() * other.getMass()
		    * configuration.getMassConstant(), 1);
		double coulomb = configuration.getCoulombRepulseStrength();
		return new Force((massMultiplier * coulomb * (xdiff / sqrdistance)),
		    (massMultiplier * coulomb * (ydiff / sqrdistance)));
	}

	public void stop() {
		run = false;
	}	
}
