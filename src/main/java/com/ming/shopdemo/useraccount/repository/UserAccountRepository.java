package com.ming.shopdemo.useraccount.repository;

import com.ming.shopdemo.useraccount.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    Page<UserAccount> findAllByUsernameIsContainingIgnoreCase(String username, Pageable pageable);

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByUuid(UUID uuid);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
