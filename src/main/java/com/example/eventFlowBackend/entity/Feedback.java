package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fID;
    @Column(nullable = false)
    private String feedback;
    @Column(nullable = false)
    private FeedbackType feedbackType;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @PrePersist
    public void prePersist() {
        createdDateTime = LocalDateTime.now();
    }

}
