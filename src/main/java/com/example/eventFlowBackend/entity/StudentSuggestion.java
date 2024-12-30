package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_suggestions")
public class StudentSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "uID", referencedColumnName = "uID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "suID", referencedColumnName = "suID")
    private Suggestion suggestion;
}
