package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eID;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 255)
    private String description;
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    @Column(nullable = false)
    private EventType eventType;
    @Column(nullable = false)
    private Boolean isActive = true;
}
