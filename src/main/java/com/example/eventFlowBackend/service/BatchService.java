package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Batch;
import com.example.eventFlowBackend.repository.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService {
    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public void create(Batch batch) {
        batchRepository.save(batch);
    }

    public Batch update(Integer id, Batch updatedBatch) {
        return batchRepository.findById(id).map(batch -> {
            batch.setBatchName(updatedBatch.getBatchName());
            batch.setCommonEmail(updatedBatch.getCommonEmail());
            return batchRepository.save(batch);
        }).orElseThrow(() -> new RuntimeException("Batch not found"));
    }

    public void delete(Integer id) {
        batchRepository.findById(id).map(batch -> {
            batch.setIsActive(false);
            return batchRepository.save(batch);
        }).orElseThrow(() -> new RuntimeException("Batch not found"));
    }

    public List<Batch> findAll() {
        return batchRepository.findAll();
    }
}
