package com.example.eventFlowBackend.payload;

import com.example.eventFlowBackend.entity.FeedbackType;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FeedbackDTO {
    private Integer fID;
    private String feedback;
    private FeedbackType feedbackType;
    private LocalDateTime createdDateTime;
}
