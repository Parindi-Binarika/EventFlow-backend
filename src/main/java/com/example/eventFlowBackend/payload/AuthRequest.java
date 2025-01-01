package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
// AuthRequest
public class AuthRequest {
    private String email;
    private String password;

    // Getters and Setters
}
