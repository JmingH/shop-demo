package com.ming.shopdemo.useraccount.model.dto;

import com.ming.shopdemo.useraccount.model.constants.Role;

import java.time.LocalDateTime;

public record UserAccountDto(
    String username,
    String email,
    String phoneNumber,
    Role role,
    LocalDateTime createdDateTime,
    LocalDateTime lastModifiedDateTime
) {
}
