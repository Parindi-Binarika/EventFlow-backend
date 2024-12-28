package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.payload.FeedbackDTO;
import com.example.eventFlowBackend.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/create/individual/{seID}")
    public void createIndividualFeedback(@PathVariable Integer seID, FeedbackDTO feedbackDTO) {
        try {
            feedbackService.createIndividualFeedback(feedbackDTO, seID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create feedback");
        }
    }

    @PostMapping("/create/group/{eID}")
    public void createGroupFeedback(@PathVariable Integer eID, FeedbackDTO feedbackDTO) {
        try {
            feedbackService.createGroupFeedback(feedbackDTO, eID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create feedback");
        }
    }

    @PostMapping("/update/{fID}")
    public void update(@PathVariable Integer fID, FeedbackDTO feedbackDTO) {
        try {
            feedbackService.update(fID, feedbackDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update feedback");
        }
    }

    @DeleteMapping("/{fID}")
    public void delete(@PathVariable Integer fID) {
        try {
            feedbackService.delete(fID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete feedback");
        }
    }

    @GetMapping("/{fID}")
    public FeedbackDTO getFeedback(@PathVariable Integer fID) {
        try {
            return feedbackService.getFeedback(fID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedback");
        }
    }

    @GetMapping("/{eID}/{uID}")
    public List<FeedbackDTO> getFeedback(@PathVariable Integer eID, @PathVariable Integer uID) {
        try {
            return feedbackService.getAllFeedbackByeIDAnd_uID(eID, uID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedback");
        }
    }
}
