package ee.joonasvali.graps.layout.forcelayout;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.Layout;
import ee.joonasvali.graps.util.ClickableEdgeUtil;
import ee.joonasvali.graps.util.FlagManager;

public class ForceLayout implements Layout{	
	public final static String EXCLUDE = "exclude_node"; 
	private LinkedList<PhysicalNode> nodes = new LinkedList<PhysicalNode>();	
	private final static double STABLE = 15, DAMPING = 0.65, STRING_STRENGTH = 0.06, COLOUMB = 200;
	private Executor executor = Executors.newSingleThreadExecutor();
	private Point center;
	
	public ForceLayout(Graph graph, Point center){
		this.center = center;
		for(Node n : graph.getNodes()){
			nodes.add(new PhysicalNode(n));
		}
		
		executor.execute(new Runnable() {			
			public void run() {
				place();				
			}
		});
	}
	
	public void place(){		
		VirtualGravityCenter center = new VirtualGravityCenter();		
		Force kinetic;
		do{
			kinetic = sumKinetic();
			for(PhysicalNode node: nodes){
				boolean exclude = FlagManager.getInstance(Node.class).get(node.getNode(), EXCLUDE);
				if(exclude){
					continue;
				}
				
				Force netForce = new Force();
				for(PhysicalNode other: nodes){					
					if(!node.equals(other)){
						netForce.add(coulombRepulsion(node, other));						
					}
				}
				
				for(Node other : node.getForeignNodes()){
					netForce.add(hookeAttraction(node, other));				
				}				
				
				netForce.add(hookeAttraction(node, center));					
							
				node.getVelocity().x = (node.getVelocity().x +(netForce.x)) * DAMPING;
				node.getVelocity().y = (node.getVelocity().y +(netForce.y)) * DAMPING;			
			}	
			for(PhysicalNode node: nodes){
				boolean exclude = FlagManager.getInstance(Node.class).get(node.getNode(), EXCLUDE);
				if(exclude){
					continue;
				}
				
				node.getNode().setLocation(new Point(
						(int)(node.getNode().getLocation().x + node.getVelocity().x), 
						(int)(node.getNode().getLocation().y + node.getVelocity().y)	
				));
							
				kinetic.add(new Force(
					node.getMass() * Math.pow(node.getVelocity().x, 2),
					node.getMass() * Math.pow(node.getVelocity().y, 2)
				));				
			}			
			
			try{
				TimeUnit.MILLISECONDS.sleep(20);
			} catch(Exception e){
				System.err.println(e);
			}			
		} while(/*kinetic.getAbsolute() > STABLE*/ true);
	}
	
	private Force hookeAttraction(PhysicalNode node, Node other) {		
		Point edgePosNode = ClickableEdgeUtil.edgeFor(node.getNode(), other);
		Point edgePosOther = ClickableEdgeUtil.edgeFor(other, node.getNode());
		double xdiff = (edgePosOther.x - edgePosNode.x);
		double ydiff = (edgePosOther.y - edgePosNode.y);	  
	  return new Force(xdiff*STRING_STRENGTH, ydiff*STRING_STRENGTH);
  }
	
	private Force hookeAttraction(PhysicalNode node, PhysicalNode other) {		
		return hookeAttraction(node, other.getNode());
  }

	private Force coulombRepulsion(PhysicalNode node, PhysicalNode other) {
		Point edgePosNode = ClickableEdgeUtil.edgeFor(node.getNode(), other.getNode());
		Point edgePosOther = ClickableEdgeUtil.edgeFor(other.getNode(), node.getNode());
		double xdiff = edgePosNode.x - edgePosOther.x;
		double ydiff = edgePosNode.y - edgePosOther.y;
		double sqrdistance = xdiff*xdiff + ydiff * ydiff;
		if(sqrdistance == 0){
			return new Force(1,1);
		}		
		
		double massMultiplier = Math.max(node.getMass() * other.getMass() * 0.005d, 1);		
		return new Force((massMultiplier * COLOUMB * (xdiff / sqrdistance)), (massMultiplier * COLOUMB * (ydiff / sqrdistance)));
  }	

	private Force sumKinetic() {
		Force kinetic = new Force(0,0);
	  for(PhysicalNode n: nodes){
	  	kinetic.add(n.getVelocity());
	  }
	  return kinetic;
  }

	public Point getPosition(Node node) {	  
	  return node.getLocation();
  }
	
	
	class VirtualGravityCenter extends PhysicalNode{
		
		
		public VirtualGravityCenter() {
			super(new Node(center, new Point(20,20)){
				@Override
				public Point getLocation() {				  
				  return center;
				}
			});
    }
		
		@Override
		public void setVelocity(Force velocity) {	}
		
		@Override
		public double getMass() {		  
		  return 5;
		} 

		
		
	}
}
