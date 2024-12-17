package com.example.mod7.security;

import com.example.mod7.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User accountUser;

    public CustomUserDetails(User accountUser) {
        this.accountUser = accountUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return accountUser.getRoles() != null && !accountUser.getRoles().isEmpty()
                ? accountUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList()
                : List.of();
    }

    @Override
    public String getPassword() {
        return accountUser.getPassword();
    }

    @Override
    public String getUsername() {
        return accountUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
