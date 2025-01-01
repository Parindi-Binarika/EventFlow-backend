package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.StudentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentBatchRepository extends JpaRepository<StudentBatch, Long> {
    List<StudentBatch> findByUser_uID(Long userId);

    List<StudentBatch> findByBatch_bIDAndUser_IsActive(Long batchBID, Boolean userIsActive);
    Optional<StudentBatch> findByUser_uIDAndBatch_bID(Long userId, Long batchId);
}
