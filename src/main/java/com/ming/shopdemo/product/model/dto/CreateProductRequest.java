package com.ming.shopdemo.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateProductRequest(
        @NotBlank String name,
        @PositiveOrZero int stock,
        @PositiveOrZero int price,
        String description
) {
}
