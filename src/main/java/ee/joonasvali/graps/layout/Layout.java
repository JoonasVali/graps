package ee.joonasvali.graps.layout;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.layout.forcelayout.UpdateListener;

public interface Layout {
  String EXCLUDE = "exclude_node";

  void execute(Graph graph);

  void stop();

  void addListener(UpdateListener listener);

  void removeListener(UpdateListener listener);

  LayoutConfiguration getConfiguration();
}
