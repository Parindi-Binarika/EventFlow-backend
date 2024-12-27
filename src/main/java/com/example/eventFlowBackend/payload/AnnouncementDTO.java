package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class AnnouncementDTO {
    private Integer aID;
    private String subject;
    private String message;
    private Long createdBy;
    private boolean isSent;
}
