package ee.joonasvali.graps.layout;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class OrthogonalLayout implements Layout{
	private Graph graph;
	
	public OrthogonalLayout(Graph graph){
		this.graph = graph;
		for(Node n : getNodes(1)){
			System.out.println(n.getLocation());
		}
		
	}
	
	public Point getPosition(Node node) {
	  // TODO Auto-generated method stub
	  return null;
  }
	
	private List<Node> getNodes(int foreignConnections){
		List<Node> nodes = new LinkedList<Node>();
		Set<Node> set = new HashSet<Node>();
		for(Node n : graph.getNodes()){			
			set.clear();
			for(Port p : n.getPorts()){				
				if(!p.getPort().getNode().equals(n)){					
					set.add(p.getPort().getNode());
				}
			}
			if(set.size() == foreignConnections){
				nodes.add(n);
			}
		}
		return nodes;		
	}
	
	/**
	 * 
	 * @return first foreign node
	 */
	private Node getForeignNode(Node n){
		for(Port p : n.getPorts()){
			if(!p.getPort().getNode().equals(n)){
				return p.getPort().getNode();
			}
		}
		return null;
	}
}
