package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class AssignedBatchResponse {
    private Integer id;
    private String batchName;
    private String commonEmail;
}
