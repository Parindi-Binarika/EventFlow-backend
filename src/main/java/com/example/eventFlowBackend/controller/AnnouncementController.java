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

    @GetMapping("/ByAnnouncement/{aid}")
    public ResponseEntity<?> getAnnouncement(@PathVariable Integer aid) {
        try {
            announcementService.getAnnouncement(aid);
            return ResponseEntity.ok(announcementService.getAnnouncement(aid));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Announcement not found: " + e.getMessage());
        }
    }


    @GetMapping("/ByUser/{uid}")
    public ResponseEntity<?> getAnnouncementsByuID(@PathVariable Integer uid) {
        try {
            return ResponseEntity.ok(announcementService.getAllAnnouncementsByUID(uid));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Announcements not found");
        }
    }

    @GetMapping("/assigned/batch/{aid}")
    public ResponseEntity<?> getAssignedBatches(@PathVariable Integer aid) {
        try {
            return ResponseEntity.ok(announcementService.getAssignBatchesByaID(aid));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Batches not found");
        }
    }

    @GetMapping("/assigned/student/{aid}")
    public ResponseEntity<?> getAssignedStudents(@PathVariable Integer aid) {
        try {
            return ResponseEntity.ok(announcementService.getAssignStudentsByaID(aid));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Students not found");
        }
    }

    @GetMapping("/assigned/announcement/student/{uid}")
    public ResponseEntity<?> getAssignedAnnouncements(@PathVariable Integer uid) {
        try {
            return ResponseEntity.ok(announcementService.getAssignAnnouncementByUID(uid));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Announcements not found");
        }
    }

    @GetMapping("/assigned/announcement/batch/{bID}")
    public ResponseEntity<?> assignAnnouncement(@PathVariable Integer bID) {
        try {
            return ResponseEntity.ok(announcementService.getAssignAnnouncementBybID(bID));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Announcements not found");
        }
    }

}
