package ee.joonasvali.graps.layout;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.layout.forcelayout.UpdateListener;

public interface Layout {	
	public void execute(Graph graph);
	public void stop();
	public void addListener(UpdateListener listener);
}
