package com.ming.shopdemo.cart.service.impl;

import com.ming.shopdemo.cart.model.mapper.CartMapper;
import com.ming.shopdemo.cart.repository.CartItemRepository;
import com.ming.shopdemo.cart.repository.CartRepository;
import com.ming.shopdemo.cart.service.CartService;
import com.ming.shopdemo.product.model.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceImplTest {
    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private CartMapper cartMapper;

    @MockBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @Test
    void addItemToCart() {


    }

    @Test
    void getCart() {
    }

    @Test
    void updateCartItemQuantity() {
    }

    @Test
    void deleteCartItemById() {
    }

    @Test
    void clearCart() {
    }
}