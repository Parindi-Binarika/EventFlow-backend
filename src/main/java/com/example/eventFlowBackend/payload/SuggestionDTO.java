package com.example.eventFlowBackend.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuggestionDTO {
    private Integer sID;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private boolean isExpired;
    private Integer createdBy;
    private Integer votes;
}
