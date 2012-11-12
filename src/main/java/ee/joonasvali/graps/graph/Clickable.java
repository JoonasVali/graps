package ee.joonasvali.graps.graph;

import java.awt.Point;

public interface Clickable {
	public int getWidth();	
	public int getHeight();	
  public Point getLocation();  
  public void setLocation(Point location);
}
