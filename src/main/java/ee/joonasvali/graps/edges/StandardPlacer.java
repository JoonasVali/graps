package ee.joonasvali.graps.edges;

import java.awt.Point;

import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class StandardPlacer implements BreakpointPlacer{
	private static final int OUT_MARGIN = 10;
	
	public void place(Port p) {
		Point a = directOutOfArea(p, p.getAbsolutes());		
		Point b = directOutOfArea(p.getPort(), p.getPort().getAbsolutes());
		p.addBreakpoint(b);		
		if(Math.abs(a.x - b.x) < Math.abs(a.y - b.y)){
			p.addBreakpoint(new Point((a.x + b.x) / 2, b.y));
			p.addBreakpoint(new Point((a.x + b.x) / 2, a.y));
		} else {
			p.addBreakpoint(new Point(b.x, (a.y + b.y) / 2));
			p.addBreakpoint(new Point(a.x, (a.y + b.y) / 2));
		}		
		
		// This must be added as last breakpoint
		p.addBreakpoint(a);	  
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
