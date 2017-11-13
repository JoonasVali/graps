package ee.joonasvali.graps.util;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class FlagManager<T> {
  private static Map<Class<?>, FlagManager<?>> instances = new IdentityHashMap<>();
  private Map<T, Flags> flags = new WeakHashMap<>();

  @SuppressWarnings("unchecked")
  public static <T> FlagManager<T> getInstance(Class<T> clazz) {
    FlagManager<T> manager = (FlagManager<T>) instances.get(clazz);
    if (manager == null) {
      manager = new FlagManager<>();
      instances.put(clazz, manager);
    }
    return manager;
  }

  public synchronized boolean get(T object, String key) {
    return getFlags(object).get(key);
  }

  public synchronized void set(T object, String key, boolean value) {
    getFlags(object).set(key, value);
  }

  private synchronized Flags getFlags(T object) {
    Flags flags = this.flags.get(object);
    if (flags == null) {
      flags = new Flags();
      this.flags.put(object, flags);
    }
    return flags;
  }

  private class Flags {
    public Map<String, Boolean> flags = new HashMap<>();

    public void set(String key, boolean up) {
      flags.put(key, up);
    }

    public boolean get(String key) {
      Boolean val = flags.get(key);
      if (val == null) {
        return false;
      }
      return val;
    }
  }
}
