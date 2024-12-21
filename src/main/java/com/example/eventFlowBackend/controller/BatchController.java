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
        batchService.create(batch);
        return ResponseEntity.ok(batch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batch> update(@PathVariable Integer id, @RequestBody Batch updatedBatch) {
        try {
            return ResponseEntity.ok(batchService.update(id, updatedBatch));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        batchService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<?> getAllBatches() {
        return ResponseEntity.ok(batchService.findAll());
    }

}
