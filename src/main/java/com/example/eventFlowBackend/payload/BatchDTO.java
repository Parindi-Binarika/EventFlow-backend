package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class BatchDTO {
    private Integer bID;
    private String batchName;
    private String commonEmail;
}
