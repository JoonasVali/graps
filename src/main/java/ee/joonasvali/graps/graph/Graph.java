package ee.joonasvali.graps.graph;

import java.util.LinkedList;

public class Graph {
	LinkedList<Node> nodes;
	
	public Graph(LinkedList<Node> nodes){
		this.nodes = nodes;
	}
	
	public LinkedList<Node> getNodes() {
  	return nodes;
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
	
}
