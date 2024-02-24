package carts.adapters.api;


import carts.domain.model.Cart;
import carts.domain.ports.CartService;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository storage;

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

  @Override
  public List<Cart> getAll() {
    return storage.getCarts();
  }

}
