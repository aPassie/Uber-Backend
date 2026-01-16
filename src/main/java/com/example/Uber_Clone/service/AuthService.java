package com.example.Uber_Clone.service;

import com.example.Uber_Clone.dto.AuthLoginRequest;
import com.example.Uber_Clone.dto.AuthRegisterRequest;
import com.example.Uber_Clone.dto.AuthResponse;
import com.example.Uber_Clone.exception.BadRequestException;
import com.example.Uber_Clone.model.User;
import com.example.Uber_Clone.repository.UserRepository;
import com.example.Uber_Clone.util.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // ✅ Correct constructor injection (NO warnings)
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void register(AuthRegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(AuthLoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new AuthResponse(token);
    }
}
