package com.ming.shopdemo.cart.repository;

import com.ming.shopdemo.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserAccountUuid(UUID userAccountUuid);


}
