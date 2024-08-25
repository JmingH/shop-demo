package com.ming.shopdemo.cart.service;

public interface CartItemService {
    void addProductToCart(Long productId, Long cartId, int quantity);
}
