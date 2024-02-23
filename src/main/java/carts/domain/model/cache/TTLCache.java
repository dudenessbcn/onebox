package carts.domain.model.cache;


public interface TTLCache {

  public void put(Long id, Object cart, long timeToLiveSeconds);

  public Object get(Long id);

  public boolean remove(Object key);

  public Object removeAndGet(Object key);
}
