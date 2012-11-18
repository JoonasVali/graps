package ee.joonasvali.graps.layout.pushlayout;

import ee.joonasvali.graps.graph.Node;

public interface NodeProvider {
	public boolean hasNext();
	public Node next();
}
