package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    List<Suggestion> findAllByExpired(boolean expired);
    Integer countAllByExpired(boolean expired);
}
