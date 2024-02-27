package carts.domain.model;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Cart {

  private static volatile Cart INSTANCE;

  private final long id;
  private final CopyOnWriteArrayList<Product> products;

  private Cart(long id, CopyOnWriteArrayList<Product> products) {
    this.id = id;
    this.products = products;
  }

  public static Cart getInstance(long id, CopyOnWriteArrayList<Product> products) {

    if (Objects.nonNull((INSTANCE))) {
      return INSTANCE;
    }
    synchronized (Cart.class) {
      if (Objects.isNull(INSTANCE)) {
        INSTANCE = new Cart(id, products);
      }
      return INSTANCE;
    }
  }

}
