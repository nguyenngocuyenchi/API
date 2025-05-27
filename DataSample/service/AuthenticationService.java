package com.example.DataSample.service;

import org.springframework.stereotype.Service;

import com.example.DataSample.controller.*;
import com.example.DataSample.model.*;
import com.example.DataSample.repository.*;
import com.example.DataSample.security.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER) 
            .build();

            userRepo.save(user);
            UserDetailsImpl userDetails = UserDetailsImpl.build(user);
            String token = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = userRepo.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("user not found"));
            UserDetailsImpl userDetails = UserDetailsImpl.build(user);
            String token = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
