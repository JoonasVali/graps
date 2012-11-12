package ee.joonasvali.graps.util;

import java.awt.Point;

public class Area {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Area(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean isInRange(Point point, int a){		
		if(point.x < this.x-a || point.x > this.x+width+a ){
			return false;
		}
		if(point.y < this.y-a || point.y > this.y+height+a ){
			return false;
		}
		return true;
	}

	public int getX() {
  	return x;
  }

	public void setX(int x) {
  	this.x = x;
  }

	public int getY() {
  	return y;
  }

	public void setY(int y) {
  	this.y = y;
  }

	public int getWidth() {
  	return width;
  }

	public void setWidth(int width) {
  	this.width = width;
  }

	public int getHeight() {
  	return height;
  }

	public void setHeight(int height) {
  	this.height = height;
  }
	
	
}
