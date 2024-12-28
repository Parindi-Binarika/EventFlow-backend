package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
