package ee.joonasvali.graps.layout.pushlayout;

import java.awt.Point;

public enum Direction {
	NORTH(0, 1),
	NORTHEAST(1, 1),	
	EAST(1, 0),
	SOUTHEAST(1, -1),
	SOUTH(0, -1),
	SOUTHWEST(-1,-1),
	WEST(-1, 0),
	NORTHWEST(-1, 1);
		
	Point offset;
	private Direction(int x, int y){
		offset = new Point(x, y);		
	}
	
	public Point getOffset(){
		return new Point(offset);
	}
	
	public int getX(){
		return offset.x;
	}
	public int getY(){
		return offset.y;
	}
}
