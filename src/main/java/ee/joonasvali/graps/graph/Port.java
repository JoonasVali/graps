package ee.joonasvali.graps.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Port {
	private List<Point> breakpoints = new ArrayList<Point>();
	private Node ownerNode;
	private Port port;
	private Point location;	
	
	public Port(Point location){
		this.location = new Point(location);
	}
	
	public void addBreakpoint(Point breakpoint, int index){
		breakpoints.add(index, breakpoint);
	}
	
	public void addBreakpoint(Point breakpoint){		
		breakpoints.add(breakpoint);
	}
	
	public void clearBreakpoints(){
		breakpoints.clear();
		if(port != null)
		port.breakpoints.clear();
	}
	
	public List<Point> getBreakpoints(){
		return new ArrayList<Point>(breakpoints);		
	}	
	
	public Node getNode() {
  	return ownerNode;
  }

	public void setNode(Node node) {
  	this.ownerNode = node;
  	clearBreakpoints();
  }

	public Point getLocation() {
  	return new Point(location);
  }

	public void setLocation(Point location) {
  	this.location = location;
  	clearBreakpoints();
  }
	
	public boolean isOccupied(){
		return port != null;
	}

	public Port getPort() {
  	return port;
  } 
	
	public void setPort(Port port) {
		this.port = port;	
		port.breakpoints = this.breakpoints;
		clearBreakpoints();
  }
	
	public Point getAbsolutes(){		
		Point a = new Point(getNode().getLocation());
	  a.translate(getLocation().x, getLocation().y);
	  return a;
	}

	@Override
	public String toString() {	  	  
	  return (" PORT: "+hashCode()+(getPort() != null ? " connected to "+getPort().hashCode():"")+"\n");	  
	}
}
