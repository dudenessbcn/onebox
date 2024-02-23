package carts.adapters.cache;

import carts.domain.model.Cart;
import carts.domain.model.cache.TTLCache;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TTL implements TTLCache {

  private Map<Long, Object[]> cache = new HashMap<>();
  @Override
  public void put(Long id, Object cart, long timeToLive) {
    timeToLive = System.currentTimeMillis() + timeToLive * 1000;
    if (id == null) {
      throw new RuntimeException("id must not be null");
    }
    cache.put(id, new Object[]{timeToLive, cart});
  }

  @Override
  public Object get(Long id) {
    if (cache.containsKey(id)) {
      Long expires = (Long) cache.get(id)[0];
      if (expires - System.currentTimeMillis() > 0) {
        return cache.get(id)[1];
      } else {
        remove(id);
      }
    }
    return null;
  }

  @Override
  public boolean remove(Object key) {
    return removeAndGet(key) != null;
  }

  @Override
  public Object removeAndGet(Object key) {
    Object entry = cache.remove(key);
    if (entry != null) {
      return entry;
    }
    return null;
  }
}
