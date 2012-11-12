package ee.joonasvali.graps.simulator;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ee.joonasvali.graps.generator.Generator;
import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.layout.Layout;
import ee.joonasvali.graps.layout.OrthogonalLayout;

public class Simulator {	
	private JFrame frame;	
	private Dimension size = new Dimension(700, 700);
	
	private static int NODES = 5;
	private static int PORTS = 15;
	private static int CANVAS_SIZE = 600;
	
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
	        Generator gen = new Generator(NODES, PORTS, CANVAS_SIZE);
	        Renderer renderer = new SimpleRenderer();	        
	        Graph graph = gen.generate();	        
	        SimulatorCanvas canvas = new SimulatorCanvas(graph, renderer, new Dimension(CANVAS_SIZE, CANVAS_SIZE));
	        MouseListener listener = getMouseListener(renderer, canvas);
	        canvas.addMouseListener(listener);
	        renderer.addListener(getRepaintListener(canvas));
	        sim.frame.add(canvas);        
	        sim.frame.setVisible(true);	        
	        Layout layout = new OrthogonalLayout(graph);
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
