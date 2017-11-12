package ee.joonasvali.graps.graph;

import java.util.Collection;
import java.util.LinkedList;

public class Graph {
  LinkedList<Node> nodes;

  public Graph(Collection<Node> nodes) {
    this.nodes = new LinkedList<Node>(nodes);
  }

  public LinkedList<Node> getNodes() {
    return new LinkedList<Node>(nodes);
  }

  public void setNodes(Collection<Node> nodes) {
    this.nodes = new LinkedList<Node>(nodes);
  }

  @Override
  public String toString() {
    StringBuilder strb = new StringBuilder();
    for (Node n : nodes) {
      strb.append("Node (" + n.getWidth() + ":" + n.getHeight() + "): \n");
      strb.append(n);
    }
    return strb.toString();
  }
}
