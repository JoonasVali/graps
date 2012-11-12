package ee.joonasvali.graps.graph;

import java.awt.Point;

public class Port {
	private Node ownerNode;
	private Port port;
	private Point location;
	
	public Port(Point location){
		this.location = location;
	}
	
	public Node getNode() {
  	return ownerNode;
  }

	public void setNode(Node node) {
  	this.ownerNode = node;
  }

	public Point getLocation() {
  	return location;
  }

	public void setLocation(Point location) {
  	this.location = location;
  }
	
	public boolean isOccupied(){
		return port != null;
	}

	public Port getPort() {
  	return port;
  } 
	
	public void setPort(Port port) {
		this.port = port;	
  }
	

	@Override
	public String toString() {	  	  
	  return (" PORT: "+hashCode()+(getPort() != null ? " connected to "+getPort().hashCode():"")+"\n");	  
	}
}
