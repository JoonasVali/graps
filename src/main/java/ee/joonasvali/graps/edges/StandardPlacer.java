package ee.joonasvali.graps.edges;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

import java.awt.*;

public class StandardPlacer implements BreakpointPlacer {
  private static final int OUT_MARGIN = 10;
  private Graph graph;
  private PathCalculatorFactory calculatorFactory;

  public StandardPlacer(Graph graph, PathCalculatorFactory calculatorFactory) {
    this.graph = graph;
    this.calculatorFactory = calculatorFactory;
  }

  public void place(Port p) {
    Point a = directOutOfArea(p.getPort(), p.getPort().getAbsolutes());
    Point b = directOutOfArea(p, p.getAbsolutes());

    try {
      CollisionMap map = new CollisionMap(graph, p);
      p.addBreakpoint(a);
      calculatorFactory.getPathCalculator(p, map, a, b, OUT_MARGIN).calculatePath();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    // This must be added as last breakpoint
    p.addBreakpoint(b);
  }

  public static Point directOutOfArea(Port p, Point absolutes) {
    boolean isRight = false;
    boolean isDown = false;
    Node node = p.getNode();
    int comparatorX, comparatorY;

    if (absolutes.x - node.getLocation().x > node.getLocation().x + node.getWidth() - absolutes.x) {
      isRight = true;
      comparatorX = (node.getLocation().x + node.getWidth()) - absolutes.x;
    } else {
      comparatorX = absolutes.x - node.getLocation().x;
    }

    if (absolutes.y - node.getLocation().y > node.getLocation().y + node.getHeight() - absolutes.y) {
      isDown = true;
      comparatorY = (node.getLocation().y + node.getHeight()) - absolutes.y;
    } else {
      comparatorY = absolutes.y - node.getLocation().y;
    }

    Point out = new Point(absolutes);
    if (comparatorX < comparatorY) {
      if (isRight) {
        out.x = absolutes.x + (node.getLocation().x + node.getWidth() - absolutes.x) + OUT_MARGIN;
      } else {
        out.x = absolutes.x - (absolutes.x - node.getLocation().x) - OUT_MARGIN;
      }
    } else {
      if (isDown) {
        out.y = absolutes.y + (node.getLocation().y + node.getHeight() - absolutes.y) + OUT_MARGIN;
      } else {
        out.y = absolutes.y - (absolutes.y - node.getLocation().y) - OUT_MARGIN;
      }
    }

    return out;
  }
}
