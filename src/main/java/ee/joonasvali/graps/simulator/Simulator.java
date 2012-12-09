package ee.joonasvali.graps.simulator;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import ee.joonasvali.graps.generator.Generator;
import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.layout.Layout;
import ee.joonasvali.graps.layout.forcelayout.ForceLayout;
import ee.joonasvali.graps.layout.pushlayout.ForeignNodeProvider;
import ee.joonasvali.graps.layout.pushlayout.NodeProvider;
import ee.joonasvali.graps.layout.pushlayout.PushLayout;
import ee.joonasvali.graps.util.GraphUtil;

public class Simulator {	
	private JFrame frame;	
	private Dimension size = new Dimension(2000, 2000);
	private JScrollPane scroll;
	
	private static int NODES = 10;
	private static int PORTS = 20;	
	
	public Simulator(){
		frame = new JFrame();		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size);
	}
	
	
	
	public static void main(String[] args) {		
	  try {
	    SwingUtilities.invokeAndWait(new Runnable(){
	    	public void run() {	    		
	        Simulator sim = new Simulator();
	        
	        Generator gen = new Generator(NODES, PORTS, 100 /* Obsolete MAX pos */);
	        Renderer renderer = new SimpleRenderer();	        
	        Graph graph = gen.generate();	        
//	        NodeProvider provider = new ForeignNodeProvider(graph);
	        Layout layout = new ForceLayout(graph, new Point(500, 500));	        
	        graph.assign(layout, 1);	        
	        SimulatorCanvas canvas = new SimulatorCanvas(graph, renderer, new Dimension(2000, 2000));
	        MouseListener listener = getMouseListener(renderer, canvas);
	        canvas.addMouseListener(listener);
	        renderer.addListener(getRepaintListener(canvas));	        
	        sim.scroll = new JScrollPane(canvas);
	        sim.frame.add(sim.scroll);
	        sim.frame.setVisible(true);        
	      }

				private ChangeListener getRepaintListener(final Canvas canvas) {	        
	        return new ChangeListener(){
						public void onChange(Object src) {
	            canvas.repaint();
            }	        	
	        };
        }

				private MouseListener getMouseListener(Renderer renderer, Canvas canvas) {
	        return new SimulatorMouseListener(renderer,canvas);
        }	  	
	    });
    }
    catch (InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
  }	
}
