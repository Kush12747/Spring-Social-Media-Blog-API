package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.repository.AccountRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    //create a message with restrictions on it
    public ResponseEntity<Message> createMessage(Message messages) {

        Optional<Account> optionalAccount = accountRepository.findById(messages.getPostedBy());
        if (messages.getMessageText().length() == 0 || messages.getMessageText().length() >= 255 || optionalAccount.isPresent() == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(messageRepository.save(messages));
        }
    }   

    //gets all messages in a list form
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(messageRepository.findAll());     
    }

    //gets all messages by id
    public ResponseEntity<Message> getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalMessage.get());
        }
        return null;
    }

    //delete messages by id
    public ResponseEntity<Integer> deleteMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (!optionalMessage.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            messageRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
    }

    //update messages by id 
    public ResponseEntity<Integer> updateMessageById(int message_id, String message_text) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent() == false || message_text.length() == 0 || message_text.length() > 255) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Message message = optionalMessage.get();
            message.setMessageText(message_text);
            messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
    }

    //gets messages by account id
    public List<Message> getMessageByAccountID(int accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }
}