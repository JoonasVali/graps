package ee.joonasvali.graps.simulator;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import ee.joonasvali.graps.graph.Clickable;
import ee.joonasvali.graps.graph.Node;
import ee.joonasvali.graps.layout.forcelayout.ForceLayout;
import ee.joonasvali.graps.util.Area;
import ee.joonasvali.graps.util.FlagManager;

public class SimulatorMouseListener implements MouseListener{
	// DO NOT ADD OR REMOVE ANYTHING, HANDLED BY RENDERER
	Map<Clickable, Area> mapping;
	Executor executor = Executors.newSingleThreadExecutor();
	Renderer renderer;
	
	private volatile Clickable drag;	
	private Component canvas;
	
	public SimulatorMouseListener(Renderer renderer, Component canvas){		
		this.renderer = renderer;
		this.canvas = canvas;
		this.mapping = renderer.getMapping();		
	}
	
	public void mouseClicked(MouseEvent e) {		
		Clickable selected = getClickable(e.getPoint());
		renderer.setSelected(selected);	  
  }
	
	public Clickable getClickable(Point click){
		Set<Entry<Clickable, Area>> set = mapping.entrySet();		
		for(Entry<Clickable, Area> entry: set){	  	
	  	if(entry.getValue().isInRange(click, 5)){	  		
	  		return entry.getKey();
	  	}
	  }
		return null;
	}

	public void mousePressed(MouseEvent e) {
		Clickable selected = getClickable(e.getPoint());
		renderer.setSelected(selected);
		drag = selected;  
		if(drag instanceof Node){
			FlagManager.getInstance(Node.class).set((Node)drag, ForceLayout.EXCLUDE, true);
		}
		executor.execute(makeRunnable(e.getPoint()));		
  }

	private Runnable makeRunnable(final Point prevClick) {	  
	  return new Runnable(){
			public void run() {
				try{					
					while(drag != null){
						Point mouse = MouseInfo.getPointerInfo().getLocation();						
						SwingUtilities.convertPointFromScreen(mouse, canvas);					
						int xDiff = mouse.x - prevClick.x;
						int yDiff = mouse.y - prevClick.y;
						prevClick.setLocation(mouse);
						Point location = drag.getLocation();
						location.x += xDiff;
						location.y += yDiff;					
						drag.setLocation(location);					
						try {
		          TimeUnit.MILLISECONDS.sleep(10);
	          }
	          catch (InterruptedException e) {	          
		          e.printStackTrace();
	          }
					}
				} catch(NullPointerException e){ }
      }	  	
	  };
  }

	public void mouseReleased(MouseEvent e) {
		FlagManager.getInstance(Node.class).set((Node)drag, ForceLayout.EXCLUDE, false);
	  drag = null;	  
  }	
	
	public void mouseEntered(MouseEvent e) {
	  // TODO Auto-generated method stub
	  
  }

	public void mouseExited(MouseEvent e) {
	  // TODO Auto-generated method stub
	  
  }

}
