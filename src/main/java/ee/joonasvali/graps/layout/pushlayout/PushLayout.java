package ee.joonasvali.graps.layout.pushlayout;

import java.awt.Point;
import java.util.Map;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.Layout;

public class PushLayout implements Layout{
	private NodeProvider provider;
	private Map<Node, Point> map;	
	public PushLayout(NodeProvider provider){
		this.provider = provider;		
		calculate();
	}
	
	private void calculate(){		
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

}
