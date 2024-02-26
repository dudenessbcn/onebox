package carts.adapters.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import carts.domain.exceptions.UnsupportedCartOperationException;
import carts.domain.model.Cart;
import carts.domain.model.Product;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

  @InjectMocks
  private CartServiceImpl sut;

  @Mock
  private CartRepository repository;

  @Test
  void givenCartAddCartShouldBeSaved() throws UnsupportedCartOperationException {
    Cart cart = buildCart();

    doReturn(null).when(repository).findById(cart.getId());
    doReturn(cart).when(repository).addCart(cart);

    Cart actual = sut.save(cart);

    assertEquals(cart, actual);
  }

  @Test
  void givenExistingCartAddProductsShouldBeUpdated() throws UnsupportedCartOperationException {
    Cart cart = buildCart();

    doReturn(cart).when(repository).findById(cart.getId());
    doReturn(cart).when(repository).addProducts(cart);

    Cart actual = sut.save(cart);

    assertEquals(cart.getId(), actual.getId());
  }

  @Test
  void givenCartIdFindByIdShouldReturnCart() {
    long id = 1L;
    Cart cart = buildCart();
    doReturn(cart).when(repository).findById(1);

    Cart actual = sut.getById(id);

    assertEquals(id, actual.getId());
  }

  @Test
  void givenCartIdDeleteShouldNotThrowException() throws UnsupportedCartOperationException {
    long id = 1L;

    doNothing().when(repository).remove(id);

    assertDoesNotThrow(() -> sut.delete(id));

  }

  @Test
  void givenCartIdDeleteShouldThrowException() throws UnsupportedCartOperationException {
    long id = 1L;

    doReturn(null).when(repository).findById(id);

    assertThrows(UnsupportedCartOperationException.class, () -> sut.delete(id));

  }

  private static Cart buildCart() {
    CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<>();
    Product product = new Product(1, "coca-cola", 2.5);
    products.add(product);
    return Cart.getInstance(1, products);
  }

}