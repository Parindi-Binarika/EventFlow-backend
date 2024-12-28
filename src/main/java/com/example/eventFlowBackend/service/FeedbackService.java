package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Feedback;
import com.example.eventFlowBackend.entity.FeedbackType;
import com.example.eventFlowBackend.payload.FeedbackDTO;
import com.example.eventFlowBackend.repository.FeedbackRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    FeedbackRepository feedbackRepository;
    UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public Integer create(FeedbackType type, FeedbackDTO feedbackDTO) {
        try {
            Feedback feedback = new Feedback();
            feedback.setFeedback(feedbackDTO.getFeedback());
            feedback.setFeedbackType(type);
            feedback.setCreatedBy(userRepository.findById(Long.valueOf(feedbackDTO.getCreatedByUserId())).get());
            Feedback res = feedbackRepository.save(feedback);
            return res.getFID();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create feedback");
        }
    }

    public void update(Integer fID, FeedbackDTO feedbackDTO) {
        try {
            Feedback feedback = feedbackRepository.findById(fID).get();
            feedback.setFeedback(feedbackDTO.getFeedback());
            feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update feedback");
        }
    }

    public void delete(Integer fID) {
        try {
            feedbackRepository.deleteById(fID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete feedback");
        }
    }

    public FeedbackDTO getFeedback(Integer fID) {
        try {
            Feedback feedback = feedbackRepository.findById(fID).get();
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setFID(feedback.getFID());
            feedbackDTO.setFeedback(feedback.getFeedback());
            feedbackDTO.setFeedbackType(feedback.getFeedbackType());
            feedbackDTO.setCreatedByUserId(feedback.getCreatedBy().getUID());
            feedbackDTO.setCreatedDateTime(feedback.getCreatedDateTime());
            return feedbackDTO;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedback");
        }
    }
}
