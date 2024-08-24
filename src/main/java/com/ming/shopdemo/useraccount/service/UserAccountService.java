package com.ming.shopdemo.useraccount.service;

import com.ming.shopdemo.useraccount.model.dto.RegisterRequest;
import com.ming.shopdemo.useraccount.model.dto.UserAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserAccountService {
    void createUserAccount(RegisterRequest request);

    UserAccountDto findByUuid(UUID username);

    Page<UserAccountDto> findAllUserAccount(Pageable pageable);
}
