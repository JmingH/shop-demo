package com.ming.shopdemo.useraccount.model.dto;

import com.ming.shopdemo.useraccount.model.constants.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserAccountDto(
        UUID uuid,
        String username,
        String email,
        String phoneNumber,
        Role role,
        LocalDateTime createdDateTime,
        LocalDateTime lastModifiedDateTime
) {
}
