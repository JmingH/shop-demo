package com.ming.shopdemo.useraccount.repository;

import com.ming.shopdemo.common.exception.NotFoundException;
import com.ming.shopdemo.useraccount.model.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void givenUsernameAndPageable_whenFindAllUser_thenReturnUserAccountsPage() {
        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        Pageable pageable = PageRequest.of(0, 5, sort);
        String username = "user";

        Page<UserAccount> userAccounts = userAccountRepository.findAllByUsernameIsContainingIgnoreCase(username, pageable);

        assertEquals(pageable.getPageSize(), userAccounts.getSize());
        assertEquals(userAccounts.getNumber(), pageable.getPageNumber());
        assertEquals(userAccounts.getSort(), pageable.getSort());
        assertTrue(userAccounts.hasContent()
                && userAccounts.getContent().stream().anyMatch(user -> user.getUsername().contains(username)));

    }

    @Test
    void givenEmptyUsernameAndPageable_whenFindAllUser_thenReturnEmpty() {
        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        Pageable pageable = PageRequest.of(0, 5, sort);
        String username = "   ";

        Page<UserAccount> userAccounts = userAccountRepository.findAllByUsernameIsContainingIgnoreCase(username, pageable);

        assertFalse(userAccounts.hasContent());

    }

    @Test
    void givenUsername_whenFindUser_thenReturnUserAccount() {
        String username = "user1";

        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        assertEquals(userAccount.getUsername(), username);
    }

    @Test
    void givenEmptyUsername_whenFindUser_thenThrowNotFoundException() {
        String username = "   ";

        assertThrows(NotFoundException.class, () -> userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found!")));
    }

    @Test
    void givenUsername_whenExistsUser_thenReturnTrue() {
        String username = "user1";

        boolean exists = userAccountRepository.existsByUsername(username);

        assertTrue(exists);
    }

    @Test
    void givenEmptyUsername_whenExistsUser_thenReturnFalse() {
        String username = "  ";

        boolean exists = userAccountRepository.existsByUsername(username);

        assertFalse(exists);
    }

    @Test
    void givenEmail_whenExistsUser_thenReturnTrue() {
        String email = "user1@example.com";

        boolean exists = userAccountRepository.existsByEmail(email);

        assertTrue(exists);
    }

    @Test
    void givenEmptyEmail_whenExistsUser_thenReturnFalse() {
        String email = " ";

        boolean exists = userAccountRepository.existsByEmail(email);

        assertFalse(exists);
    }
}