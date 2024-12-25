package com.example.eventFlowBackend.payload;

import com.example.eventFlowBackend.entity.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Integer uID;
    private String name;
    private String email;
    private String mobile;
    private String nic;
    private String password;
    private Boolean isActive;
    private Role role;
    private Integer createdBy;
}
