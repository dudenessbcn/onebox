package carts.adapters.api;


import carts.domain.exceptions.UnsupportedCartOperationException;
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
  private CartRepository repository;

  public Cart save(Cart cart) {
    boolean cartExists = Objects.nonNull(repository.findById(cart.getId()));
    return cartExists ? repository.addProducts(cart) : repository.addCart(cart);
  }

  public Cart getById(long id) {
    return repository.findById(id);
  }

  @Override
  public void delete(long id) throws UnsupportedCartOperationException {
    Cart cart = repository.findById(id);
    if (Objects.nonNull(cart)) {
      repository.remove(cart.getId());
    } else {
      throw new UnsupportedCartOperationException();
    }
  }

  @Override
  public List<Cart> getAll() {
    return repository.getCarts();
  }

}
