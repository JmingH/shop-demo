package com.ming.shopdemo.useraccount.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank String username,
    @NotBlank String password,
    @NotBlank @Email String email,
    @NotBlank String phoneNumber
) {
}
