package ee.joonasvali.graps.layout.pushlayout;

public class Main {
	public static void main(String[] args) {
	  NodeField<Integer> field = new NodeField<Integer>(0);
	  for(int i = 0; i < 20; i++){
	  	field.addNextToItem((int)(Math.random()*i), i+1);
	  	System.out.println(field);
	  	//field.addNextToItem(i, i+1);
	  }
	  System.out.println(field);
	  
  }
}
