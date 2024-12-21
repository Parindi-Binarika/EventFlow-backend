package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.entity.StudentBatch;
import com.example.eventFlowBackend.service.StudentBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-batches")
public class StudentBatchController {
    private final StudentBatchService studentBatchService;

    public StudentBatchController(StudentBatchService studentBatchService) {
        this.studentBatchService = studentBatchService;
    }

    @PostMapping("/assign/{userId}/{batchId}")
    public ResponseEntity<StudentBatch> assignUserToBatch(@PathVariable Long userId,@PathVariable Long batchId) {
        StudentBatch studentBatch = studentBatchService.assignUserToBatch(userId, batchId);
        return ResponseEntity.ok(studentBatch);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StudentBatch>> getBatchesForUser(@PathVariable Long userId) {
        List<StudentBatch> batches = studentBatchService.getBatchesForUser(userId);
        return ResponseEntity.ok(batches);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<StudentBatch>> getUsersForBatch(@PathVariable Long batchId) {
        List<StudentBatch> users = studentBatchService.getUsersForBatch(batchId);
        return ResponseEntity.ok(users);
    }
}
