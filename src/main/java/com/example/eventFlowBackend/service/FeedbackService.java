package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Feedback;
import com.example.eventFlowBackend.entity.FeedbackType;
import com.example.eventFlowBackend.entity.StudentEventFeedback;
import com.example.eventFlowBackend.payload.FeedbackDTO;
import com.example.eventFlowBackend.repository.FeedbackRepository;
import com.example.eventFlowBackend.repository.StudentEventFeedbackRepository;
import com.example.eventFlowBackend.repository.StudentEventRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    FeedbackRepository feedbackRepository;
    StudentEventFeedbackRepository studentEventFeedbackRepository;
    StudentEventRepository studentEventRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, StudentEventFeedbackRepository studentEventFeedbackRepository, StudentEventRepository studentEventRepository) {
        this.feedbackRepository = feedbackRepository;
        this.studentEventFeedbackRepository = studentEventFeedbackRepository;
        this.studentEventRepository = studentEventRepository;
    }

    public void createIndividualFeedback(FeedbackDTO feedbackDTO, Integer seID) {
        try {
            Feedback feedback = new Feedback();
            feedback.setFeedback(feedbackDTO.getFeedback());
            feedback.setFeedbackType(FeedbackType.individual);
            Feedback res = feedbackRepository.save(feedback);
            StudentEventFeedback studentEventFeedback = new StudentEventFeedback();
            studentEventFeedback.setFeedback(res);
            studentEventFeedback.setStudentEvent(studentEventRepository.findById(seID).get());
            studentEventFeedbackRepository.save(studentEventFeedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create feedback: " + e.getMessage());
        }
    }

    public void createGroupFeedback(FeedbackDTO feedbackDTO, Integer eID) {
        try {
            Feedback feedback = new Feedback();
            feedback.setFeedback(feedbackDTO.getFeedback());
            feedback.setFeedbackType(FeedbackType.group);
            Feedback res = feedbackRepository.save(feedback);
            studentEventRepository.findAllByEvent_eID(eID).forEach(studentEvent -> {
                StudentEventFeedback studentEventFeedback = new StudentEventFeedback();
                studentEventFeedback.setFeedback(res);
                studentEventFeedback.setStudentEvent(studentEvent);
                studentEventFeedbackRepository.save(studentEventFeedback);
            });
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
            studentEventFeedbackRepository.deleteAllByFeedback_fID(fID);
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
            feedbackDTO.setCreatedDateTime(feedback.getCreatedDateTime());
            return feedbackDTO;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedback");
        }
    }

    public List<FeedbackDTO> getAllFeedbackByeIDAnd_uID(Integer eID, Integer uID) {
        try {
            List<FeedbackDTO> feedbackDTOList = new ArrayList<>();
            studentEventFeedbackRepository.findAllByStudentEvent_Event_eIDAndStudentEvent_User_uID(eID,uID).forEach(studentEventFeedback -> {
                FeedbackDTO feedbackDTO = new FeedbackDTO();
                feedbackDTO.setFID(studentEventFeedback.getFeedback().getFID());
                feedbackDTO.setFeedback(studentEventFeedback.getFeedback().getFeedback());
                feedbackDTO.setFeedbackType(studentEventFeedback.getFeedback().getFeedbackType());
                feedbackDTO.setCreatedDateTime(studentEventFeedback.getFeedback().getCreatedDateTime());
                feedbackDTOList.add(feedbackDTO);
            });
            return feedbackDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedback");
        }
    }

}
