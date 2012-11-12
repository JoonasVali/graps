package ee.joonasvali.graps.layout;

import java.awt.Point;

import ee.joonasvali.graps.graph.Node;

public interface Layout {
	public Point getPosition(Node node);
}
