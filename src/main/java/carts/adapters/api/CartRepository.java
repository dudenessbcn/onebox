package carts.adapters.api;

import carts.domain.model.Cart;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Getter
@Slf4j
public class CartRepository {

  private final List<Cart> carts = new ArrayList<>();


  public Cart addCart(final Cart cart) {
    carts.add(cart);
    log.info("Cart added to storage {}", cart);
    scheduledRemove(cart.getId());
    return carts.stream().filter(e -> e.equals(cart)).findAny().orElse(null);
  }

  public Cart findById(long id) {
    return carts.stream().filter(e -> e.getId() == id).findAny().orElse(null);
  }

  public Cart addProducts(Cart cart) {
    Cart updatedCart = findById(cart.getId());
    if (Objects.nonNull(updatedCart)) {
      carts.remove(cart);
      carts.add(updatedCart);
      log.info("Cart updated from storage {}", updatedCart);
    }
    return updatedCart;
  }

  public void remove(long id) {
    Cart cart = findById(id);
    if (Objects.nonNull(cart)) {
      carts.remove(cart);
      log.info("Cart removed from storage {}", cart);
    }
  }

  @Scheduled(initialDelay = 60, timeUnit = TimeUnit.SECONDS)
  public void scheduledRemove(long id) {
    remove(id);
  }
}
