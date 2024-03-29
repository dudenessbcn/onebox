package carts.adapters.api;

import carts.domain.model.Cart;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CartRepository {


  private static final Long TEN_MINUTES = 60000L * 10;
  private final CopyOnWriteArrayList<Cart> carts;
  @Getter
  private final ConcurrentHashMap<Long, Long> dispensableCarts;

  public CartRepository() {
    this.carts = new CopyOnWriteArrayList<>();
    this.dispensableCarts = new ConcurrentHashMap<>();
  }


  public Cart addCart(final Cart cart) {
    carts.add(cart);
    log.info("Cart added to storage {}", cart.toString());
    dispensableCarts.put(cart.getId(), System.currentTimeMillis());
    return carts.stream().filter(e -> e.equals(cart)).findAny().orElse(null);
  }

  public Cart findById(long id) {
    return carts.stream().filter(e -> e.getId() == id).findAny().orElse(null);
  }

  public Cart addProducts(Cart cart) {
    remove(cart.getId());
    carts.add(cart);
    log.info("Cart updated from storage {}", cart.toString());
    return cart;
  }

  public void remove(long id) {
    Cart cart = findById(id);
    carts.remove(cart);
    log.info("Cart removed from storage {}", cart.toString());

  }

  @Scheduled(fixedRate = 5, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
  public void scheduledRemove() {
    for (Iterator<Entry<Long, Long>> it =
        dispensableCarts.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry<Long, Long> cart = it.next();
      long currentTime = System.currentTimeMillis();
      if (currentTime - cart.getValue() >= TEN_MINUTES) {
        it.remove();
        remove(cart.getKey());
      }

    }
  }


}
