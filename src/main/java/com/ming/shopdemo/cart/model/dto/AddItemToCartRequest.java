package com.ming.shopdemo.cart.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddItemToCartRequest(
        @NotNull long productId,
        @Positive int quantity
) {
}
