package ee.joonasvali.graps.graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node implements Clickable {
  private List<Port> ports;
  private Point location;
  private Point size;

  public Node(Point location, Point size) {
    ports = new ArrayList<>();
    this.location = location;
    this.size = size;
  }

  public Node(List<Port> ports) {
    this.ports = Collections.unmodifiableList(ports);
  }

  public void addPort(Port p) {
    ports.add(p);
  }

  public List<Port> getPorts() {
    return ports;
  }

  public int getWidth() {
    return size.x;
  }

  public int getHeight() {
    return size.y;
  }

  public List<Port> getOpenPorts() {
    List<Port> res = new ArrayList<>();
    for (Port p : ports) {
      if (!p.isOccupied()) {
        res.add(p);
      }
    }
    return res;
  }

  /**
   * @return the location
   */
  public Point getLocation() {
    return new Point(location);
  }

  /**
   * @return the center of the node
   */
  public Point getCenter() {
    return new Point(location.x + size.x / 2, location.y + size.y / 2);
  }

  @Override
  public String toString() {
    StringBuilder strb = new StringBuilder();
    for (Port p : ports) {
      strb.append(p);
    }
    return strb.toString();
  }

  public void setLocation(Point point) {
    location.x = point.x;
    location.y = point.y;
  }
}
