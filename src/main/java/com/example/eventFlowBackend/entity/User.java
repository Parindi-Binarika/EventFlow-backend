package com.example.eventFlowBackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uID;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, length = 15)
    private String mobile;

    @Column(length = 20)
    private String nic;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "createdBy",referencedColumnName = "uID", nullable = true)
    private User createdBy;

    @Column(updatable = false )
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}
