package ee.joonasvali.graps.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import ee.joonasvali.graps.graph.Clickable;
import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;
import ee.joonasvali.graps.util.Area;
import ee.joonasvali.graps.util.GraphUtil;

public class SimpleRenderer implements Renderer{
	private static final int PORT_SIZE = 3;	
	private Map<Clickable, Area> map = new HashMap<Clickable, Area>();
	
  private Clickable selected;  
  
  private ColorBlinker blink = new ColorBlinker(200, new Color(0,0,255), new Color(0,255,0), new Color(255,0,0));
  private LinkedList<ChangeListener> listeners = new LinkedList<ChangeListener>();
	
	public void draw(Graph graph, Graphics2D g, Dimension size) {	  
	  Set<Port> painted = new HashSet<Port>();
	  for(Node n: graph.getNodes()){
	  	drawNodes(g, n);
	  	for(Port p : n.getPorts()){	  		
	  		drawPorts(g, n, p);
	  		if(p.isOccupied()){
	  			if(painted.add(p.getPort())){
	  				painted.add(p);
	  				drawLines(g, p);	  			
	  			}
	  		}
	  	}
	  }	  
	  drawSelected(g);
  }

	private void drawSelected(Graphics2D g) {
		if(selected == null) return;
		g.setColor(Color.GREEN);
		g.drawRect(
				scaleX(selected.getLocation().x - 2), 
				scaleY(selected.getLocation().y - 2), 
				scaleX(selected.getWidth()+ 4), 
				scaleY(selected.getHeight()+ 4));
		g.setColor(Color.BLACK);
		if(selected != null && selected instanceof Node){
			drawRelatives(g);
		}
  }

	private void drawRelatives(Graphics2D g) {
	  Node n = (Node)selected;
	  for(Port p : n.getPorts()){
	  	Node relative = p.getPort().getNode();
	  	if (!relative.equals(n)){
	  		g.setColor(blink.getColor());
	  		g.drawRect(
	  				scaleX(relative.getLocation().x - 2), 
	  				scaleY(relative.getLocation().y - 2), 
	  				scaleX(relative.getWidth()+ 4), 
	  				scaleY(relative.getHeight()+ 4));
	  		g.setColor(Color.BLACK);
	  	}
	  }	  
  }

	private void drawNodes(Graphics2D g, Node n) {
		int x = scaleX(n.getLocation().x);
		int y = scaleY(n.getLocation().y);
		int width = scaleX(n.getWidth());
		int height = scaleY(n.getHeight());
		Area a = map.get(n);
		if(a != null){
			a.setWidth(width);
			a.setHeight(height);
			a.setX(x);
			a.setY(y);
		} 
		else {
			a = new Area(x, y, width, height);
			map.put(n, a);
		}
	  g.drawRect(x, y, width, height);
  }

	private void drawLines(Graphics2D g, Port p) {
		Point breaking = 
				new Point(
						scaleX(p.getPort().getNode().getLocation().x + p.getPort().getLocation().x), 
						scaleY(p.getNode().getLocation().y + p.getLocation().y) 
				);
		g.drawLine(
				scaleX(p.getPort().getLocation().x + p.getPort().getNode().getLocation().x),
				scaleY(p.getPort().getLocation().y + p.getPort().getNode().getLocation().y),
				breaking.x,
				breaking.y
		);
		
		g.drawLine(
				scaleX(p.getLocation().x + p.getNode().getLocation().x),
	  		scaleY(p.getLocation().y + p.getNode().getLocation().y),
				breaking.x,
				breaking.y
		);		
  }

	private void drawPorts(Graphics2D g, Node n, Port p) {
		g.setColor(Color.RED);
	  g.fillRect(
	  		scaleX(n.getLocation().x+p.getLocation().x - PORT_SIZE/2),
	  		scaleY(n.getLocation().y+p.getLocation().y - PORT_SIZE/2),
	  		scaleX(PORT_SIZE),
	  		scaleY(PORT_SIZE)
	  );
	  g.setColor(Color.BLACK);
	  g.drawRect(
	  		scaleX(n.getLocation().x+p.getLocation().x  - PORT_SIZE/2),
	  		scaleY(n.getLocation().y+p.getLocation().y  - PORT_SIZE/2),
	  		scaleX(PORT_SIZE),
	  		scaleY(PORT_SIZE)
	  );
  }
	
	public int scaleX(int orig){
	  return orig; /* REIMPLEMENT IF EVER NEEDED */
  }
	
	public int scaleY(int orig){
		return orig; /* REIMPLEMENT IF EVER NEEDED */
  }

	private void notifyListeners(){
		for(ChangeListener listener : listeners){
			listener.onChange(selected);
		}
	}
	
	public Map<Clickable,Area> getMapping() {
	  return map;	  
  }

	public void setSelected(Clickable clickable) {		
	  this.selected = clickable;  
	  notifyListeners();
  }

	public void addListener(ChangeListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(ChangeListener listener){
		listeners.remove(listener);
	}
	
}

