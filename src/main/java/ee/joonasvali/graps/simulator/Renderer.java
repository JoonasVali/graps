package ee.joonasvali.graps.simulator;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Map;
import ee.joonasvali.graps.graph.Clickable;
import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.util.Area;

public interface Renderer {
	public void draw(Graph graph, Graphics2D g, Dimension size);
	public Map<Clickable, Area> getMapping();
	public void setSelected(Clickable clickable);
	public void addListener(ChangeListener change);
	public void removeListener(ChangeListener change);	
}
