package ee.joonasvali.graps.edges;

import java.awt.Point;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class StandardPlacer implements BreakpointPlacer{
	private static final int OUT_MARGIN = 10;
	private Graph graph;
	private PathCalculatorFactory calculatorFactory;
	
	public StandardPlacer(Graph graph, PathCalculatorFactory calculatorFactory){
		this.graph = graph;
		this.calculatorFactory = calculatorFactory;
	}
	
	public void place(Port p) {				
		Point a = directOutOfArea(p.getPort(), p.getPort().getAbsolutes());
		Point b = directOutOfArea(p, p.getAbsolutes());
				
		try{
			CollisionMap map = new CollisionMap(graph, p);
			calculatorFactory.getPathCalculator().calculatePath(p, map, a, b, OUT_MARGIN);
			p.addBreakpoint(a); // don't add if something fails			
		} catch(Exception e){
			e.printStackTrace();
			return;
		}		
		// This must be added as last breakpoint
		p.addBreakpoint(b);	  
  }

	private Point directOutOfArea(Port p, Point absolutes) {
		boolean isRight = false;
	  boolean isDown = false;
	  Node node = p.getNode();
	  if(absolutes.x - node.getLocation().x > node.getLocation().x + node.getWidth() - absolutes.x){	  	
	  	isRight = true;
	  }
	  int comparatorX = Math.abs(absolutes.x - node.getLocation().x - (node.getLocation().x + node.getWidth() - absolutes.x));
	  
	  if(absolutes.y - node.getLocation().y > node.getLocation().y + node.getHeight() - absolutes.y){
	  	isDown = true;
	  }
	  int comparatorY = Math.abs(absolutes.y - node.getLocation().y - (node.getLocation().y + node.getHeight() - absolutes.y));
	  
	  Point out = new Point(absolutes);
	  if(comparatorX > comparatorY){
		  if(isRight){
		  	out.x = absolutes.x + (node.getLocation().x + node.getWidth() - absolutes.x) + OUT_MARGIN;
		  } else {
		  	out.x = absolutes.x - (absolutes.x - node.getLocation().x) - OUT_MARGIN;
		  }
	  } else {
	  	if(isDown){
		  	out.y = absolutes.y + (node.getLocation().y + node.getHeight() - absolutes.y) + OUT_MARGIN;
		  } else {
		  	out.y = absolutes.y - (absolutes.y - node.getLocation().y) - OUT_MARGIN;
		  }
	  } 
		
	  return out;
  }
}
