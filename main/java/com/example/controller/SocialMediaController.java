package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
public class SocialMediaController {
    
    @Autowired
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
    
    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id) {
        return messageService.deleteMessageById(message_id);
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        return messageService.updateMessageById(message_id, message.getMessageText());
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int account_id) {
        List<Message> message1 = messageService.getMessageByAccountID(account_id);
        return ResponseEntity.status(HttpStatus.OK).body(message1);
    }
}