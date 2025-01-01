package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.FeedbackType;
import com.example.eventFlowBackend.entity.StudentEventFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEventFeedbackRepository extends JpaRepository<StudentEventFeedback, Integer> {
    void deleteAllByFeedback_fID(Integer feedbackFID);
    List<StudentEventFeedback> findAllByStudentEvent_Event_eIDAndStudentEvent_User_uID(Integer eventEID, Integer userUID);
    StudentEventFeedback findByStudentEvent_IdAndFeedback_FeedbackType(Integer seID, FeedbackType feedbackType);
}
