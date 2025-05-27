package com.example.DataSample.service;

import java.util.*;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.DataSample.model.User;

public class UserDetailsImpl implements UserDetails, CredentialsContainer {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    } 

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user);
    } 
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getUsername() {
        return user.getEmail(); 
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    
    @Override
    public void eraseCredentials() {
        if(user != null) {
            user.setPassword(null);
        }
    }

    
}

 