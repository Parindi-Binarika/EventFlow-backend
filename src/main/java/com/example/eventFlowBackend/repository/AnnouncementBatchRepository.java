package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.AnnouncementBatch;
import com.example.eventFlowBackend.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementBatchRepository extends JpaRepository<AnnouncementBatch, Integer> {
    List<AnnouncementBatch> findByAnnouncement_aID(Integer announcementId);
}
