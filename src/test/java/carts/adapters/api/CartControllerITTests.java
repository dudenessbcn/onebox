package carts.adapters.api;

import static org.assertj.core.api.Assertions.assertThat;

import carts.domain.ports.CartService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CartControllerITTests {


  @Autowired
  private CartController controller;

  @Autowired
  private CartService service;

  @Autowired
  private CartRepository repository;


  @Test
  void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }




  /*
  @Test
  void createCart() throws Exception {

    Product product = new Product(1, "producto_1", 10.0);
    CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<>();
    products.add(product);

    Cart cart = Cart.getInstance(1, products);

    mockMvc.perform(post("/")
        .contentType("application/json")
        )

  }
    */
}
