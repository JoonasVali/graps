package ee.joonasvali.graps.util;

import java.awt.Point;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class GraphUtil {
	public static Point calculateMaxPosition(Graph g){
		Point pos = new Point(0,0);
		Max x = new Max();
		Max y = new Max();
	  for(Node node: g.getNodes()){
	  	x.add(node.getLocation().x+node.getWidth());
	  	y.add(node.getLocation().y+node.getHeight());
	  	for(Port p: node.getOpenPorts()){
	  	// Assumes Graph connectors have relative positions to nodes 
	  		x.add(node.getLocation().x+node.getWidth() + p.getLocation().x+node.getWidth()); 
	  		y.add(node.getLocation().y+node.getHeight() + p.getLocation().y+node.getHeight());
	  	}
	  }
	  pos.x = x.get();
	  pos.y = y.get();
	  return pos;
	}
	
	public static int ran(int max){
		return (int)(Math.random()*max);
	}
}
