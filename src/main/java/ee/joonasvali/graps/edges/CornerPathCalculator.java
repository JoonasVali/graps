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
				
  private CornerPathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {  	
  	super(port, map, start, end, margin);	 
  }
	
	@Override
  boolean calculatePath() {
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
							return true;
						}
					}
				}
				return false;
			}
		} else {
			if(!bp.isVoid())
				port.addBreakpoint(bp.get());
		}
		return true;
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
