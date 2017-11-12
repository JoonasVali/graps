package ee.joonasvali.graps.util;

public class Max {
  private int max = Integer.MIN_VALUE;

  public void add(int number) {
    max = Math.max(number, max);
  }

  public int get() {
    return max;
  }
}
