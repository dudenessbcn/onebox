package carts.adapters.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
//@ComponentScan({
//    "carts.adapters.api.CartController",
//    "carts.adapter.api.CartServiceImpl"
//})
@EnableScheduling
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
