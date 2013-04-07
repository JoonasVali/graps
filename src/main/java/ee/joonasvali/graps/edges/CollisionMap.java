package ee.joonasvali.graps.edges;

import java.awt.Point;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

public class CollisionMap {
	private final static int MARGIN = 30;
	private Graph graph;	
	private Node a,b ;
	
	private int xmin, ymin, xmax, ymax;
	private boolean[][] map;
	
	/**
	 * 
	 * @param graph The full graph
	 * @param p The port to be connected
	 */
	public CollisionMap(Graph graph, Port port){
		this.graph = graph;		
		if(port.getPort() == null){
			throw new IllegalArgumentException(port+" is not connected to any other port");
		}
		a = port.getNode();
		b = port.getPort().getNode();
		checkNodesInGraph();
		createMap();		
	}

	private void checkNodesInGraph() throws IllegalArgumentException{	  
		if(graph.getNodes().contains(a) && graph.getNodes().contains(b)){
			return;
		}	
		throw new IllegalArgumentException("One of the nodes missing from the provided graph "+a+" "+b);
  }	

	private void createMap() {
	  xmax = (a.getLocation().x + a.getWidth() > b.getLocation().x + b.getWidth() ? a.getLocation().x + a.getWidth() : b.getLocation().x + b.getWidth()) + MARGIN;
	  ymax = (a.getLocation().y + a.getHeight() > b.getLocation().y + b.getHeight() ? a.getLocation().y + a.getHeight() : b.getLocation().y + b.getHeight()) + MARGIN;
	  xmin = (a.getLocation().x < b.getLocation().x ? a.getLocation().x : b.getLocation().x) - MARGIN;
	  ymin = (a.getLocation().y < b.getLocation().y ? a.getLocation().y : b.getLocation().y) - MARGIN;
	  map = new boolean[xmax - xmin][ymax - ymin];
	  System.out.println("MAP "+(xmax- xmin)+" "+(ymax - ymin));
	  System.out.println(xmax + " " +ymax + " " + xmin + " " +ymin );
	  
	  for(Node node: graph.getNodes()){
	  	if(contains(node)){
	  		renderToMap(node);
	  	}
	  }
  }	
	
	private void renderToMap(Node node) {
		Point location = translateToLocal(node.getLocation());		
	  for(int i = location.x ; i < location.x + node.getWidth() ; i++){
	  	for(int j = location.y; j < location.y + node.getHeight(); j++){
	  		if(i < 0 || i >= map.length || j < 0 || j >= map[0].length) continue;
	  		map[i][j] = true;
	  	}
	  }	  
  }
	
	private Point translateToLocal(Point point){
		return new Point(point.x - xmin, point.y - ymin);
	}

	public boolean contains(Node node){
		if(contains(node.getLocation())) 
			return true;
		Point temp = new Point(node.getLocation());
		temp.translate(node.getWidth(), 0);
		if(contains(temp)) 
			return true;
		temp = new Point(node.getLocation());
		temp.translate(0, node.getHeight());
		if(contains(temp)) 
			return true;
		temp = new Point(node.getLocation());
		temp.translate(node.getWidth(), node.getHeight());
		return contains(temp);
	}
	
	private boolean contains(Point point){		
		return contains(point.x, point.y);
	}
	
	private boolean contains(int x, int y){		
		return (x > xmin && x < xmax && y > ymin && y < ymax);		
	}
	
	public boolean isOccupied(Point p){
		return isOccupied(p.x, p.y);
	}
	
	public boolean isOccupied(int x, int y){
		return map[x - xmin][y - ymin];
	}
}
