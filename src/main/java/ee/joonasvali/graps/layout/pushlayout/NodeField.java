package ee.joonasvali.graps.layout.pushlayout;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NodeField<T> {
	private List<List<Container>> field; 
	private int expanderCounter = 0;
	
	private List<Container> makeList(){
		ArrayList<Container> list = new ArrayList<Container>(field.get(0).size());
		for(int i = 0; i < field.get(0).size(); i++){
			list.add(new Container(null));
		}
		return list;
	}
	
	public NodeField(T item){
		field = new ArrayList<List<Container>>();
		List<Container> internal = new ArrayList<Container>();
		field.add(internal);
		internal.add(new Container(item));
	}
	
	public void addNextToItem(T item, T newItem){
		for(int i = 0; i < field.size(); i++){
			for(int j = 0; j < field.get(i).size(); j++){
				Container container = field.get(i).get(j);
				if(item.equals(container.item)){
					addNextTo(i, j, newItem);
					return;
				}
			}			
		}		
	}
	
	private void addNextTo(int i, int j, T newItem) {
		Point location = new Point(j, i);
		LinkedList<Direction> availableDir = new LinkedList<Direction>();
	  for(Direction dir : Direction.values()){
	  	Point newLocation = new Point(location.x + dir.getX(), location.y + dir.getY());
	  	try{
		  	Container c = field.get(newLocation.y).get(newLocation.x);
		  	if(c.getItem() == null){
		  		c.setItem(newItem);
		  		return;
		  	}
	  	} catch(IndexOutOfBoundsException e){	
	  		availableDir.add(dir);
	  	}
	  }
	  if(availableDir.size() > 0){	  	
	  	Direction dir = availableDir.getFirst();  	
	  	Point newLocation = new Point(location.x + dir.getX(), location.y + dir.getY());
	  	if(newLocation.x < 0){
	  		i++;
	  		newLocation.x++;
	  	}
	  	if(newLocation.y < 0){
	  		j++;
	  		newLocation.y++;
	  	}
		  extendX(newLocation.x);
		  extendY(newLocation.y);
		  addNextTo(i, j, newItem);
	  	return;
	  }
	  if(expanderCounter++ % 2 == 0){
	  	extendX(j);	  
	  }
	  else extendY(i);	
	  addNextTo(i, j, newItem);
  }
		
	public Map<T, Point> getPositions(){
		Map<T, Point> map = new HashMap<T, Point>();
		for(int i = 0; i < field.size(); i++){
			for(int j = 0; j < field.get(i).size(); j++){
				map.put(field.get(i).get(j).getItem(), new Point(j, i));
			}
		}
		return map;
	}
	

	@Override
	public String toString(){
		StringBuilder strb = new StringBuilder();
		for(int i = 0; i < field.get(0).size(); i++){
			for(int j = 0; j < field.size(); j++){
				Container c = field.get(j).get(i);
				strb.append(c.getItem() == null ? "•" : "☻");
			}
			strb.append("\n");
		}		
		return strb.toString();
	}

			
	private void extendX(int i) {
		for(int k = 0; k < field.size(); k++){
			field.get(k).add(i, new Container(null));
		}	  
	  
  }
	
	private void extendY(int j) {	  
		field.add(j, makeList());
  }
	
	private class Container{
		T item;
		private Container(T item){
			this.item = item;
		}
		
		private T getItem(){
			return item;
		}
		
		private void setItem(T item){
			this.item = item;
		}	
	}
	
}
