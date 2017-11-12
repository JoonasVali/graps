package ee.joonasvali.graps.simulator;

import ee.joonasvali.graps.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

class SimulatorCanvas extends Canvas {
  private static final long serialVersionUID = 1L;
  private Renderer renderer;
  private Graph graph;
  private Image image;
  private Graphics graphics;

  public SimulatorCanvas(Graph graph, Renderer renderer, Dimension size) {
    this.graph = graph;
    this.renderer = renderer;
    this.setSize(size);
    this.setBackground(new Color(200, 200, 200));
    System.out.println(this.getWidth());
    System.out.println(this.getHeight());
    Timer animationTimer = new Timer();
    animationTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        repaint();
      }
    }, 100, 10);

  }

  public Image getImage() {
    return image;
  }

  /**
   * Creates the image and corresponding graphics.
   */
  public void initGraphics() {
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
    Point mouse = MouseInfo.getPointerInfo().getLocation();
    SwingUtilities.convertPointFromScreen(mouse, this);
    graphics.clearRect(0, 0, getWidth(), getHeight());
    renderer.draw(graph, (Graphics2D) graphics, mouse);
    g.drawImage(image, 0, 0, this);
  }

}
