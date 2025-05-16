package com.example.DataSample.controller;

import java.net.Authenticator;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

import com.example.DataSample.model.User;
import com.example.DataSample.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserServiceImpl userService, BCryptPasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUser(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User register) {
        register.setPassword(encoder.encode(register.getPassword()));
        register.setRole("USER");
        userService.saveUser(register);
        return new ResponseEntity<>("user saved", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password())
        );
        return ResponseEntity.ok("Login successful");
}

   
  
    public record LoginRequest(String username, String password) {

    }

    @GetMapping("/login")
    String login() {
        return "login";
    }
}

