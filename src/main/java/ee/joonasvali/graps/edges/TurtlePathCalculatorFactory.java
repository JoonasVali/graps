package ee.joonasvali.graps.edges;

public class TurtlePathCalculatorFactory implements PathCalculatorFactory{
	public PathCalculator getPathCalculator() {	  
	  return new TurtlePathCalculator();
  }
}
