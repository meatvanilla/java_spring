package com.example.mod4.controller;

import com.example.mod4.dto.AccountUserDTO;
import com.example.mod4.model.AccountUser;
import com.example.mod4.service.AccountUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<AccountUserDTO> createUser(@RequestBody @Valid AccountUser user, Authentication authentication) {
        String currentUserName = authentication.getName();
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")) &&
                !user.getUsername().equals(currentUserName)) {
            throw new AccessDeniedException("Нет доступа");
        }
        AccountUserDTO createdUser = accountUserService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<AccountUserDTO> getUserById(@PathVariable Long id, Authentication authentication) {
        String currentUserName = authentication.getName();
        AccountUserDTO user = accountUserService.getUserById(id);

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")) &&
                !user.getUsername().equals(currentUserName)) {
            throw new AccessDeniedException("Нет доступа");
        }
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AccountUserDTO>> getAllUsers() {
        List<AccountUserDTO> users = accountUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, Authentication authentication) {
        String currentUserName = authentication.getName();
        AccountUserDTO user = accountUserService.getUserById(id);
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")) &&
                !user.getUsername().equals(currentUserName)) {
            throw new AccessDeniedException("Нет доступа");
        }
        accountUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
