package carts.domain.exceptions;

public class UnsupportedCartOperationException extends Exception {

  public UnsupportedCartOperationException() {
    super("Attempt on cart operation not supported");
  }
}
