package com.example.eventFlowBackend.payload;

import lombok.Data;

@Data
public class EventAttendanceAnalysisDTO {
    private Integer allStudents;
    private Integer attendedStudents;
    private Integer absentStudents;
}
