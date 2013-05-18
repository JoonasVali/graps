package ee.joonasvali.graps.layout;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.layout.forcelayout.UpdateListener;

public interface Layout {	
	public final static String EXCLUDE = "exclude_node"; 
	public void execute(Graph graph);
	public void stop();
	public void addListener(UpdateListener listener);
	public void removeListener(UpdateListener listener);
	public LayoutConfiguration getConfiguration();
}
