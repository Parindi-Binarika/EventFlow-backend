package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEventRepository extends JpaRepository<StudentEvent, Integer> {
}
