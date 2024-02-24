package carts.adapters.api;

import carts.domain.model.Cart;
import carts.domain.ports.CartService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {

  @Autowired
  private CartService cartService;

  @RequestMapping("/hello-world")
  public @ResponseBody String getHelloWorld() {
    return "Hello, world";
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cart> getCartById(@PathVariable("id") long id) {
    Cart cart = cartService.getById(id);
    return Objects.nonNull(cart) ?
        new ResponseEntity<Cart>(cart, HttpStatus.OK)
        : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/")
  public ResponseEntity<List<Cart>> getAllCarts() {
    List<Cart> carts = cartService.getAll();
    return new ResponseEntity<List<Cart>>(carts, HttpStatus.OK);
  }

@PostMapping("/")
public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) {
  Cart savedCart = cartService.save(cart);
  return Objects.nonNull(savedCart) ?
      new ResponseEntity<Cart>(savedCart, HttpStatus.CREATED)
      : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}

@PutMapping("/{id}")
public ResponseEntity<Cart> updateCart(@PathVariable("id") long id, @RequestBody Cart cart) {
  Cart notUpdated = Cart.getInstance(id, cart.getProducts());
  Cart updatedCart = cartService.save(notUpdated);
  return Objects.nonNull(updatedCart) ?
      new ResponseEntity<Cart>(updatedCart, HttpStatus.CREATED)
      : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}

@DeleteMapping("/{id}")
public ResponseEntity<Cart> deleteCart(@PathVariable("id") long id) {
  cartService.delete(id);
  return new ResponseEntity<>(null, HttpStatus.OK);
}
}
