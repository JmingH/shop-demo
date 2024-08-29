package com.ming.shopdemo.useraccount.controller;

import com.ming.shopdemo.useraccount.model.dto.RegisterRequest;
import com.ming.shopdemo.useraccount.model.dto.UserAccountDto;
import com.ming.shopdemo.useraccount.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserAccountController {

    private final UserAccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody @Validated RegisterRequest request) {
        accountService.createUserAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("#uuid == authentication.principal.uuid || hasRole('ADMIN')")
    @GetMapping("/{uuid}")
    public UserAccountDto getUserAccountByUuid(@PathVariable("uuid") UUID uuid) {
        return accountService.findByUuid(uuid);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserAccountDto>> getAllUserAccount(@RequestParam(required = false) String username,
                                                                  @PageableDefault(size = 5, sort = {"username"}) Pageable pageable) {
        Page<UserAccountDto> userList = accountService.findAllUserAccount(username, pageable);
        return ResponseEntity.ok(userList);
    }

}
