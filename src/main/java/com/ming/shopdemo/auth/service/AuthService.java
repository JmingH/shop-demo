package com.ming.shopdemo.auth.service;

import com.ming.shopdemo.auth.dto.AuthDto;
import com.ming.shopdemo.useraccount.model.dto.LoginRequest;

public interface AuthService {
    AuthDto login(LoginRequest request);
}
