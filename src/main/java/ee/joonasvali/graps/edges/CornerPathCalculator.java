package ee.joonasvali.graps.edges;

import java.awt.Point;

import ee.joonasvali.graps.graph.Port;

public class CornerPathCalculator extends PathCalculator {
	private static final int SEARCH_DENSITY = 20;
	
	public static PathCalculatorFactory factory;
	public synchronized static PathCalculatorFactory getFactory(){
		if(factory == null){
			factory = new PathCalculatorFactory(){
				public PathCalculator getPathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {	        
	        return new CornerPathCalculator(port, map, start, end, margin);
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
		
  private CornerPathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {  	
	  this.port = port;
	  this.map = map;
	  this.start = start;
	  this.end = end;
	  this.margin = margin;
  }
	
	@Override
  void calculatePath() {
		FutureBreakpoint bp;
		bp = checkAndCreateDirectRoute(start, end);
		if(bp == null){			
			Point midPoint = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
			boolean success = calculatePathWithMiddlePoint(midPoint);
			if(!success){
				int xVal = Math.max(1, Math.abs(map.getXmin() - map.getXmax()) / SEARCH_DENSITY);
				int yVal = Math.max(1, Math.abs(map.getYmin() - map.getYmax()) / SEARCH_DENSITY);				
				int startx = Math.min(map.getXmin(), map.getXmax());
				int starty = Math.min(map.getYmin(), map.getYmax());
				int endx = Math.max(map.getXmin(), map.getXmax());
				int endy = Math.max(map.getYmin(), map.getYmax());
				
				for(int i = startx; i <= endx; i+=xVal){
					for(int j = starty; j <= endy; j+=yVal){						
						midPoint = new Point(i, j);						
						success = calculatePathWithMiddlePoint(midPoint);
						if(success){							
							return;
						}
					}
				}
			}
		} else {
			if(!bp.isVoid())
				port.addBreakpoint(bp.get());
		}
	}

	private boolean calculatePathWithMiddlePoint(Point midPoint) {
	  FutureBreakpoint bp;
	  bp = checkAndCreateDirectRoute(start, midPoint);
	  if(bp != null){
	  	FutureBreakpoint other = checkAndCreateDirectRoute(midPoint, end);
	  	if(other != null){					
	  		if(!bp.isVoid())
	  			port.addBreakpoint(bp.get());
	  		
	  		if(!(other.isVoid() || bp.isVoid()) && !isAligned(bp.get(), other.get())){
	  			FutureBreakpoint third = checkAndCreateDirectRoute(bp.get(), other.get());
	  			if(third != null && !third.isVoid()){
	  				port.addBreakpoint(third.get());
	  			}
	  		}	  		
	  		
	  		if(!other.isVoid())
	  			port.addBreakpoint(other.get());
	  		return true;
	  	}				
	  }
	  return false;
  }
	
	public FutureBreakpoint checkAndCreateDirectRoute(Point a, Point b){
		if(a.x == b.x || a.y == b.y){
			boolean direct = isAlignedAndDirectAccess(a,b);
			if(direct){
				// DIRECT ROUTE, NO BREAKPOINTS NEEDED
				return new FutureBreakpoint();
			}
		}
		{
			boolean direct = isAlignedAndDirectAccess(a, new Point(a.x, b.y)) && isAlignedAndDirectAccess(b, new Point(a.x, b.y));
			if(direct){				
				return new FutureBreakpoint(a.x, b.y);
			}
		}
		{
			boolean direct = isAlignedAndDirectAccess(a, new Point(b.x, a.y)) && isAlignedAndDirectAccess(b, new Point(b.x, a.y));
			if(direct){				
				return new FutureBreakpoint(b.x, a.y);
			}
		}
		return null;
	}
	
	public boolean isAligned(Point a, Point b){
		return (a.x == b.x || a.y == b.y);
	}
	
	public boolean isAlignedAndDirectAccess(Point a, Point b){
		if(!isAligned(a, b)) throw new IllegalArgumentException("Points not on the same line "+a+" "+b);
		if(a.x == b.x){
			int min = Math.min(a.y, b.y);
			int max = Math.max(a.y, b.y);
			for(int i = min; i < max; i++){
				try{
					if(map.isOccupied(a.x, i)){
						return false;
					}
				} catch(ArrayIndexOutOfBoundsException e){
					return false; 
				}
			}	
			return true;
		} 
		else {
			int min = Math.min(a.x, b.x);
			int max = Math.max(a.x, b.x);
			for(int i = min; i < max; i++){
				try{
					if(map.isOccupied(i, a.y)){
						return false;
					}
				} catch(ArrayIndexOutOfBoundsException e){
					return false; 
				}
			}	
			return true;
		} 
	}
	
	private class FutureBreakpoint {
		private boolean voidBreakpoint;
		private int x, y;
		
		private FutureBreakpoint(){
			voidBreakpoint = true;
		}		
		
		private FutureBreakpoint(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		private boolean isVoid(){
			return voidBreakpoint;
		}		
		
		private Point get(){
			return new Point(x, y);
		}
	}
}
