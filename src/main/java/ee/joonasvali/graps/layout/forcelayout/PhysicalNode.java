package ee.joonasvali.graps.layout.forcelayout;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ee.joonasvali.graps.graph.Clickable;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class PhysicalNode implements Clickable{
	private Force velocity = new Force(0,0);
	private Node node;
	private double mass;
	private Collection<Node> foreignNodes;
	
	public PhysicalNode(Node node){		
		this.node = node;
		foreignNodes = createForeignNodes();		
		this.mass = Math.max(0.1, foreignNodes.size())* 10;
	}	

	public Force getVelocity() {
  	return velocity;
  }

	public void setVelocity(Force velocity) {
  	this.velocity = velocity;
  }

	public Node getNode() {
  	return node;
  }
	
	public Collection<Node> getForeignNodes(){
		return new ArrayList<Node>(foreignNodes);
	}
	
	private Collection<Node> createForeignNodes(){
		Set<Node> set = new HashSet<Node>();		
		for(Port p : node.getPorts()){			
			if(p.getNode() != null && p.getPort() != null && p.getPort().getNode() != null){
				if(!p.getPort().getNode().equals(node)){					
					set.add(p.getPort().getNode());
				} 
			} 
		}
		return set;	
	}

	public double getMass() {	 
	  return mass;
  }
	
	public double distance(PhysicalNode other){
		return 
				Math.sqrt(
					Math.pow(other.getNode().getLocation().x - node.getLocation().x, 2) +
					Math.pow(other.getNode().getLocation().y - node.getLocation().y, 2)
				);
	}

	public int getWidth() {	  
	  return node.getWidth();
  }

	public int getHeight() {	  
	  return node.getHeight();
  }

	public Point getLocation() {	  
	  return node.getLocation();
  }

	public Point getCenter() {	  
	  return node.getCenter();
  }

	public void setLocation(Point location) {
	  node.setLocation(location);	  
  }
}
