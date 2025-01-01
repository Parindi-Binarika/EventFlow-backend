package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.AnnouncementStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnouncementStudentRepository extends JpaRepository<AnnouncementStudent, Integer> {
    List<AnnouncementStudent> findByAnnouncement_aID(Integer announcementId);
    void deleteByAnnouncement_aID(Integer announcementId);
    Optional<AnnouncementStudent> findByUser_uIDAndAnnouncement_aID(Integer userId, Integer announcementId);
}
