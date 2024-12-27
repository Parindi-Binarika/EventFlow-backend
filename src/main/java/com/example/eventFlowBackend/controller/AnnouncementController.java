package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.entity.Announcement;
import com.example.eventFlowBackend.payload.AnnouncementDTO;
import com.example.eventFlowBackend.service.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AnnouncementDTO announcementDTO) {
        try {
            AnnouncementDTO resannouncementDTO = new AnnouncementDTO();
            Announcement announcement = announcementService.createAnnouncement(announcementDTO);
            resannouncementDTO.setAID(announcement.getAID());
            resannouncementDTO.setSubject(announcement.getSubject());
            resannouncementDTO.setMessage(announcement.getMessage());
            return ResponseEntity.ok(resannouncementDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to create announcement");
        }
    }

    @PostMapping("/assign/batch/{aID}/{bID}")
    public ResponseEntity<?> assignBatch(@PathVariable Integer aID, @PathVariable Integer bID) {
        try {
            announcementService.assignBatch(aID, bID);
            return ResponseEntity.status(200).body("Batch assigned successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to assign batch");
        }
    }

    @PostMapping("/assign/student/{aID}/{uID}")
    public ResponseEntity<?> assignStudent(@PathVariable Integer aID, @PathVariable Integer uID) {
        try {
            announcementService.assignStudent(aID, uID);
            return ResponseEntity.status(200).body("Student assigned successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to assign student");
        }
    }

    @PutMapping("/send/{aid}")
    public ResponseEntity<?> sendAnnouncement(@PathVariable Integer aid) {
        try {
            announcementService.sendAnnouncement(aid);
            return ResponseEntity.status(200).body("Announcement sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Announcement not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AnnouncementDTO updatedAnnouncement) {
        try {
            announcementService.updateAnnouncement(id, updatedAnnouncement);
            return ResponseEntity.status(200).body("Announcement updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Announcement not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.status(200).body("Announcement deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Announcement cannot be deleted");
        }
    }

    @DeleteMapping("/unassign/batch/{id}")
    public ResponseEntity<?> unassignBatch(@PathVariable Integer id) {
        try {
            announcementService.unassignBatch(id);
            return ResponseEntity.status(200).body("Batch unassigned successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Batch not found");
        }
    }

    @DeleteMapping("/unassign/student/{id}")
    public ResponseEntity<?> unassignStudent(@PathVariable Integer id) {
        try {
            announcementService.unassignStudent(id);
            return ResponseEntity.status(200).body("Student unassigned successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Student not found");
        }
    }

    @GetMapping("/{aid}")
    public ResponseEntity<?> getAnnouncement(@PathVariable Integer aid) {
        return ResponseEntity.ok(announcementService.getAnnouncement(aid));
    }


    @GetMapping("/send/{uid}")
    public ResponseEntity<?> getAnnouncements(@PathVariable Integer uid) {
        return ResponseEntity.ok(announcementService.getAllSendAnnouncements(uid));
    }

    @GetMapping("/draft/{uid}")
    public ResponseEntity<?> getDraftAnnouncements(@PathVariable Integer uid) {
        return ResponseEntity.ok(announcementService.getAllDraftAnnouncements(uid));
    }

    @GetMapping("/assigned/batch/{aid}")
    public ResponseEntity<?> getAssignedBatches(@PathVariable Integer aid) {
        return ResponseEntity.ok(announcementService.getAssignBatchesByaID(aid));
    }

    @GetMapping("/assigned/student/{aid}")
    public ResponseEntity<?> getAssignedStudents(@PathVariable Integer aid) {
        return ResponseEntity.ok(announcementService.getAssignStudentsByaID(aid));
    }

}
