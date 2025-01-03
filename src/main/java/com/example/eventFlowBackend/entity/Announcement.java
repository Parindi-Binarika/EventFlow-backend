package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
    private String message;

    @ManyToOne
    @JoinColumn(name = "createdBy",referencedColumnName = "uID", nullable = false)
    private User createdBy;


}
