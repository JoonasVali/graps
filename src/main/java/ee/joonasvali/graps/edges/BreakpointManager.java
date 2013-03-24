package ee.joonasvali.graps.edges;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class BreakpointManager {
	Graph graph;
	
	public BreakpointManager(Graph graph){
		this.graph = graph;
	}
	
	public void makeBreakPoints(){		
		Set<Port> processed = new HashSet<Port>();
		for(Node node : graph.getNodes()){
			for(Port p : node.getPorts()){
				if(processed.add(p)){
					if(p.getPort() != null){
						processed.add(p.getPort());
						p.clearBreakpoints();						
						makeBreakPoints(p);
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

	private void makeBreakPoints(Port p) {		
		Point a = p.getAbsolutes();
		Point b = p.getPort().getAbsolutes();
		if(Math.abs(a.x - b.x) < Math.abs(a.y - b.y)){
			p.addBreakpoint(new Point((a.x + b.x) / 2, b.y));
			p.addBreakpoint(new Point((a.x + b.x) / 2, a.y));
		} else {
			p.addBreakpoint(new Point(b.x, (a.y + b.y) / 2));
			p.addBreakpoint(new Point(a.x, (a.y + b.y) / 2));
		}		
			  
  }
}

