package ee.joonasvali.graps.layout.forcelayout;

import java.awt.Point;
import java.util.LinkedList;
import java.util.concurrent.Executor;
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
	private final static double DAMPING = 0.65, STRING_STRENGTH = 0.15,
	    COLOUMB = 100, MASS_CONSTANT = 0.003d;
	private Executor executor = Executors.newSingleThreadExecutor();
	private boolean run;
	private Point offset = new Point(0, 0);

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
		VirtualGravityCenter center = new VirtualGravityCenter();
		do {
			for (PhysicalNode node : nodes) {
				boolean exclude = FlagManager.getInstance(Node.class).get(
				    node.getNode(), EXCLUDE);
				if (exclude) {
					continue;
				}

				Force netForce = new Force();
				for (PhysicalNode other : nodes) {
					
					if (!DummyNode.isDummyNode(other.getNode()) && !node.equals(other)) {
						netForce.add(coulombRepulsion(node, other));
					}
				}

				for (Node other : node.getForeignNodes()) {
					netForce.add(hookeAttraction(node, other));
				}
				
				netForce.add(hookeAttraction(node, center, 0.001));
				
				node.getVelocity().x = (node.getVelocity().x + (netForce.x)) * DAMPING;
				node.getVelocity().y = (node.getVelocity().y + (netForce.y)) * DAMPING;
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

			try {
				TimeUnit.MILLISECONDS.sleep(20);
			}
			catch (Exception e) {
				System.err.println(e);
			}

			Point minvals = GraphUtil.calculateMinPosition(nodes);
			offset.x = -(minvals.x) + 20;
			offset.y = -(minvals.y) + 20;
			notifyListeners();
		}
		while (run);
	}

	public Point getOffset() {
		return offset;
	}

	private void notifyListeners() {
		for (UpdateListener l : listeners) {
			l.update();
		}
	}

	private Force hookeAttraction(PhysicalNode node, Node other) {
		return hookeAttraction(node, other, STRING_STRENGTH);
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
		if(xdiff > 200 || ydiff > 200){
			return new Force(0,0);
		}
		double sqrdistance = xdiff * xdiff + ydiff * ydiff;
		if (sqrdistance == 0) {
			return new Force(Math.random() - 0.5d, Math.random() - 0.5d);
		}

		double massMultiplier = Math.max(node.getMass() * other.getMass()
		    * MASS_CONSTANT, 1);
		return new Force((massMultiplier * COLOUMB * (xdiff / sqrdistance)),
		    (massMultiplier * COLOUMB * (ydiff / sqrdistance)));
	}

	public void stop() {
		run = false;
	}

	class VirtualGravityCenter extends PhysicalNode {

		public VirtualGravityCenter() {
			super(new Node(new Point(100, 100), new Point(1, 1)) {
				@Override
				public Point getLocation() {
					return new Point(100, 100);
				}
			});
		}
		
		public double getMass(){
			return 200;
		}
	}
}
