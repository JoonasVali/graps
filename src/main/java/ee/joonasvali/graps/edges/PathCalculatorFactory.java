package ee.joonasvali.graps.edges;

import java.awt.Point;

import ee.joonasvali.graps.graph.Port;

public interface PathCalculatorFactory {
	public PathCalculator getPathCalculator(Port port, CollisionMap map, Point start, Point end, int margin);
}
