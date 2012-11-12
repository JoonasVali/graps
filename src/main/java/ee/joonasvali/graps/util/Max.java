package ee.joonasvali.graps.util;

public class Max {
	private int max;
	
	public void add(int number){
		max = number > max ? number : max;
	}
	
	public int get(){
		return max;
	}
}
