package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Batch;
import com.example.eventFlowBackend.entity.StudentBatch;
import com.example.eventFlowBackend.entity.User;
import com.example.eventFlowBackend.repository.BatchRepository;
import com.example.eventFlowBackend.repository.StudentBatchRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class StudentBatchService {
    private final StudentBatchRepository studentBatchRepository;
    private final UserRepository userRepository;
    private final BatchRepository batchRepository;

    public StudentBatchService(StudentBatchRepository studentBatchRepository,
                               UserRepository userRepository,
                               BatchRepository batchRepository) {
        this.studentBatchRepository = studentBatchRepository;
        this.userRepository = userRepository;
        this.batchRepository = batchRepository;
    }

    public StudentBatch assignUserToBatch(Long userId, Long batchId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        StudentBatch studentBatch = new StudentBatch();
        studentBatch.setUser(user);
        studentBatch.setBatch(batch);
        studentBatch.setCreatedAt(LocalDateTime.now());

        return studentBatchRepository.save(studentBatch);
    }

    public List<StudentBatch> getBatchesForUser(Long userId) {
        return studentBatchRepository.findByUser_uID(userId);
    }

    public List<StudentBatch> getUsersForBatch(Long batchId) {
        return studentBatchRepository.findByBatch_bID(batchId);
    }

}
