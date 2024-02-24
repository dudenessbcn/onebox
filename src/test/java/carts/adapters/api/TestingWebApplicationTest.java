package carts.adapters.api;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import carts.domain.model.Cart;
import carts.domain.model.Product;
import carts.domain.ports.CartService;
import com.google.gson.Gson;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
public class TestingWebApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CartService cartService;

  @Test
  void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/hello-world")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("world")));
  }

  @Test
  void shouldSaveCart() throws Exception {
    CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<>();
    Product product = new Product(1, "coca-cola", 2.5);
    products.add(product);
    Cart cart = Cart.getInstance(1, products);
    Gson gson = new Gson();
    String json = gson.toJson(cart, Cart.class);
    when(cartService.save(any())).thenReturn(cart);

    this.mockMvc.perform(post("/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }
}
