package ee.joonasvali.graps.graph;

import java.awt.*;

public interface Clickable {
  int getWidth();

  int getHeight();

  Point getLocation();

  Point getCenter();

  void setLocation(Point location);
}
