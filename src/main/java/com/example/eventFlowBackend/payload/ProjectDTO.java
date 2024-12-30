package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class ProjectDTO {
    private Integer proID;
    private String displayName;
    private String description;
    private String technologies;
    private String resourcesLink;
    private Integer portfolioId; // Referencing Portfolio by its ID
}
