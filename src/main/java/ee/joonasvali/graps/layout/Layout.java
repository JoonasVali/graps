package ee.joonasvali.graps.layout;

import java.awt.Point;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.forcelayout.UpdateListener;

public interface Layout {
	public Point getPosition(Node node);
	public void execute(Graph graph);
	public void stop();
	public void addListener(UpdateListener listener);
}
