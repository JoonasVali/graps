package ee.joonasvali.graps.layout.pushlayout;

import java.util.Collection;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;

abstract class NodeProvider extends Graph{
	public NodeProvider(Collection<Node> nodes) {
	  super(nodes);	  
  }
	
	public abstract boolean hasNext();
	public abstract Node next();
}
