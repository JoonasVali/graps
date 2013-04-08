package ee.joonasvali.graps.edges;

import java.awt.Point;

import ee.joonasvali.graps.graph.Port;

public abstract class PathCalculator {	
	abstract void calculatePath(Port port, CollisionMap map, Point start, Point end, int margin);	
}
