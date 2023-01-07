package com.cosmos.repository;

import com.cosmos.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query(value = "select * from Accounts where email = ?1", nativeQuery = true)
    Account findTaiKhoanByEmail(String email);

    Optional<Account> findByEmail(String email);

}
