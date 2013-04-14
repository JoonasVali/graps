package ee.joonasvali.graps.simulator;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import ee.joonasvali.graps.edges.BreakpointManager;
import ee.joonasvali.graps.edges.CornerPathCalculator;
import ee.joonasvali.graps.generator.Generator;
import ee.joonasvali.graps.graph.Graph;
import ee.joonasvali.graps.layout.forcelayout.ForceLayout;

public class Simulator {	
	private JFrame frame;	
	private Dimension size = new Dimension(2000, 2000);
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel controlPanel = new JPanel(new FlowLayout());
	private JButton runButton = new JButton("run/pause");
	private JButton addBreakpointsButton = new JButton("Add Breakpoints");
	private JScrollPane scroll;
	private ForceLayout layout;
	private BreakpointManager bpManager;
	
	private static int NODES = 30;
	private static int PORTS = 100;
	
	private ActionListener runAction = new ActionListener() {			
		public void actionPerformed(ActionEvent e) {				
			bpManager.clearBreakpoints();
			layout.getConfiguration().setPause(!layout.getConfiguration().isPause());
			addBreakpointsButton.setEnabled(layout.getConfiguration().isPause());
		}
	};
	
	private ActionListener breakpointsAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	    bpManager.makeBreakPoints();	    
    }		
	};
	
	
	public Simulator(Graph graph, boolean exitOnClose){
		frame = new JFrame();		
		
		if(exitOnClose){
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		frame.setSize(size);
		
		Renderer renderer = new SimpleRenderer();	        
    
    layout = new ForceLayout();    
    layout.execute(graph);	        
    
    
    SimulatorCanvas canvas = new SimulatorCanvas(graph, renderer, new Dimension(2000, 2000));
    MouseListener listener = getMouseListener(renderer, canvas);
    canvas.addMouseListener(listener);
    renderer.addListener(getRepaintListener(canvas));	        
    bpManager = new BreakpointManager(graph, CornerPathCalculator.getFactory());
    
    this.scroll = new JScrollPane(canvas);    
        
    runButton.addActionListener(runAction);
    addBreakpointsButton.addActionListener(breakpointsAction);
    
    controlPanel.add(runButton);    
    controlPanel.add(addBreakpointsButton);
    addBreakpointsButton.setEnabled(false);
    
    
    mainPanel.add(this.controlPanel, BorderLayout.NORTH);
    mainPanel.add(this.scroll, BorderLayout.CENTER);   
    
    this.frame.add(mainPanel);
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
