package ee.joonasvali.graps.simulator;

import ee.joonasvali.graps.graph.Clickable;
import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.util.Area;

import java.awt.*;
import java.util.Map;

public interface Renderer {
  void draw(Graph graph, Graphics2D g, Point mouse);

  Map<Clickable, Area> getMapping();

  void setSelected(Clickable clickable);

  void addListener(ChangeListener change);

  void removeListener(ChangeListener change);
}
