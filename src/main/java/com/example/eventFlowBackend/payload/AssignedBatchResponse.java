package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class AssignedBatchResponse {
    private Integer id;
    private Integer bID;
    private String batchName;
    private String commonEmail;
}
