package carts.domain.model;


import lombok.Getter;

@Getter
public final class Product {

  private final long id;
  private final String description;
  private final double amount;

  public Product(final long id, final String description, final double amount) {
    this.id = id;
    this.description = description;
    this.amount = amount;
  }
}
