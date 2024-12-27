package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository ar) {
        this.accountRepository = ar;
    }

    //creates the user account with given account restrictions
    public ResponseEntity<Account> createAccount(Account account) {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (optionalAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(accountRepository.save(account));
    }

    //lets user login
    public ResponseEntity<Account> loginAccount(Account account) {

        Optional<Account> optionalAccount = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (optionalAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalAccount.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}