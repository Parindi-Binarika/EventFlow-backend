package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.entity.Batch;
import com.example.eventFlowBackend.payload.BatchDTO;
import com.example.eventFlowBackend.service.BatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batches")
public class BatchController {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BatchDTO batch) {
        try {
            batchService.create(batch);
            return ResponseEntity.status(200).body("Batch created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Batch creation failed");
        }
    }

    @PostMapping("/assign/{bID}/{uID}")
    public ResponseEntity<?> assignUser(@PathVariable Long bID, @PathVariable Long uID) {
        try {
            batchService.assignUser(bID, uID);
            return ResponseEntity.status(200).body("User assigned to batch successfully");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body("User assignment failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BatchDTO updatedBatch) {
        try {
            batchService.update(id, updatedBatch);
            return ResponseEntity.status(200).body("Batch updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Batch not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            batchService.delete(id);
            return ResponseEntity.status(200).body("Batch deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Batch not found");
        }
    }

    @DeleteMapping("/assign/{id}")
    public ResponseEntity<?> unassignUser(@PathVariable Long id) {
        try {
            batchService.unassignUser(id);
            return ResponseEntity.status(200).body("User unassigned from batch successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllBatches() {
        return ResponseEntity.ok(batchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBatchById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(batchService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Batch not found");
        }
    }

    @GetMapping("/allStudents/{bID}")
    public ResponseEntity<?> getAllStudents(@PathVariable Long bID) {
        try {
            return ResponseEntity.ok(batchService.findUsersByBatch(bID));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Batch not found");
        }
    }

    @GetMapping("allBatches/{uID}")
    public ResponseEntity<?> getAllBatches(@PathVariable Long uID) {
        try {
            return ResponseEntity.ok(batchService.findBatchesByUser(uID));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }



}
