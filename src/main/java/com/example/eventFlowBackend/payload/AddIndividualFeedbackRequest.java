package com.example.eventFlowBackend.payload;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AddIndividualFeedbackRequest {
    private ArrayList<IndividualFeedbackDTO> individualFeedbackDTOS;
}
