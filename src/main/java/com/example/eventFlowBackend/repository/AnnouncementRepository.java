package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Announcement;
import com.example.eventFlowBackend.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findByCreatedBy_uID(Integer createdBy);

}
