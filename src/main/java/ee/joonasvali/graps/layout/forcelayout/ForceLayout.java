package ee.joonasvali.graps.layout.forcelayout;

import java.awt.Point;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.Layout;

public class ForceLayout implements Layout{	
	private LinkedList<PhysicalNode> nodes = new LinkedList<PhysicalNode>();
	private final static double STABLE = 15, DAMPING = 0.80, STRING_STRENGTH = 0.06, COLOUMB = 200;
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
				TimeUnit.MILLISECONDS.sleep(60);
			} catch(Exception e){
				System.err.println(e);
			}			
		} while(/*kinetic.getAbsolute() > STABLE*/ true);
	}
	
	private Force hookeAttraction(PhysicalNode node, Node other) {		
		double xdiff = (other.getCenter().x - node.getNode().getCenter().x);
		double ydiff = (other.getCenter().y - node.getNode().getCenter().y);	  
	  return new Force(xdiff*STRING_STRENGTH, ydiff*STRING_STRENGTH);
  }
	
	private Force hookeAttraction(PhysicalNode node, PhysicalNode other) {		
		return hookeAttraction(node, other.getNode());
  }

	private Force coulombRepulsion(PhysicalNode node, PhysicalNode other) {
		double xdiff = node.getNode().getCenter().x - other.getNode().getCenter().x;
		double ydiff = node.getNode().getCenter().y - other.getNode().getCenter().y;
		double sqrdistance = xdiff*xdiff + ydiff * ydiff;
		if(sqrdistance == 0){
			return new Force(1,1);
		}		
		
		double massMultiplier = Math.max(node.getMass() * other.getMass() * 0.0000005d, 1);		
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
		  return 13000;
		} 

		
		
	}
}
