package ee.joonasvali.graps.layout.pushlayout;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;

import java.util.Collection;

abstract class NodeProvider extends Graph {
  public NodeProvider(Collection<Node> nodes) {
    super(nodes);
  }

  public abstract boolean hasNext();

  public abstract Node next();
}
