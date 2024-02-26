package carts.adapters.api;

import carts.domain.model.Cart;
import carts.domain.model.Product;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void greetingShouldReturnDefaultMessage() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/hello-world",
        String.class)).contains("world");
  }

  @Test
  public void cartShouldReturnIsCreatedStatus() throws URISyntaxException {
    ResponseEntity<Cart> result = insertCart();

    assertEquals(201, result.getStatusCodeValue());
  }

  private ResponseEntity<Cart> insertCart() throws URISyntaxException {
    final String baseUrl = "http://localhost:" + port + "/";
    URI uri = new URI(baseUrl);
    Cart cart = buildCart();

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-COM-PERSIST", "true");

    HttpEntity<Cart> request = new HttpEntity<>(cart, headers);

    return this.restTemplate.postForEntity(uri, request, Cart.class);
  }

  @Test
  public void updatedCartShouldReturnIsCreatedStatus() throws URISyntaxException {
    insertCart();
    Cart cart = buildCart();
    final String baseUrl = "http://localhost:" + port + "/" + cart.getId();
    URI uri = new URI(baseUrl);


    HttpHeaders headers = new HttpHeaders();
    headers.set("X-COM-PERSIST", "true");

    HttpEntity<Cart> request = new HttpEntity<>(cart, headers);

    ResponseEntity<Cart> result = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Cart.class);

    assertEquals(201, result.getStatusCodeValue());
  }

  @Test
  public void cartShouldBeDeleted() throws URISyntaxException {
    ResponseEntity<Cart> response = insertCart();
    final String baseUrl = "http://localhost:" + port + "/" + Objects.requireNonNull(
        response.getBody()).getId();
    URI uri = new URI(baseUrl);

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-COM-PERSIST", "true");

    HttpEntity<Cart> request = new HttpEntity<>(null, headers);

    ResponseEntity<Cart> result = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, Cart.class);

    assertDoesNotThrow(()-> result);
  }

  @Test
  public void cartShouldBeReturnedIsOkStatus() throws URISyntaxException {
    ResponseEntity<Cart> response = insertCart();
    final String baseUrl = "http://localhost:" + port + "/" + Objects.requireNonNull(
        response.getBody()).getId();
    URI uri = new URI(baseUrl);


    ResponseEntity<Cart> result = this.restTemplate.getForEntity(uri, Cart.class);

    assertEquals(200, result.getStatusCodeValue());
  }

  @Test
  public void getAllCartsShouldReturnAllCarts() throws URISyntaxException {
    insertCart();
    final String baseUrl = "http://localhost:" + port + "/";
    URI uri = new URI(baseUrl);

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<Cart> request = new HttpEntity<>(null, headers);
    ResponseEntity<List<Cart>> result =
        this.restTemplate.exchange(uri, HttpMethod.GET, request,  new ParameterizedTypeReference<List<Cart>>(){});

    assertEquals(200, result.getStatusCodeValue());
  }

  private static Cart buildCart() {
    CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<>();
    Product product = new Product(1, "coca-cola", 2.5);
    products.add(product);
    return Cart.getInstance(1, products);
  }
}
