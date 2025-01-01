package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_event_feedback")
public class StudentEventFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sefID;
    @ManyToOne
    @JoinColumn(name = "seID",referencedColumnName = "id")
    private StudentEvent studentEvent;
    @ManyToOne
    @JoinColumn(name = "fID",referencedColumnName = "fID")
    private Feedback feedback;
}
