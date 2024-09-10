package com.ming.shopdemo.auth.service.impl;

import com.ming.shopdemo.auth.dto.AuthDto;
import com.ming.shopdemo.auth.service.AuthService;
import com.ming.shopdemo.auth.service.JwtService;
import com.ming.shopdemo.useraccount.model.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthDto login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthDto(
                jwtService.generateToken((UserDetails) authentication.getPrincipal())
        );
    }
}
