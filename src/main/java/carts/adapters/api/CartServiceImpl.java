package carts.adapters.api;


import carts.domain.model.Cart;
import carts.domain.ports.CartService;
import java.util.Objects;
import lombok.Getter;
import org.springframework.stereotype.Component;
@Getter
@Component
public class CartServiceImpl implements CartService {

  private final CartRepository storage;

  public CartServiceImpl(CartRepository storage) {
    this.storage = storage;
  }

  public Cart save(Cart cart) {
    boolean cartExists = Objects.nonNull(storage.findById(cart.getId()));
    return cartExists ? storage.addProducts(cart) : storage.addCart(cart);
  }

  public Cart getById(long id) {
    return storage.findById(id);
  }

  @Override
  public void delete(long id) {
    storage.remove(id);
  }

}
