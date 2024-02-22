package carts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CartMainApplication {

  public static void main(String[] args) {
    SpringApplication.run(CartMainApplication.class, args);
    log.info("*");
    log.info( "*********************************#########################********************************");
    log.info( "*********************************#  E-COMMERCE STARTING! #********************************");
    log.info( "*********************************#########################********************************");
    log.info("*");
  }
}
