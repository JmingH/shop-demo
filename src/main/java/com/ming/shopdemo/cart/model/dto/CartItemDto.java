package com.ming.shopdemo.cart.model.dto;

import com.ming.shopdemo.product.model.dto.ProductDto;

public record CartItemDto(
        Long id,
        ProductDto productDto,
        int quantity
) {
}
