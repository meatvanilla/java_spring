package com.example.mod4.service;

import com.example.mod4.dto.AccountUserDTO;
import com.example.mod4.exception.UserNotFoundException;
import com.example.mod4.mapper.AccountUserMapper;
import com.example.mod4.model.AccountUser;
import com.example.mod4.repository.AccountUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountUserService {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final AccountUserRepository accountUserRepository;
    private final AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserService(AccountUserRepository accountUserRepository, AccountUserMapper accountUserMapper) {
        this.accountUserRepository = accountUserRepository;
        this.accountUserMapper = accountUserMapper;
    }

    public AccountUserDTO createUser(AccountUser user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            AccountUser savedUser = accountUserRepository.save(user);
            return accountUserMapper.toDTO(savedUser);
        } catch (Exception e) {
            // Логирование ошибки
            throw new RuntimeException("Error while creating user: " + e.getMessage());
        }
    }

    public AccountUserDTO getUserById(Long id) {
        try {
            AccountUser user = accountUserRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
            return accountUserMapper.toDTO(user);
        } catch (UserNotFoundException e) {
            // Логирование и повторное выбрасывание пользовательского исключения
            throw e;
        } catch (Exception e) {
            // Логирование других исключений
            throw new RuntimeException("Error while fetching user by id: " + e.getMessage());
        }
    }

    public List<AccountUserDTO> getAllUsers() {
        try {
            List<AccountUser> users = accountUserRepository.findAll();
            return users.stream().map(accountUserMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            // Логирование ошибки
            throw new RuntimeException("Error while fetching all users: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            if (!accountUserRepository.existsById(id)) {
                throw new UserNotFoundException("User with id " + id + " not found");
            }
            accountUserRepository.deleteById(id);
        } catch (UserNotFoundException e) {
            // Логирование и повторное выбрасывание пользовательского исключения
            throw e;
        } catch (Exception e) {
            // Логирование других исключений
            throw new RuntimeException("Error while deleting user by id: " + e.getMessage());
        }
    }
}