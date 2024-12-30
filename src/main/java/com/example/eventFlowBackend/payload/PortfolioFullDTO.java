package com.example.eventFlowBackend.payload;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioFullDTO {
    private Integer pID;
    private String displayName;
    private String description;
    private String technologies;
    private String githubUsername;
    private String linkedinUsername;
    private Boolean isPublic;
    private Integer userId;
}