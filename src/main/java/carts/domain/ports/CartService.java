package carts.domain.ports;

import carts.domain.model.Cart;
import java.util.List;

public interface CartService {

  Cart save(Cart cart);

  Cart getById(long id);

  void delete(long id);

  List<Cart> getAll();

}
