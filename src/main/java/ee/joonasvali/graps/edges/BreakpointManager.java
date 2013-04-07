package ee.joonasvali.graps.edges;

import java.util.HashSet;
import java.util.Set;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class BreakpointManager {
	private Graph graph;
	private BreakpointPlacer placer;
	
	public BreakpointManager(Graph graph){
		this(graph, new StandardPlacer());		
	}
	
	public BreakpointManager(Graph graph, BreakpointPlacer placer){
		this.graph = graph;
		this.placer = placer;		
	}
	
	public void makeBreakPoints(){
		//TESTING
		try{
			CollisionMap map = new CollisionMap(graph, graph.getNodes().getFirst().getPorts().get(0));			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		//TESTING//
		Set<Port> processed = new HashSet<Port>();
		for(Node node : graph.getNodes()){
			for(Port p : node.getPorts()){
				if(processed.add(p)){
					if(p.getPort() != null){
						processed.add(p.getPort());
						p.clearBreakpoints();						
						placer.place(p);
					}
				}
			}
		}		
	}
	
	public void clearBreakpoints(){
		for(Node node : graph.getNodes()){
			for(Port p : node.getPorts()){
				p.clearBreakpoints();
			}			
		}
	}

	
}

