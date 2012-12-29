package ee.joonasvali.graps.util;

import java.awt.Point;
import java.util.Collection;

import ee.joonasvali.graps.graph.Clickable;

public class GraphUtil {
	public static Point calculateMaxPosition(Collection<? extends Clickable> nodes){
		Point pos = new Point(0,0);
		Max x = new Max();
		Max y = new Max();
	  for(Clickable node: nodes){
	  	x.add(node.getLocation().x+node.getWidth());
	  	y.add(node.getLocation().y+node.getHeight());	  	
	  }
	  pos.x = x.get();
	  pos.y = y.get();
	  return pos;
	}
	
	public static Point calculateMinPosition(Collection<? extends Clickable> nodes){
		Point pos = new Point(0,0);
		Min x = new Min();
		Min y = new Min();
	  for(Clickable node: nodes){
	  	x.add(node.getLocation().x);
	  	y.add(node.getLocation().y);	  	
	  }
	  pos.x = x.get();
	  pos.y = y.get();
	  return pos;
	}
	
	public static int ran(int max){
		return (int)(Math.random()*max);
	}
}
