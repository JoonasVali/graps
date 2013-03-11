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
import ee.joonasvali.graps.layout.forcelayout.UpdateListener;

public class Simulator {	
	private JFrame frame;	
	private Dimension size = new Dimension(2000, 2000);
	private JScrollPane scroll;
	
	private static int NODES = 30;
	private static int PORTS = 100;	
	
	public Simulator(Graph graph, boolean exitOnClose){
		frame = new JFrame();		
		
		if(exitOnClose){
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		frame.setSize(size);
		
		Renderer renderer = new SimpleRenderer();	        
    

		DummyNode.injectAll(graph);
		DummyNode.injectAll(graph);
		
    Layout layout = new ForceLayout();    
    layout.execute(graph);	        
    
    
    SimulatorCanvas canvas = new SimulatorCanvas(graph, renderer, new Dimension(2000, 2000));
    MouseListener listener = getMouseListener(renderer, canvas);
    canvas.addMouseListener(listener);
    renderer.addListener(getRepaintListener(canvas));	        
    this.scroll = new JScrollPane(canvas);
    this.frame.add(this.scroll);
    this.frame.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {		
	  try {
	    SwingUtilities.invokeAndWait(new Runnable(){
	    	public void run() {	    		
	    		Generator gen = new Generator(NODES, PORTS, 100 /* Obsolete MAX pos */);    		
	    		Graph graph = gen.generate();	    			    		
	    		new Simulator(graph, true);	                
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
}
