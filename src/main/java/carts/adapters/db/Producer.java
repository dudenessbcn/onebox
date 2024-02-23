package carts.adapters.db;

import carts.domain.model.Cart;
import lombok.Getter;

@Getter
public class Producer implements Runnable {

  private final SharedResource sharedResource;
  private final Cart cart;

  public Producer(Cart cart) {
    this.cart = cart;
    sharedResource = new SharedResource(cart.getId(), cart.getProducts());
  }

  @Override
  public void run() {
    sharedResource.updateCart(cart);
  }
}
