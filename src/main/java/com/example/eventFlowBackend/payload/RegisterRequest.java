package com.example.eventFlowBackend.payload;

import com.example.eventFlowBackend.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String mobile;
    private String nic;
    private String password;
    private Role role;
    private Integer createdBy;
}
