package ee.joonasvali.graps.util;

public class Min {
  private int min = Integer.MAX_VALUE;

  public void add(int number) {
    min = Math.min(number, min);
  }

  public int get() {
    return min;
  }
}
