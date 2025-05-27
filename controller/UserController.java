package com.example.DataSample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.DataSample.security.AuthenticationRequest;
import com.example.DataSample.security.AuthenticationResponse;
import com.example.DataSample.service.AuthenticationService;
import com.example.DataSample.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationService authService;

    @Autowired
    public UserController(UserServiceImpl userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUser(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("testingggg...");
    }
   
}

