package com.example.eventFlowBackend.payload;

import lombok.Data;

// AuthResponse
@Data
public class AuthResponse {
    private String token;
    private Integer uID;
    private String type;

    public AuthResponse(String token,Integer uID,String type) {
        this.token = token;
        this.uID = uID;
        this.type = type;
    }
}
