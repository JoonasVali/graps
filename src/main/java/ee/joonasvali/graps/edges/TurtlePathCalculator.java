package ee.joonasvali.graps.edges;

import java.awt.Point;

import ee.joonasvali.graps.graph.Port;

public class TurtlePathCalculator extends PathCalculator {
	
	public static PathCalculatorFactory factory;
	public synchronized static PathCalculatorFactory getFactory(){
		if(factory == null){
			factory = new PathCalculatorFactory(){
				public PathCalculator getPathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {	        
	        return new TurtlePathCalculator(port, map, start, end, margin);
        }				
			};
		}
		return factory;
	}
		
	private Port port;
	private CollisionMap map;
	private Point start;
	private Point end;
	private int margin;
		
  private TurtlePathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {
	  this.port = port;
	  this.map = map;
	  this.start = start;
	  this.end = end;
	  this.margin = margin;
  }
	
	@Override
  void calculatePath() {
		boolean success = checkAndCreateDirectRoute(start, end);
		if(!success){			
			Point midPoint = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
			success = checkAndCreateDirectRoute(start, midPoint);
			if(success){
				success = checkAndCreateDirectRoute(midPoint, end);
				return;
			}			
		}
	}
	
	public boolean checkAndCreateDirectRoute(Point a, Point b){
		if(a.x == b.x || a.y == b.y){
			boolean direct = hasDirectRouteLine(a,b);
			if(direct){
				return true;
			}
		}
		{
			boolean direct = hasDirectRouteLine(a, new Point(a.x, b.y)) && hasDirectRouteLine(b, new Point(a.x, b.y));
			if(direct){
				port.addBreakpoint(new Point(a.x, b.y));
				return true;
			}
		}
		{
			boolean direct = hasDirectRouteLine(a, new Point(b.x, a.y)) && hasDirectRouteLine(b, new Point(b.x, a.y));
			if(direct){
				port.addBreakpoint(new Point(b.x, a.y));
				return true;
			}
		}
		return false;
	}
	
	public boolean hasDirectRouteLine(Point a, Point b){
		if(a.x != b.x && a.y != b.y) throw new IllegalArgumentException("Points not on the same line "+a+" "+b);
		if(a.x == b.x){
			int min = Math.min(a.y, b.y);
			int max = Math.max(a.y, b.y);
			for(int i = min; i < max; i++){
				if(map.isOccupied(a.x, i)){
					return false;
				}
			}	
			return true;
		} 
		else {
			int min = Math.min(a.x, b.x);
			int max = Math.max(a.x, b.x);
			for(int i = min; i < max; i++){
				if(map.isOccupied(i, a.y)){
					return false;
				}
			}	
			return true;
		} 
	}
		
	private enum Direction{
		NORTH, EAST, WEST, SOUTH
	}
}
