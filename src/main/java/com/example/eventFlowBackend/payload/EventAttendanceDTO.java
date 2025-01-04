package com.example.eventFlowBackend.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventAttendanceDTO {
    private Integer seID;
    private Integer uID;
    private Integer eID;
    private Boolean attended;
    private String title;
    private String description;
    private Integer points;
    private Integer individual_fID;
    private String StudentName;
    private String mobile;
    private LocalDateTime date;
}
