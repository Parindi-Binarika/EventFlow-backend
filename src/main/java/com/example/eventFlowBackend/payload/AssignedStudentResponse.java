package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class AssignedStudentResponse {
    private Integer id;
    private Integer uID;
    private String name;
    private String email;
}
