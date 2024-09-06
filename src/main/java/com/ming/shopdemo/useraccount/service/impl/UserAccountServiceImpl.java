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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository accountRepository;

    private final UserAccountMapper accountMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createUserAccount(RegisterRequest request) {
        boolean isUsernameExist = accountRepository.existsByUsername(request.username());
        boolean isEmailExist = accountRepository.existsByEmail(request.email());
        if (isUsernameExist) {
            throw new DuplicateException("Username already exist");
        }
        if (isEmailExist) {
            throw new DuplicateException("Email already exist");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        UserAccount account = accountMapper.toEntity(request);
        account.setPassword(encodedPassword);
        account.setRole(Role.ROLE_USER);

        accountRepository.save(account);
    }

    @Override
    public UserAccountDto findByUuid(UUID uuid) {
        UserAccount account = accountRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        return accountMapper.toDto(account);
    }

    @Override
    public Page<UserAccountDto> findAllUserAccount(String username, Pageable pageable) {
        return accountRepository.findAllByUsernameIsContainingIgnoreCase(username, pageable).map(accountMapper::toDto);
    }
}
