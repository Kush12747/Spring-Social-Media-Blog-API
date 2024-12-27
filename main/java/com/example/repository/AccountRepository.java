package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findAccountByUsername(String username);
    public Optional<Account> findAccountByUsernameAndPassword(String username, String password);
}