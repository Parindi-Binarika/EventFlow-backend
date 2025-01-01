package com.example.eventFlowBackend.payload;

// AuthResponse
public class AuthResponse {
    private String token;
    private Integer uID;

    public AuthResponse(String token,Integer uID) {
        this.token = token;
        this.uID = uID;
    }

    // Getter
    public String getToken() {
        return token;
    }

    public Integer getUID() {
        return uID;
    }
}
