package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String nic;
}
