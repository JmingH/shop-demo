package com.ming.shopdemo.cart.service;

import com.ming.shopdemo.cart.model.dto.CartDto;
import com.ming.shopdemo.product.model.dto.ProductDto;

public interface CartService {

    void addItemToCart(ProductDto productDto, int quantity);

    CartDto getCart();

    void updateCartItemQuantity(Long cartItemId, int quantity);

    void deleteCartItemById(Long cartItemId);

    void clearCart();
}
