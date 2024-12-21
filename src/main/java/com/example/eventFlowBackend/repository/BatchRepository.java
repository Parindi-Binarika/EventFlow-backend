package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Integer> {

}
