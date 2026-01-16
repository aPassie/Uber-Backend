package com.example.Uber_Clone.controller;

import jakarta.validation.Valid;
import com.example.Uber_Clone.dto.*;
import com.example.Uber_Clone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody AuthRegisterRequest request
    ) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthLoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
