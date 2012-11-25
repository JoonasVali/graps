package ee.joonasvali.graps.layout.forcelayout;

public class Force {
	public double x;
	public double y;
	
	public Force(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Force(){
		this(0,0);
	}

	public void add(Force force) {
	  x+= force.x;
	  y+= force.y;	  
  }
	
	public double getAbsolute(){
		return Math.sqrt(x*x+y*y);
	}
}
