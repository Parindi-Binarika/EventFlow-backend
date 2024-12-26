package com.example.eventFlowBackend.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "announcement_student")
public class AnnouncementStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "aID", referencedColumnName = "aID")
    private Announcement announcement;
    @ManyToOne
    @JoinColumn(name = "uID", referencedColumnName = "uID")
    private User user;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
