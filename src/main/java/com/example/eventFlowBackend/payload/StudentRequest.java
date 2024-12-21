package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class StudentRequest {
    private Long studentId;
    private Long batchId;
}
