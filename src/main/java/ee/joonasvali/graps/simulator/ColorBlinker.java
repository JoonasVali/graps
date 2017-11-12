package ee.joonasvali.graps.simulator;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ColorBlinker {
  private Color[] colors;
  private int speed;
  private int selected = 0;

  public ColorBlinker(int speed, Color... colors) {
    if (colors.length < 2)
      throw new IllegalArgumentException("Must be at least 2 colors");
    this.colors = colors;
    this.speed = speed;
    Thread thread = new Thread(getRunnable());
    thread.setDaemon(true);
    thread.start();
  }

  public Color getColor() {
    return colors[selected];
  }

  private Runnable getRunnable() {
    return new Runnable() {
      public void run() {
        while (true) {
          try {
            TimeUnit.MILLISECONDS.sleep(speed);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          selected = (selected + 1) % colors.length;
        }
      }
    };
  }
}
