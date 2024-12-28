package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.payload.FeedbackDTO;
import com.example.eventFlowBackend.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/create/individual/{seID}")
    public ResponseEntity<?> createIndividualFeedback(@PathVariable Integer seID,@RequestBody FeedbackDTO feedbackDTO) {
        try {
            feedbackService.createIndividualFeedback(feedbackDTO, seID);
            return ResponseEntity.ok("Feedback created successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Failed to create feedback");
        }
    }

    @PostMapping("/create/group/{eID}")
    public ResponseEntity<?> createGroupFeedback(@PathVariable Integer eID,@RequestBody FeedbackDTO feedbackDTO) {
        try {
            feedbackService.createGroupFeedback(feedbackDTO, eID);
            return ResponseEntity.ok("Feedback created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create feedback");
        }
    }

    @PostMapping("/update/{fID}")
    public ResponseEntity<?> update(@PathVariable Integer fID,@RequestBody FeedbackDTO feedbackDTO) {
        try {
            feedbackService.update(fID, feedbackDTO);
            return ResponseEntity.ok("Feedback updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update feedback");
        }
    }

    @DeleteMapping("/{fID}")
    public ResponseEntity<?> delete(@PathVariable Integer fID) {
        try {
            feedbackService.delete(fID);
            return ResponseEntity.ok("Feedback deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete feedback");
        }
    }

    @GetMapping("/{fID}")
    public ResponseEntity<?> getFeedback(@PathVariable Integer fID) {
        try {
            return ResponseEntity.ok(feedbackService.getFeedback(fID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get feedback");
        }
    }

    @GetMapping("/{eID}/{uID}")
    public ResponseEntity<?> getFeedback(@PathVariable Integer eID, @PathVariable Integer uID) {
        try {
            return ResponseEntity.ok(feedbackService.getAllFeedbackByeIDAnd_uID(eID, uID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get feedback");
        }
    }
}
