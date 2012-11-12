package ee.joonasvali.graps.simulator;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import ee.joonasvali.graps.graph.Graph;

class SimulatorCanvas extends Canvas{	
  private static final long serialVersionUID = 1L;
  private Renderer renderer;
  private Graph graph;
  private Dimension size;
  private Image image; 
	private Graphics graphics;
      
	public SimulatorCanvas(Graph graph, Renderer renderer, Dimension size) {
    this.graph = graph;
    this.renderer = renderer;
    this.size = size;
    this.setSize(size);    
    Timer animationTimer = new Timer();
		animationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 100, 10);
	
  }	
	
	public Image getImage(){
		return image;
	}
	
	/**
	 * Creates the image and corresponding graphics.
	 */
	public void initGraphics(){
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
	}
	
	/**
	 * Draws the string to a new place.
	 */
	@Override
	public void update(Graphics g) {			
		if (image == null) {
			initGraphics();
		}
		graphics.clearRect(0, 0, getWidth(), getHeight());				
		renderer.draw(graph, (Graphics2D)graphics, size);
		g.drawImage(image, 0, 0, this);
	}
	
}
