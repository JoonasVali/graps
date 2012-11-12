package ee.joonasvali.graps.layout;

import java.util.LinkedList;

public class Manager {
	private static Manager manager;
	private LinkedList<RelativePosition> positions = new LinkedList<RelativePosition>();
	
	public static Manager getInstance(){
		if(manager == null){
			manager = new Manager();
		}
		return manager;
	}	
	
	public void add(RelativePosition position){
		positions.add(position);
	}
}
