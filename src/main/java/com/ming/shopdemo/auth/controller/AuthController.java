package com.ming.shopdemo.auth.controller;

import com.ming.shopdemo.auth.dto.AuthDto;
import com.ming.shopdemo.auth.service.AuthService;
import com.ming.shopdemo.useraccount.model.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody @Validated LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
