package ee.joonasvali.graps.edges;

import ee.joonasvali.graps.graph.Port;

import java.awt.*;

public interface PathCalculatorFactory {
  PathCalculator getPathCalculator(Port port, CollisionMap map, Point start, Point end, int margin);
}
