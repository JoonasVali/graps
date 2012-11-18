package ee.joonasvali.graps.layout.pushlayout;

public class Main {
	public static void main(String[] args) {
	  NodeField<Integer> field = new NodeField<Integer>(0);
	  for(int i = 0; i < 1000; i++){
	  	field.addNextToItem((int)(Math.random()*i), i+1);	  	
	  }
	  System.out.println(field);
	  
  }
}
