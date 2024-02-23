package carts.adapters.api;

import carts.domain.model.Cart;
import carts.domain.ports.CartService;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cart> getCartById(@PathVariable("id") long id) {
    Cart cart = cartService.getById(id);
    return Objects.nonNull(cart) ?
    new ResponseEntity<Cart>(cart, HttpStatus.OK)
    :  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @PostMapping("/")
  public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) {
    Cart savedCart = cartService.save(cart);
    return Objects.nonNull(savedCart) ?
        new ResponseEntity<Cart>(savedCart, HttpStatus.OK)
        :  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cart> updateCart(@PathVariable("id") long id, @RequestBody Cart cart) {
    Cart notUpdated = Cart.getInstance(id, cart.getProducts());
    Cart updatedCart = cartService.save(notUpdated);
    return Objects.nonNull(updatedCart) ?
        new ResponseEntity<Cart>(updatedCart, HttpStatus.OK)
        :  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Cart> deleteCart(@PathVariable("id") long id) {
    cartService.delete(id);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
