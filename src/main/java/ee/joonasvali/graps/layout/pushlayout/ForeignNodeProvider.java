package ee.joonasvali.graps.layout.pushlayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class ForeignNodeProvider implements NodeProvider{
	private LinkedList<Node> nodes;
	
	public ForeignNodeProvider(Graph graph){
		this.nodes = graph.getNodes();
		Collections.sort(this.nodes, getComparator());
	}
	
	private Comparator<Node> getComparator(){
		return new Comparator<Node>(){
			public int compare(Node o1, Node o2) {	      
	      return Integer.compare(getForeignNodes(o1), getForeignNodes(o2));
      }		
		};
	}
	
	public boolean hasNext() {	  
	  return nodes.size() > 0;
  }

	public Node next() {		
	  return nodes.removeFirst();
  }

	public int getForeignNodes(Node n){
		Set<Node> set = new HashSet<Node>();
		set.clear();
		for(Port p : n.getPorts()){				
			if(!p.getPort().getNode().equals(n)){					
				set.add(p.getPort().getNode());
			}
		}
		return set.size();	
	}	
}
