package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.entity.Batch;
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
    public ResponseEntity<Batch> create(@RequestBody Batch batch) {
        try {
            Batch resBatch = batchService.create(batch);
            return ResponseEntity.status(200).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/assign/{bID}/{uID}")
    public ResponseEntity<Batch> assignUser(@PathVariable Long bID, @PathVariable Long uID) {
        try {
            batchService.assignUser(bID, uID);
            return ResponseEntity.status(200).build();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batch> update(@PathVariable Long id, @RequestBody Batch updatedBatch) {
        try {
            batchService.update(id, updatedBatch);
            return ResponseEntity.status(200).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            batchService.delete(id);
            return ResponseEntity.status(200).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/assign/{id}")
    public ResponseEntity<Void> unassignUser(@PathVariable Long id) {
        try {
            batchService.unassignUser(id);
            return ResponseEntity.status(200).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllBatches() {
        return ResponseEntity.ok(batchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batch> getBatchById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(batchService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allStudents/{bID}")
    public ResponseEntity<?> getAllStudents(@PathVariable Long bID) {
        try {
            return ResponseEntity.ok(batchService.findUsersByBatch(bID));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("allBatches/{uID}")
    public ResponseEntity<?> getAllBatches(@PathVariable Long uID) {
        try {
            return ResponseEntity.ok(batchService.findBatchesByUser(uID));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
