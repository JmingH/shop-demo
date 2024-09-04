package com.ming.shopdemo.cart.repository;

import com.ming.shopdemo.cart.model.Cart;
import com.ming.shopdemo.cart.model.CartItem;
import com.ming.shopdemo.product.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void givenCartIdAndProductId_whenFindCart_thanReturnCart() {
        Long productId = 1L;
        Long cartId = 1L;

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElse(null);

        assertNotNull(cartItem);
        assertEquals(cartId, cartItem.getCart().getId());
        assertEquals(productId, cartItem.getProduct().getId());
    }

    @Test
    void givenInvalidCartId_whenFindCart_thanReturnCart() {
        Long productId = -1L;
        Long cartId = 1L;

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElse(null);

        assertNull(cartItem);
    }


    @Test
    void givenCartItemIdAndQuantity_whenUpdateQuantity_thanUpdate() {
        Long cartItemId = 1L;
        int quantity = 2;

        cartItemRepository.updateQuantityById(cartItemId, quantity);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElse(null);
        assertNotNull(cartItem);
        assertEquals(cartItemId, cartItem.getId());
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void givenInvalidCartItem_whenUpdateQuantity_thanUpdate() {
        Long cartItemId = -1L;
        int quantity = 2;

        cartItemRepository.updateQuantityById(cartItemId, quantity);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElse(null);
        assertNull(cartItem);
    }


    @Test
    void givenCartItemId_whenDeleteCartItem_thanDelete() {
        Long cartItemId = 5L;

        cartItemRepository.deleteCartItemById(cartItemId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElse(null);
        assertNull(cartItem);
    }

    @Test
    void givenCartId_whenClearCart_thenClear() {
        Long cartId = 1L;

        cartItemRepository.deleteAllByCartId(cartId);

        boolean isDeleted = cartItemRepository.findAll().stream()
                .noneMatch(cartItem -> cartItem.getCart().getId().equals(cartId));

        assertTrue(isDeleted);
    }
}