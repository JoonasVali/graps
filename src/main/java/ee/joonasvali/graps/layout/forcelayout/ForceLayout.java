package ee.joonasvali.graps.layout.forcelayout;

import java.awt.Point;
import java.util.LinkedList;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.Layout;

public class ForceLayout implements Layout{	
	private LinkedList<PhysicalNode> nodes = new LinkedList<PhysicalNode>();
	private final static double STABLE = 0.1d, DAMPING = 0.85, STRING_STRENGTH = 0.06;
	
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
				
				node.getVelocity().x = (node.getVelocity().x +(netForce.x)) * DAMPING;
				node.getVelocity().y = (node.getVelocity().y +(netForce.y)) * DAMPING;				
			}	
			for(PhysicalNode node: nodes){
				node.getNode().setLocation(new Point(
						(int)(node.getNode().getLocation().x + node.getVelocity().x), 
						(int)(node.getNode().getLocation().y + node.getVelocity().y)	
				));
							
				kinetic.add(
					new Force(
						node.getMass() * Math.pow(node.getVelocity().x, 2),
						node.getMass() * Math.pow(node.getVelocity().y, 2)
					)
				);
			}			
			
		} while(kinetic.getAbsolute() > STABLE);
		System.out.println(nodes.get(0).getNode().getLocation());
	}
	
	private Force hookeAttraction(PhysicalNode node, Node other) {		
		double xdiff = (node.getNode().getLocation().x - other.getLocation().x);
		double ydiff = (node.getNode().getLocation().y - other.getLocation().y);	  
	  return new Force(xdiff*STRING_STRENGTH, ydiff*STRING_STRENGTH);
  }

	private Force coulombRepulsion(PhysicalNode node, PhysicalNode other) {
		// Simplified
		double k = 200;		
		double xdiff = node.getNode().getLocation().x - other.getNode().getLocation().x;
		double ydiff = node.getNode().getLocation().y - other.getNode().getLocation().y;
		double sqrdistance = xdiff*xdiff - ydiff * ydiff;
		return new Force((k * xdiff / sqrdistance), (k * ydiff / sqrdistance));
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
