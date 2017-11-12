package ee.joonasvali.graps.util;

import ee.joonasvali.graps.graph.Clickable;

import java.awt.*;

public class ClickableEdgeUtil {
  public static Point edgeFor(Clickable node, Clickable other) {

    if (contains(node, other.getLocation())) {
      return node.getCenter();
    }

    Point lowerRight = new Point(other.getLocation().x + other.getWidth(), other.getLocation().y + other.getHeight());
    if (contains(node, lowerRight)) {
      return node.getCenter();
    }

    Point lowerLeft = new Point(other.getLocation().x, other.getLocation().y + other.getHeight());
    if (contains(node, lowerLeft)) {
      return node.getCenter();
    }

    Point upperRight = new Point(other.getLocation().x + other.getWidth(), other.getLocation().y);
    if (contains(node, upperRight)) {
      return node.getCenter();
    }

    return edgeFor(node, other.getCenter());
  }

  public static Point edgeFor(Clickable node, Point other) {
    int x1 = node.getCenter().x;
    int x2 = other.x;
    int y1 = node.getCenter().y;
    int y2 = other.y;

    if (contains(node, other)) {
      return other;
    }

    int x = node.getCenter().x, y = node.getCenter().y;
    double angle = angle(x1, x2, y1, y2);
    double alphaAngle = angle(node.getLocation().x, node.getCenter().x, node.getLocation().y, node.getCenter().y);
    double betaAngle = 90 - alphaAngle;
    if (angle > 3 * alphaAngle + 2 * betaAngle && angle <= 3 * alphaAngle + 4 * betaAngle) {

      // UP
      y = node.getLocation().y;
      double alpha = Math.atan((double) (x2 - x1) / (double) (y2 - y1));
      x = (int) (node.getCenter().x - Math.tan(alpha) * (node.getCenter().y - node.getLocation().y));
    } else if (angle > alphaAngle + 2 * betaAngle && angle <= 3 * alphaAngle + 2 * betaAngle) {
      // LEFT
      x = node.getLocation().x;
      double alpha = Math.atan((double) (y2 - y1) / (double) (x2 - x1));
      y = (int) (node.getCenter().y - Math.tan(alpha) * (node.getCenter().x - node.getLocation().x));
    } else if (angle > alphaAngle && angle <= 2 * betaAngle + alphaAngle) {
      // DOWN
      y = node.getLocation().y + node.getHeight();
      double alpha = Math.atan((double) (x2 - x1) / (double) (y2 - y1));
      x = (int) (node.getCenter().x - Math.tan(alpha) * (node.getLocation().y - node.getCenter().y));
    } else {
      // RIGHT
      x = node.getLocation().x + node.getWidth();
      double alpha = Math.atan((double) (y2 - y1) / (double) (x2 - x1));
      y = (int) (node.getCenter().y - Math.tan(alpha) * (node.getLocation().x - node.getCenter().x));
    }

    return new Point(x, y);
  }

  public static boolean contains(Clickable node, Point p) {
    return (node.getLocation().x < p.getX() && node.getLocation().y < p.getY() &&
        node.getLocation().x + node.getWidth() > p.getX() &&
        node.getLocation().y + node.getHeight() > p.getY());
  }

  private static double angle(int x1, int x2, int y1, int y2) {
    return (Math.toDegrees(Math.atan2(y2 - y1, x2 - x1)) + 360) % 360;
  }
}
