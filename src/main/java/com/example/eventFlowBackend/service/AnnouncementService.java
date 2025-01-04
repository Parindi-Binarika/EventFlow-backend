package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.*;
import com.example.eventFlowBackend.payload.AnnouncementDTO;
import com.example.eventFlowBackend.payload.AssignedBatchResponse;
import com.example.eventFlowBackend.payload.AssignedStudentResponse;
import com.example.eventFlowBackend.repository.*;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO) {
        try {
            Announcement announcement = new Announcement();
            announcement.setSubject(announcementDTO.getSubject());
            announcement.setMessage(announcementDTO.getMessage());
            announcement.setCreatedBy(userRepository.findById(announcementDTO.getCreatedBy()).get());
            Announcement newAnnouncement = announcementRepository.save(announcement);
            if (announcementDTO.getBatches() != null) {
                announcementDTO.getBatches().forEach(batchId -> {
                    AnnouncementBatch announcementBatch = new AnnouncementBatch();
                    announcementBatch.setAnnouncement(newAnnouncement);
                    if (batchRepository.findById(Long.valueOf(batchId)).isPresent()) {
                        Batch batch = batchRepository.findById(Long.valueOf(batchId)).get();
                        announcementBatch.setBatch(batch);
                        announcementBatchRepository.save(announcementBatch);
                    }
                });
            }

            if(announcementDTO.getStudents() != null) {
                announcementDTO.getStudents().forEach(studentId -> {
                    AnnouncementStudent announcementStudent = new AnnouncementStudent();
                    announcementStudent.setAnnouncement(newAnnouncement);
                    if (userRepository.findById(Long.valueOf(studentId)).isPresent()) {
                        User user = userRepository.findById(Long.valueOf(studentId)).get();
                        announcementStudent.setUser(user);
                        announcementStudentRepository.save(announcementStudent);
                    }
                });
            }

            return fillStudentAndBatch(newAnnouncement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create announcement");
        }
    }

    private AnnouncementDTO fillStudentAndBatch(Announcement announcement) {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setAID(announcement.getAID());
        announcementDTO.setSubject(announcement.getSubject());
        announcementDTO.setMessage(announcement.getMessage());
        announcementDTO.setCreatedBy(Long.valueOf(announcement.getCreatedBy().getUID()));
        ArrayList<Integer> batches = new ArrayList<>();
        ArrayList<Integer> students = new ArrayList<>();
        announcementBatchRepository.findByAnnouncement_aID(announcement.getAID()).forEach(announcementBatch -> {
            batches.add(announcementBatch.getBatch().getBID());
        });
        announcementStudentRepository.findByAnnouncement_aID(announcement.getAID()).forEach(announcementStudent -> {
            students.add(announcementStudent.getUser().getUID());
        });
        announcementDTO.setBatches(batches);
        announcementDTO.setStudents(students);
        return announcementDTO;
    }

    public AnnouncementDTO getAnnouncement(Integer aID) {
        try {
            Announcement announcement = announcementRepository.findById(aID).orElseThrow(() -> new RuntimeException("Announcement not found"));
            return fillStudentAndBatch(announcement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get announcement");
        }
    }

    public List<AnnouncementDTO> getAllAnnouncementsByUID(Integer userId) {
        List<AnnouncementDTO> announcementDTOS = new ArrayList<>();
        try {
            announcementRepository.findByCreatedBy_uID(userId).forEach(announcement -> {
                announcementDTOS.add(fillStudentAndBatch(announcement));
            });
            return announcementDTOS;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get send announcements");
        }
    }

    public List<AssignedBatchResponse> getAssignBatchesByaID(Integer aID) {
       try {
            List<AssignedBatchResponse> assignedBatchResponses = new ArrayList<>();
            announcementBatchRepository.findByAnnouncement_aID(aID).forEach(announcementBatch -> {
                AssignedBatchResponse assignedBatchResponse = new AssignedBatchResponse();
                assignedBatchResponse.setId(announcementBatch.getId());
                assignedBatchResponse.setBatchName(announcementBatch.getBatch().getBatchName());
                assignedBatchResponse.setCommonEmail(announcementBatch.getBatch().getCommonEmail());
                assignedBatchResponse.setBID(announcementBatch.getBatch().getBID());
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
                assignedStudentResponse.setUID(announcementStudent.getUser().getUID());
                assignedStudentResponses.add(assignedStudentResponse);
            });
            return assignedStudentResponses;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get assigned students");
        }
    }

    public List<AnnouncementDTO> getAssignAnnouncementByUID(Integer uid) {
        try {
            List<AnnouncementDTO> announcementDTOS = new ArrayList<>();
            announcementStudentRepository.findByUser_uID(uid).forEach(announcementStudent -> {
                AnnouncementDTO announcementDTO = new AnnouncementDTO();
                announcementDTO.setAID(announcementStudent.getAnnouncement().getAID());
                announcementDTO.setSubject(announcementStudent.getAnnouncement().getSubject());
                announcementDTO.setMessage(announcementStudent.getAnnouncement().getMessage());
                announcementDTO.setCreatedBy(Long.valueOf(announcementStudent.getAnnouncement().getCreatedBy().getUID()));
                announcementDTOS.add(announcementDTO);
            });
            return announcementDTOS;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get assigned announcements");
        }
    }

    public List<AnnouncementDTO> getAssignAnnouncementBybID(Integer bid) {
        try {
            List<AnnouncementDTO> announcementDTOS = new ArrayList<>();
            announcementBatchRepository.findByBatch_bID(bid).forEach(announcementBatch -> {
                AnnouncementDTO announcementDTO = new AnnouncementDTO();
                announcementDTO.setAID(announcementBatch.getAnnouncement().getAID());
                announcementDTO.setSubject(announcementBatch.getAnnouncement().getSubject());
                announcementDTO.setMessage(announcementBatch.getAnnouncement().getMessage());
                announcementDTO.setCreatedBy(Long.valueOf(announcementBatch.getAnnouncement().getCreatedBy().getUID()));
                announcementDTOS.add(announcementDTO);
            });
            return announcementDTOS;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get assigned announcements");
        }
    }

}
