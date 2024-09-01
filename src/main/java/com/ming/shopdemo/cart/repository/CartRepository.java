package com.ming.shopdemo.cart.repository;

import com.ming.shopdemo.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

}
