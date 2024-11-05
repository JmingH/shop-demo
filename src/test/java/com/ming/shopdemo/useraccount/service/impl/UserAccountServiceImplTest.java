package com.ming.shopdemo.useraccount.service.impl;

import com.ming.shopdemo.common.exception.DuplicateException;
import com.ming.shopdemo.common.exception.NotFoundException;
import com.ming.shopdemo.useraccount.model.UserAccount;
import com.ming.shopdemo.useraccount.model.constants.Role;
import com.ming.shopdemo.useraccount.model.dto.RegisterRequest;
import com.ming.shopdemo.useraccount.model.dto.UserAccountDto;
import com.ming.shopdemo.useraccount.model.mapper.UserAccountMapper;
import com.ming.shopdemo.useraccount.repository.UserAccountRepository;
import com.ming.shopdemo.useraccount.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserAccountServiceImplTest {

    @Autowired
    private UserAccountService userAccountService;

    @MockBean
    private UserAccountRepository accountRepository;

    @MockBean
    private UserAccountMapper accountMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void givenExistingUsername_whenCreateUserAccount_thenThrowDuplicateException() {
        RegisterRequest request = new RegisterRequest(
                "user1",
                "user1@example.com",
                "password123",
                "1234567890");
        when(accountRepository.existsByUsername(request.username())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> userAccountService.createUserAccount(request));
    }

    @Test
    void givenExistingEmail_whenCreateUserAccount_thenThrowDuplicateException() {
        RegisterRequest request = new RegisterRequest(
                "user1",
                "user1@example.com",
                "password123",
                "1234567890"
        );
        when(accountRepository.existsByUsername(request.username())).thenReturn(false);
        when(accountRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> userAccountService.createUserAccount(request));
    }

    @Test
    void givenRegisterRequest_whenCreateUserAccount_thenCreatedUserAccount() {
        RegisterRequest request = new RegisterRequest(
                "user3",
                "user3@example.com",
                "password123",
                "0987654321"
        );
        UserAccount account = new UserAccount();

        when(accountRepository.existsByUsername(request.username())).thenReturn(false);
        when(accountRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(accountMapper.toEntity(request)).thenReturn(account);

        userAccountService.createUserAccount(request);

        assertEquals("encodedPassword", account.getPassword());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void givenUuid_whenFindUserAccount_thenReturnUserAccountDto() {
        UUID uuid = UUID.randomUUID();
        UserAccount account = new UserAccount();
        UserAccountDto accountDto = new UserAccountDto(
                uuid,
                "user1",
                "user1@example.com",
                "1234567890",
                Role.ROLE_USER,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
        );
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        UserAccountDto result = userAccountService.findByUuid(uuid);

        assertNotNull(result);
        assertEquals(accountDto, result);
    }

    @Test
    void givenInvalidUuid_whenFindUserAccount_thenThrowNotFoundException() {
        UUID uuid = UUID.randomUUID();
        when(accountRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userAccountService.findByUuid(uuid));
    }

    @Test
    void givenUsername_whenFindAllUserAccount_thenReturnUserAccountPage() {
        UUID uuid = UUID.randomUUID();
        String username = "user";
        Pageable pageable = PageRequest.of(0, 10);
        UserAccount account = new UserAccount();
        UserAccountDto accountDto = new UserAccountDto(
                uuid,
                "user1",
                "user1@example.com",
                "1234567890",
                Role.ROLE_USER,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
        );
        Page<UserAccount> accountPage = new PageImpl<>(Collections.singletonList(account), pageable, 1);
        when(accountRepository.findAllByUsernameIsContainingIgnoreCase(username, pageable)).thenReturn(accountPage);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        Page<UserAccountDto> result = userAccountService.findAllUserAccount(username, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(accountDto, result.getContent().get(0));
    }

    @Test
    void givenNonExistentUsername_whenFindAllUserAccount_thenReturnEmptyPage() {
        String username = "nonexistent";
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserAccount> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(accountRepository.findAllByUsernameIsContainingIgnoreCase(username, pageable)).thenReturn(emptyPage);

        Page<UserAccountDto> result = userAccountService.findAllUserAccount(username, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}