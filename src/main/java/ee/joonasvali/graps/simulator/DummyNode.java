package ee.joonasvali.graps.simulator;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;
import ee.joonasvali.graps.util.FlagManager;

public class DummyNode {
	public static final FlagManager<Node> manager = FlagManager.getInstance(Node.class);
	public static final String DUMMY_NODE = "dummy";

	public static void injectAll(Graph graph) {
		HashSet<Port> set = new HashSet<Port>();
		LinkedList<Node> list = graph.getNodes();
		System.out.println(list.size());
	  for(Node node : graph.getNodes()){
	  	for(Port p: node.getPorts()){
	  		if(set.add(p)){
	  			set.add(p.getPort());
	  			Node dummy = new Node(new Point(0,0), new Point(1,1));
	  			Port dummyPortA = new Port(new Point(0, 0));
	  			Port dummyPortB = new Port(new Point(0, 0));
	  			dummyPortA.setNode(dummy);
	  			dummyPortB.setNode(dummy);	  			
	  			
	  			dummyPortA.setPort(p);	  			
	  			dummyPortB.setPort(p.getPort());
	  			p.getPort().setPort(dummyPortB);
	  			p.setPort(dummyPortA);
	  			
	  			dummy.addPort(dummyPortA);
	  			dummy.addPort(dummyPortB);
	  	  	list.add(dummy);	  	  	
	  	  	manager.set(dummy, DUMMY_NODE, true);
	  		}	  		
	  	}	  	

	  }
	  graph.setNodes(list);	  
  }
	
	public static boolean isDummyNode(Node node){
		return manager.get(node, DUMMY_NODE);
	}
	
	
}
