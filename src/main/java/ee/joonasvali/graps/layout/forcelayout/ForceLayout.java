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
	private final static double STABLE = 1, DAMPING = 0.70, STRING_STRENGTH = 0.06, COLOUMB = 200;
	private Executor executor = Executors.newSingleThreadExecutor();
	
	public ForceLayout(Graph graph){		
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
				if(node.getForeignNodes().size() == 0)
					continue;
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
			
		} while(kinetic.getAbsolute() > STABLE);
		//System.out.println(nodes.get(0).getNode().getLocation());
		//nodes.remove(center);
	}
	
	private Force hookeAttraction(PhysicalNode node, Node other) {		
		double xdiff = (other.getLocation().x - node.getNode().getLocation().x);
		double ydiff = (other.getLocation().y - node.getNode().getLocation().y);	  
	  return new Force(xdiff*STRING_STRENGTH, ydiff*STRING_STRENGTH);
  }
	
	private Force hookeAttraction(PhysicalNode node, PhysicalNode other) {		
		return hookeAttraction(node, other.getNode());
  }

	private Force coulombRepulsion(PhysicalNode node, PhysicalNode other) {
		double xdiff = node.getNode().getLocation().x - other.getNode().getLocation().x;
		double ydiff = node.getNode().getLocation().y - other.getNode().getLocation().y;
		double sqrdistance = xdiff*xdiff + ydiff * ydiff;
		if(sqrdistance == 0){
			return new Force(10,10);
		}		
		return new Force((COLOUMB * (xdiff / sqrdistance)), (COLOUMB * (ydiff / sqrdistance)));
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
			super(new Node(new Point(250, 250), new Point(20,20)){
				@Override
				public Point getLocation() {				  
				  return new Point(250, 250);
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
