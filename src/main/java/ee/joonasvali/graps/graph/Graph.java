package ee.joonasvali.graps.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Graph {
  ArrayList<Node> nodes;

  public Graph(Collection<Node> nodes) {
    this.nodes = new ArrayList<>(nodes);
  }

  public LinkedList<Node> getNodes() {
    return new LinkedList<>(nodes);
  }

  public void setNodes(Collection<Node> nodes) {
    this.nodes = new ArrayList<>(nodes);
  }

  @Override
  public String toString() {
    StringBuilder strb = new StringBuilder();
    for (Node n : nodes) {
      strb.append("Node (").append(n.getWidth()).append(":").append(n.getHeight()).append("): \n");
      strb.append(n);
    }
    return strb.toString();
  }
}
