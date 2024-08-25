package com.ming.shopdemo.cart.model.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCartItemQuantityRequest(
        @PositiveOrZero int quantity
) {
}
