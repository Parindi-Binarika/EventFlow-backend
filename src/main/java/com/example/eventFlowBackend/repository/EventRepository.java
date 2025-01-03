package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Event;
import com.example.eventFlowBackend.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByEventTypeAndIsActive(EventType eventType, Boolean isActive);
    Optional<Event> findByAnnouncement_aID(Integer aID);
}
