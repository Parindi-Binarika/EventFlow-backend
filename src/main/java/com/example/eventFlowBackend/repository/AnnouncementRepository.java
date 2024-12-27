package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findByCreatedBy_uIDAndIsSentTrue(Integer userId);
    List<Announcement> findByCreatedBy_uIDAndIsSentFalse(Integer userId);

}
