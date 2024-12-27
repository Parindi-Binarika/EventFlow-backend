package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "student_event")
public class StudentEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "uID", referencedColumnName = "uID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "eID")
    private Event event;

    @Column
    private Integer points;

    @Column
    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }
}
