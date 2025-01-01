package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEventRepository extends JpaRepository<StudentEvent, Integer> {
    List<StudentEvent> findAllByEvent_eID(Integer eventEID);
    List<StudentEvent> findAllByUser_uID(Integer userUID);
    StudentEvent findTopByEvent_eID(Integer eventEID);
}
