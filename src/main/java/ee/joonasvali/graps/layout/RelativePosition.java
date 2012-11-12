package ee.joonasvali.graps.layout;

import java.awt.Point;

import ee.joonasvali.graps.graph.Node;

public class RelativePosition {
	Node node;
	Point location;
	
	public RelativePosition(Node node, Point location){
		this.node = node;
		this.location = location;
		Manager.getInstance().add(this);
	}
}
