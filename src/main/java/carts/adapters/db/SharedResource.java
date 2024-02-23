package carts.adapters.db;

import carts.domain.model.Cart;
import carts.domain.model.Product;
import java.util.concurrent.CopyOnWriteArrayList;

public class SharedResource {

  private volatile Cart cart;

  public SharedResource(){

  }
  public SharedResource(long id, CopyOnWriteArrayList<Product> products) {
    cart = Cart.getInstance(id, products);
  }

  public void updateCart(Cart cart) {
    this.cart = cart;
  }

  public Cart getCart() {
    return this.cart;
  }


}
