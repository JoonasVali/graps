package ee.joonasvali.graps.graph;

import java.awt.Point;
import java.util.LinkedList;

import ee.joonasvali.graps.layout.Layout;

public class Graph {
	LinkedList<Node> nodes;
	
	public Graph(LinkedList<Node> nodes){		
		this.nodes = nodes;		
	}
	
	public LinkedList<Node> getNodes() {
  	return new LinkedList<Node>(nodes);
  }

	public void setNodes(LinkedList<Node> nodes) {		
  	this.nodes = nodes;
  }
	
	@Override
	public String toString() {
	  StringBuilder strb = new StringBuilder();
	  for(Node n: nodes){
	  	strb.append("Node ("+n.getWidth()+":"+n.getHeight()+"): \n");
	  	strb.append(n);
	  }
	  return strb.toString();
	}

	public void assign(Layout layout, int scale) {		
	  for(Node node: nodes){	  	
	  	Point position = new Point(layout.getPosition(node));
	  	position.x *= scale;
	  	position.y *= scale;
	  	node.setLocation(position);
	  }	  
  }
	
}
