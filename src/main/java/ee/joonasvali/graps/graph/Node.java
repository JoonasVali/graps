package ee.joonasvali.graps.graph;

import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Node implements Clickable{
	private List<Port> ports;
	private Point location;
	private Point size;
	
	public Node(Point location, Point size){
		ports = new LinkedList<Port>();
		this.location = location;
		this.size = size;
	}
	public Node(LinkedList<Port> ports){
		this.ports = (List<Port>) Collections.unmodifiableList(ports);
	}
	
	public void addPort(Port p){
		ports.add(p);
	}
	
	public List<Port> getPorts() {
  	return ports;
  }
		
	public int getWidth(){
		return size.x;		
	}
	
	public int getHeight(){
		return size.y;		
	}
	
	public LinkedList<Port> getOpenPorts(){
		LinkedList<Port> res = new LinkedList<Port>();
		for(Port p : ports){
			if(!p.isOccupied()){
				res.add(p);
			}
		}
		return res;
	}
	/**
   * @return the location
   */
  public Point getLocation() {  
	  return new Point(location);
  }	

	@Override
	public String toString() {
	  StringBuilder strb = new StringBuilder();
	  for(Port p : ports){	  	
	  	strb.append(p);
	  }
	  return strb.toString();
	}
	public void setLocation(Point point) {
	  location.x = point.x;
	  location.y = point.y;	  
  }
}
