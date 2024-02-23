package carts.adapters.db;

import carts.domain.model.Cart;
import lombok.Getter;

@Getter
public class Consumer implements Runnable {

  private final SharedResource sharedResource;
  private Cart cart;

  public Consumer() {
    sharedResource = new SharedResource();
  }
  @Override
  public void run() {
    cart = sharedResource.getCart();
  }
}

