package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.StudentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentBatchRepository extends JpaRepository<StudentBatch, Long> {
    List<StudentBatch> findByUser_uID(Long userId);
    List<StudentBatch> findByBatch_bID(Long batchId);
}
