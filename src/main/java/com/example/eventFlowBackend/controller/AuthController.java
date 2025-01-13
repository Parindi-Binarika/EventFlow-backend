package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.config.JwtUtil;
import com.example.eventFlowBackend.entity.User;
import com.example.eventFlowBackend.payload.AuthRequest;
import com.example.eventFlowBackend.payload.AuthResponse;
import com.example.eventFlowBackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtUtil.generateToken(request.getEmail(), user.getRole());
            return ResponseEntity.ok(new AuthResponse(token, user.getUID(), user.getRole().name()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid email/password");
        }
    }
}