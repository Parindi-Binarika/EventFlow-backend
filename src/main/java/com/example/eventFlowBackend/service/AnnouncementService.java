package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.*;
import com.example.eventFlowBackend.payload.AnnouncementDTO;
import com.example.eventFlowBackend.payload.AssignedBatchResponse;
import com.example.eventFlowBackend.payload.AssignedStudentResponse;
import com.example.eventFlowBackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementService {

    AnnouncementRepository announcementRepository;
    BatchRepository batchRepository;
    UserRepository userRepository;

    AnnouncementBatchRepository announcementBatchRepository;
    AnnouncementStudentRepository announcementStudentRepository;


    public AnnouncementService(AnnouncementRepository announcementRepository, UserRepository userRepository, AnnouncementBatchRepository announcementBatchRepository, BatchRepository batchRepository, AnnouncementStudentRepository announcementStudentRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.announcementBatchRepository = announcementBatchRepository;
        this.batchRepository = batchRepository;
        this.announcementStudentRepository = announcementStudentRepository;
    }

    public void createAnnouncement(AnnouncementDTO announcementDTO) {
        try {
            Announcement announcement = new Announcement();
            announcement.setSubject(announcementDTO.getSubject());
            announcement.setMassage(announcementDTO.getMassage());
            announcement.setCreatedBy(userRepository.findById(announcementDTO.getCreatedBy()).get());
            announcement.setIsActive(announcementDTO.getIsActive());
            announcement.setIsActive(announcementDTO.isSent());
            announcementRepository.save(announcement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create announcement");
        }
    }

    public void assignBatch(Integer aID, Integer bID) {
        try {
            Announcement announcement = announcementRepository.findById(aID).orElseThrow(() -> new RuntimeException("Announcement not found"));
            Batch batch = batchRepository.findById(Long.valueOf(bID)).orElseThrow(() -> new RuntimeException("Batch not found"));
            AnnouncementBatch announcementBatch = new AnnouncementBatch();
            announcementBatch.setAnnouncement(announcement);
            announcementBatch.setBatch(batch);
            announcementBatchRepository.save(announcementBatch);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign batch");
        }
    }

    public void assignStudent(Integer aID, Integer uID) {
        try {
            Announcement announcement = announcementRepository.findById(aID).orElseThrow(() -> new RuntimeException("Announcement not found"));
            User user = userRepository.findById(Long.valueOf(uID)).orElseThrow( () -> new RuntimeException("User not found"));
            AnnouncementStudent announcementStudent = new AnnouncementStudent();
            announcementStudent.setAnnouncement(announcement);
            announcementStudent.setUser(user);
            announcementStudentRepository.save(announcementStudent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign batch");
        }
    }

    public void updateAnnouncement(Integer id,AnnouncementDTO announcementDTO) {
        try {
            Announcement announcement = announcementRepository.findById(id).get();
            announcement.setSubject(announcementDTO.getSubject());
            announcement.setMassage(announcementDTO.getMassage());
            announcementRepository.save(announcement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update announcement");
        }
    }

    public void deleteAnnouncement(Integer aID) {
        try {
            Announcement announcement = announcementRepository.findById(aID).orElseThrow(() -> new RuntimeException("Announcement not found"));
            announcement.setIsActive(false);
            announcementRepository.save(announcement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete announcement");
        }
    }

    public void unassignBatch(Integer id) {
        try {
            AnnouncementBatch announcementBatch = announcementBatchRepository.findById(id).orElseThrow(() -> new RuntimeException("AnnouncementBatch not found"));
            announcementBatchRepository.delete(announcementBatch);
        } catch (Exception e) {
            throw new RuntimeException("Failed to unassign batch");
        }
    }

    public void unassignStudent(Integer id) {
        try {
            AnnouncementStudent announcementStudent = announcementStudentRepository.findById(id).orElseThrow(() -> new RuntimeException("AnnouncementStudent not found"));
            announcementStudentRepository.delete(announcementStudent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to unassign student");
        }
    }

    public void sendAnnouncement(Integer aID) {
        try {
            Announcement announcement = announcementRepository.findById(aID).orElseThrow( () -> new RuntimeException("Announcement not found"));
            announcement.setIsSent(true);
            announcementRepository.save(announcement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send announcement");
        }
    }

    public Announcement getAnnouncement(Integer aID) {
        return announcementRepository.findById(aID).orElseThrow(() -> new RuntimeException("Announcement not found"));
    }

    public List<Announcement> getAllSendAnnouncements(Integer userId) {
        return announcementRepository.findByCreatedBy_uIDAndIsActiveTrueAndIsSentTrue(userId);
    }

    public List<Announcement> getAllDraftAnnouncements(Integer userId) {
        return announcementRepository.findByCreatedBy_uIDAndIsActiveTrueAndIsSentFalse(userId);
    }

    public List<AssignedBatchResponse> getAssignBatchesByaID(Integer aID) {
       try {
            List<AssignedBatchResponse> assignedBatchResponses = new ArrayList<>();
            announcementBatchRepository.findByAnnouncement_aID(aID).forEach(announcementBatch -> {
                AssignedBatchResponse assignedBatchResponse = new AssignedBatchResponse();
                assignedBatchResponse.setId(announcementBatch.getId());
                assignedBatchResponse.setBatchName(announcementBatch.getBatch().getBatchName());
                assignedBatchResponse.setCommonEmail(announcementBatch.getBatch().getCommonEmail());
                assignedBatchResponses.add(assignedBatchResponse);
            });
            return assignedBatchResponses;
       } catch (Exception e) {
           throw new RuntimeException("Failed to get assigned batches");
       }
    }

    public List<AssignedStudentResponse> getAssignStudentsByaID(Integer aID) {
        try {
            List<AssignedStudentResponse> assignedStudentResponses = new ArrayList<>();
            announcementStudentRepository.findByAnnouncement_aID(aID).forEach(announcementStudent -> {
                AssignedStudentResponse assignedStudentResponse = new AssignedStudentResponse();
                assignedStudentResponse.setId(announcementStudent.getId());
                assignedStudentResponse.setName(announcementStudent.getUser().getName());
                assignedStudentResponse.setEmail(announcementStudent.getUser().getEmail());
                assignedStudentResponses.add(assignedStudentResponse);
            });
            return assignedStudentResponses;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get assigned students");
        }
    }

}
