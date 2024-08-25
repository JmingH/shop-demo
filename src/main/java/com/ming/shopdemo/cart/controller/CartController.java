package com.ming.shopdemo.cart.controller;

import com.ming.shopdemo.cart.model.dto.AddItemToCartRequest;
import com.ming.shopdemo.cart.model.dto.CartDto;
import com.ming.shopdemo.cart.model.dto.UpdateCartItemQuantityRequest;
import com.ming.shopdemo.cart.service.CartService;
import com.ming.shopdemo.product.model.dto.ProductDto;
import com.ming.shopdemo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<HttpStatus> addProductToCart(@RequestBody @Validated AddItemToCartRequest request) {
        ProductDto productDto = productService.findProductById(request.productId());
        cartService.addItemToCart(productDto, request.quantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart() {
        CartDto cartDto = cartService.getCart();
        return ResponseEntity.ok(cartDto);
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<HttpStatus> updateCartItemQuantity(
            @PathVariable long cartItemId,
            @RequestBody @Validated UpdateCartItemQuantityRequest request) {
        cartService.updateCartItemQuantity(cartItemId, request.quantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<HttpStatus> removeCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItemById(cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }
}
