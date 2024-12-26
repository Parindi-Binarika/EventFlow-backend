package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aID;

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(nullable = false, length = 255)
    private String massage;

    @ManyToOne
    @JoinColumn(name = "createdBy",referencedColumnName = "uID", nullable = false)
    private User createdBy;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isSent = false;


}
