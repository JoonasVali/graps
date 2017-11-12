package ee.joonasvali.graps.edges;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;

import java.util.HashSet;
import java.util.Set;

public class BreakpointManager {
  private Graph graph;
  private BreakpointPlacer placer;

  public BreakpointManager(Graph graph, PathCalculatorFactory calculatorFactory) {
    this(graph, new StandardPlacer(graph, calculatorFactory));
  }

  public BreakpointManager(Graph graph, BreakpointPlacer placer) {
    this.graph = graph;
    this.placer = placer;
  }

  public void makeBreakPoints() {
    Set<Port> processed = new HashSet<Port>();
    for (Node node : graph.getNodes()) {
      for (Port p : node.getPorts()) {
        if (processed.add(p)) {
          if (p.getPort() != null) {
            processed.add(p.getPort());
            p.clearBreakpoints();
            placer.place(p);
          }
        }
      }
    }
  }

  public void clearBreakpoints() {
    for (Node node : graph.getNodes()) {
      for (Port p : node.getPorts()) {
        p.clearBreakpoints();
      }
    }
  }


}

