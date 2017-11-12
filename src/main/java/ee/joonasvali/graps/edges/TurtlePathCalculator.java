package ee.joonasvali.graps.edges;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ee.joonasvali.graps.graph.Port;

public class TurtlePathCalculator extends PathCalculator{

	private boolean[] illegals = new boolean[4];
	private Point current;

	public static PathCalculatorFactory factory;
	public synchronized static PathCalculatorFactory getFactory(){
		if(factory == null){
			factory = new PathCalculatorFactory(){
				public PathCalculator getPathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {
	        return new TurtlePathCalculator(port, map, start, end, margin);
        }
			};
		}
		return factory;
	}

	private TurtlePathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {
		super(port, map, start, end, margin);
		current = new Point(start);
  }

	@Override
  boolean calculatePath() {
		illegals[getPortSide().ordinal()] = true;
		while(!isDone()){
			Direction dir = getNextDirection();
			if(dir == null) return false;
			step(dir);
			clearIllegals();
			illegals[Direction.getOpposite(dir).ordinal()] = true;
		}

		return true;
  }

	private boolean isDone(){
		if(isAligned(current, port.getPort().getAbsolutes())){
			if(isAlignedAndDirectAccess(current, port.getPort().getAbsolutes())){
				return true;
			}
		}
		return false;
	}

	private void step(Direction dir) {
	  int i = getFree(dir);
	  if(dir == Direction.EAST){
	  	current.x += i;
	  }
	  if(dir == Direction.WEST){
	  	current.x -= i;
	  }
	  if(dir == Direction.SOUTH){
	  	current.y += i;
	  }
	  if(dir == Direction.NORTH){
	  	current.y -= i;
	  }
	  if(!current.equals(port.getPort().getAbsolutes())){
	  	port.addBreakpoint(current);
	  }
  }

	private void clearIllegals() {
	  for(int i = 0; i < illegals.length; i++){
	  	illegals[i] = false;
	  }

  }

	private Direction getPortSide() {
		if(port.getAbsolutes().x == start.x){
			if(port.getAbsolutes().y  < start.y) return Direction.NORTH;
			else return Direction.SOUTH;
		} else {
			if(port.getAbsolutes().x  < start.x) return Direction.WEST;
			else return Direction.EAST;
		}
  }

	private Direction getNextDirection(){
		Port other = port.getPort();
		Direction dir;
		Collection<Direction> directions = getReasonableDirections();
		if(other.getAbsolutes().x - current.x > other.getAbsolutes().y - current.y){
			dir = getHorizontal(directions);
			if(dir != null) return dir;
			if(directions.size() > 0) return directions.iterator().next();
		} else {
			dir = getVertical(directions);
			if(dir != null) return dir;
			if(directions.size() > 0) return directions.iterator().next();
		}
		return null;
	}

	private static Direction getVertical(Collection<Direction> col){
		for(Direction dir: col){
			if(dir == Direction.NORTH || dir == Direction.SOUTH) return dir;
		}
		return null;
	}

	private static Direction getHorizontal(Collection<Direction> col){
		for(Direction dir: col){
			if(dir == Direction.EAST || dir == Direction.WEST) return dir;
		}
		return null;
	}

	private Set<Direction> getReasonableDirections(){
		Set<Direction> set = new HashSet<Direction>();
		Port other = port.getPort();

		int east = illegals[Direction.EAST.ordinal()] ? 0 : getFree(Direction.EAST);
		int west = illegals[Direction.WEST.ordinal()] ? 0 : getFree(Direction.WEST);
		int north = illegals[Direction.NORTH.ordinal()] ? 0 : getFree(Direction.NORTH);
		int south = illegals[Direction.SOUTH.ordinal()] ? 0 : getFree(Direction.SOUTH);
		if(east != 0) set.add(Direction.EAST);
		if(west != 0) set.add(Direction.WEST);
		if(north != 0) set.add(Direction.NORTH);
		if(south != 0) set.add(Direction.SOUTH);

		if(other.getAbsolutes().x > current.x){
			set.remove(Direction.WEST);
		}

		if(other.getAbsolutes().x < current.x){
			set.remove(Direction.EAST);
		}

		if(other.getAbsolutes().y < current.y){
			set.remove(Direction.SOUTH);
		}

		if(other.getAbsolutes().y > current.y){
			set.remove(Direction.NORTH);
		}

		return set;
	}


	private int getFree(Direction direction){
		int count = 0;
		if(direction.equals(Direction.EAST)){
			for(int i = current.x; i < map.getXmax(); i++){
				if(!map.isOccupied(new Point(i, current.y))){
					count++;
				} else {
					return count;
				}
			}
		}

		if(direction.equals(Direction.WEST)){
			for(int i = current.x; i > map.getXmin(); i--){
				if(!map.isOccupied(new Point(i, current.y))){
					count++;
				} else {
					return count;
				}
			}
		}

		if(direction.equals(Direction.NORTH)){
			for(int i = current.y; i > map.getYmin(); i--){
				if(!map.isOccupied(new Point(current.x, i))){
					count++;
				} else {
					return count;
				}
			}
		}

		if(direction.equals(Direction.SOUTH)){
			for(int i = current.y; i < map.getYmax(); i++){
				if(!map.isOccupied(new Point(current.x, i))){
					count++;
				} else {
					return count;
				}
			}
		}

		return count;

	}

	private enum Direction{
		EAST, WEST, NORTH, SOUTH;

		public static Direction getOpposite(Direction dir){
			switch(dir){
				case EAST: return WEST;
				case WEST: return EAST;
				case NORTH: return NORTH;
				case SOUTH: return SOUTH;
			}
			return null;
		}
	}
}
