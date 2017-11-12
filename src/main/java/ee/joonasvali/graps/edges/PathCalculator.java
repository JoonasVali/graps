package ee.joonasvali.graps.edges;

import ee.joonasvali.graps.graph.Port;

import java.awt.*;


public abstract class PathCalculator {
  Port port;
  CollisionMap map;
  Point start;
  Point end;
  int margin;

  protected PathCalculator(Port port, CollisionMap map, Point start, Point end, int margin) {
    this.port = port;
    this.map = map;
    this.start = start;
    this.end = end;
    this.margin = margin;
  }

  abstract boolean calculatePath();

  public boolean isAlignedAndDirectAccess(Point a, Point b) {
    if (!isAligned(a, b)) throw new IllegalArgumentException("Points not on the same line " + a + " " + b);
    if (a.x == b.x) {
      int min = Math.min(a.y, b.y);
      int max = Math.max(a.y, b.y);
      for (int i = min; i < max; i++) {
        try {
          if (map.isOccupied(a.x, i)) {
            return false;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          return false;
        }
      }
      return true;
    } else {
      int min = Math.min(a.x, b.x);
      int max = Math.max(a.x, b.x);
      for (int i = min; i < max; i++) {
        try {
          if (map.isOccupied(i, a.y)) {
            return false;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          return false;
        }
      }
      return true;
    }
  }

  public boolean isAligned(Point a, Point b) {
    return (a.x == b.x || a.y == b.y);
  }
}
