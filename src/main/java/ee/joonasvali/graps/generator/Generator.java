package ee.joonasvali.graps.generator;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;
import ee.joonasvali.graps.util.GraphUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Generator {
  private int nodes;
  private int ports;
  private int canvasSize;
  private static int NODE_SIZE = 50;

  public Generator(int nodes, int ports, int canvasSize) {
    this.canvasSize = canvasSize;
    this.nodes = nodes;
    this.ports = ports;
  }

  public Graph generate() {
    return new Graph(generateNodes());
  }

  private List<Node> generateNodes() {
    ArrayList<Node> res = new ArrayList<>(nodes);
    for (int i = 0; i < nodes; i++) {
      int nodesizeX = (int) (Math.random() * NODE_SIZE) + NODE_SIZE;
      int nodesizeY = (int) (Math.random() * NODE_SIZE) + NODE_SIZE;
      Node node = new Node(new Point(GraphUtil.ran(canvasSize), GraphUtil.ran(canvasSize)),
          new Point(nodesizeX, nodesizeY));
      res.add(node);
      for (Port p : node.getOpenPorts()) {
        p.setNode(node);
      }
    }
    List<Port> ports = generatePorts();
    for (Port p : ports) {
      Node n = getRandom(res);
      n.addPort(p);
      p.setLocation(new Point(GraphUtil.ran(n.getWidth()), GraphUtil.ran(n.getHeight())));
      p.setNode(n);
    }
    return res;
  }

  private List<Port> generatePorts() {
    List<Port> res = new ArrayList<>();
    for (int i = 0; i < ports; i += 2) {
      Pair p = new Pair();
      res.add(p.portA);
      res.add(p.portB);
    }
    return res;
  }

  private static <T> T getRandom(List<T> list) {
    return list.get((int) (Math.random() * list.size()));
  }

  class Pair {
    private Port portA, portB;

    private Pair() {
      this.portA = new Port(new Point(0, 0));
      this.portB = new Port(new Point(0, 0));
      portA.setPort(portB);
      portB.setPort(portA);
    }
  }
}
