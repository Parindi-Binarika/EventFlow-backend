package com.example.eventFlowBackend.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventAttendanceDTO {
    private Integer seID;
    private Integer uID;
    private Integer eID;
    private String title;
    private String description;
    private Integer points;
    private String StudentName;
    private String mobile;
    private LocalDateTime date;
}
