package com.example.mod4.controller;

import com.example.mod4.dto.AccountUserDTO;
import com.example.mod4.service.AccountUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AccountUserController {
    private final AccountUserService accountUserService;

    @Autowired
    public AccountUserController(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }

    @PostMapping
    public ResponseEntity<AccountUserDTO> createUser(@RequestBody @Valid AccountUserDTO accountUserDTO) {
        AccountUserDTO createdUser = accountUserService.createUser(accountUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountUserDTO> getUserById(@PathVariable Long id) {
        AccountUserDTO user = accountUserService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<AccountUserDTO>> getAllUsers() {
        List<AccountUserDTO> users = accountUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        accountUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
