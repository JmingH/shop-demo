package com.ming.shopdemo.cart.repository;

import com.ming.shopdemo.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = ?2 WHERE c.id = ?1")
    void updateQuantityById(Long cartItemId, int quantity);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.id = ?1")
    void deleteCartItemById(Long id);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cart.id = ?1")
    void deleteAllByCartId(Long cartId);
}
