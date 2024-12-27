package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.AnnouncementStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementStudentRepository extends JpaRepository<AnnouncementStudent, Integer> {
    List<AnnouncementStudent> findByAnnouncement_aID(Integer announcementId);
}
