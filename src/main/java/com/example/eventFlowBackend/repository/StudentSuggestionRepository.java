package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.StudentSuggestion;
import com.example.eventFlowBackend.entity.Suggestion;
import com.example.eventFlowBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentSuggestionRepository extends JpaRepository<StudentSuggestion, Integer> {
    boolean existsByUserAndSuggestion(User user, Suggestion suggestion);
    StudentSuggestion findByUserAndSuggestion(User user, Suggestion suggestion);
    Integer countAllBySuggestion(Suggestion suggestion);
}
