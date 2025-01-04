package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "portfolio")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pID;
    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String technologies;
    @Column
    private String githubUsername;
    @Column
    private String linkedinUsername;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPublic = false;
    @OneToOne
    @JoinColumn(name = "uID",referencedColumnName = "uID")
    private User user;
}
