package ee.joonasvali.graps.layout.pushlayout;

import java.awt.Point;
import java.util.Map;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.Layout;
import ee.joonasvali.graps.layout.forcelayout.UpdateListener;

public class PushLayout implements Layout{
	private NodeProvider provider;
	private Map<Node, Point> map;	
	
	public void execute(Graph graph){
		if(!(graph instanceof NodeProvider)){
			throw new IllegalArgumentException(graph.toString());
		}
		if(provider.hasNext()){
			Node prev = provider.next();
			NodeField<Node> field = new NodeField<Node>(prev);
			while(provider.hasNext()){
				Node next = provider.next();
				field.addNextToItem(prev, next);
				prev = next;
			}
			map = field.getPositions();
		}		
	}
		
	public Point getPosition(Node node) {		
		return map.get(node);	  
  }

	public void addListener(UpdateListener listener) {
	  // TODO Auto-generated method stub
	  
  }

	public void stop() {
	  // TODO Auto-generated method stub
	  
  }

	public void removeListener(UpdateListener listener) {
	  // TODO Auto-generated method stub
	  
  }
}
