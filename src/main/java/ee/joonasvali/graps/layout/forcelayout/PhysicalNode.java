package ee.joonasvali.graps.layout.forcelayout;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class PhysicalNode {
	private Force velocity = new Force(0,0);
	private Node node;
	
	public PhysicalNode(Node node){
		this.node = node;
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
		Set<Node> set = new HashSet<Node>();
		set.clear();
		for(Port p : node.getPorts()){				
			if(!p.getPort().getNode().equals(node)){					
				set.add(p.getPort().getNode());
			}
		}
		return set;	
	}

	public double getMass() {	 
	  return 2;
  }	
	
	public double distance(PhysicalNode other){
		return 
				Math.sqrt(
					Math.pow(other.getNode().getLocation().x - node.getLocation().x, 2) +
					Math.pow(other.getNode().getLocation().y - node.getLocation().y, 2)
				);
	}
}
