package com.ming.shopdemo.cart.model.dto;

import com.ming.shopdemo.useraccount.model.dto.UserAccountDto;

import java.util.Set;

public record CartDto(
        Long id,
        UserAccountDto userAccountDto,
        Set<CartItemDto> cartItems
) {
}
