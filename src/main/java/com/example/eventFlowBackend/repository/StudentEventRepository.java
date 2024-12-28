package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEventRepository extends JpaRepository<StudentEvent, Integer> {
    List<StudentEvent> findAllByEvent_EID(Integer eID);
    List<StudentEvent> findAllByUser_UID(Integer uID);
}
