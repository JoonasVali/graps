package ee.joonasvali.graps.generator;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.graph.Port;
import ee.joonasvali.graps.util.GraphUtil;

public class Generator {
	private int nodes;
  private int ports;
  private int canvasSize;
	private static int NODE_SIZE = 30; 
  
	public Generator(int nodes, int ports, int canvasSize){
		this.canvasSize = canvasSize;
		this.nodes = nodes;
		this.ports = ports;
	}
	
	public Graph generate(){
		return new Graph(generateNodes());
	}
	
	private LinkedList<Node> generateNodes(){
		LinkedList<Node> res = new LinkedList<Node>();
		for(int i = 0; i < nodes; i++){
			Node node = new Node(new Point(GraphUtil.ran(canvasSize),GraphUtil.ran(canvasSize)), new Point(NODE_SIZE,NODE_SIZE));
			res.add(node);
			for(Port p : node.getOpenPorts()){
				p.setNode(node);
			}
		}		
		LinkedList<Port> ports = generatePorts();
		for(Port p: ports){
			Node n = getRandom(res);
			n.addPort(p);
			p.setLocation(new Point(GraphUtil.ran(n.getWidth()), GraphUtil.ran(n.getHeight())));
			p.setNode(n);
		}
		return res;
	}

	private LinkedList<Port> generatePorts() {
		LinkedList<Port> res = new LinkedList<Port>();
		for(int i = 0; i < ports; i+=2){
			Pair p = new Pair();
			res.add(p.portA);
			res.add(p.portB);
		}
	  return res;
  }
	
	private static <T> T getRandom(List<T> list){
		return list.get((int)(Math.random()*list.size()));
	}
	
	class Pair{
		private Port portA, portB;
		
		private Pair(){
			this.portA = new Port(new Point(0,0));
			this.portB = new Port(new Point(0,0));
			portA.setPort(portB);
			portB.setPort(portA);
		}
	}
}
