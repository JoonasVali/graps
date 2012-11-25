package ee.joonasvali.graps.layout.forcelayout;

import java.awt.Point;
import java.util.LinkedList;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.Layout;

public class ForceLayout implements Layout{	
	private LinkedList<PhysicalNode> nodes = new LinkedList<PhysicalNode>();
	private final static double STABLE = 0.5d, DAMPING = 0.2, TIMESTEP = 1, STRING_EQUILIBRIUM = 0.5, STRING_STRENGTH = 1;
	
	public ForceLayout(Graph graph){		
		for(Node n : graph.getNodes()){
			nodes.add(new PhysicalNode(n));
		}
		place();
	}
	
	public void place(){
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
				node.getVelocity().x += (TIMESTEP * netForce.x);
				node.getVelocity().x *= DAMPING;		
				node.getVelocity().y += (TIMESTEP * netForce.y);
				node.getVelocity().y *= DAMPING;
				node.getNode().setLocation(new Point(
					(int)(node.getNode().getLocation().x + TIMESTEP * node.getVelocity().x), 
					(int)(node.getNode().getLocation().y + TIMESTEP * node.getVelocity().y)	
				));
				kinetic.add(
						new Force(node.getMass() * Math.pow(node.getVelocity().x, 2),
						node.getMass() * Math.pow(node.getVelocity().y, 2))
				);
			}		
			try {
	      Thread.sleep(1000);
      }
      catch (InterruptedException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
			System.out.println(kinetic.getAbsolute());
		} while(kinetic.getAbsolute() > STABLE);		
	}
	
	private Force hookeAttraction(PhysicalNode node, Node other) {		
		double xdiff = (node.getNode().getLocation().x - other.getLocation().x) - STRING_EQUILIBRIUM;
		double ydiff = (node.getNode().getLocation().y - other.getLocation().y) - STRING_EQUILIBRIUM;	  
	  return new Force(-xdiff*STRING_STRENGTH, -ydiff*STRING_STRENGTH);
  }

	private Force coulombRepulsion(PhysicalNode node, PhysicalNode other) {
		// Simplified
		double k = 0.002;
		double xdiff = node.getNode().getLocation().x - other.getNode().getLocation().x;
		double ydiff = node.getNode().getLocation().y - other.getNode().getLocation().y;
		return new Force(k / xdiff, k/ydiff );
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
	
	
	
}
