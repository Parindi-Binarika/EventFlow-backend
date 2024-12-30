package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proID;
    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
    private String description;
    @Column
    private String technologies;
    @Column(nullable = false)
    private String resourcesLink;
    @ManyToOne
    @JoinColumn(name = "pID",referencedColumnName = "pID")
    private Portfolio portfolio;
}
