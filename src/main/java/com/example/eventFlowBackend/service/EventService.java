package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Event;
import com.example.eventFlowBackend.entity.EventType;
import com.example.eventFlowBackend.payload.EventDTO;
import com.example.eventFlowBackend.repository.AnnouncementRepository;
import com.example.eventFlowBackend.repository.EventRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    EventRepository eventRepository;
    UserRepository userRepository;
    AnnouncementRepository announcementRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, AnnouncementRepository announcementRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
    }

    public void create(EventDTO eventDTO, EventType eventType) {
        try {
            Event event = new Event();
            event.setTitle(eventDTO.getTitle());
            event.setDescription(eventDTO.getDescription());
            event.setStartDateTime(eventDTO.getStartDateTime());
            event.setEventType(eventType);
            event.setCreatedBy(userRepository.findById(Long.valueOf(eventDTO.getCreatedBy())).get());
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create event");
        }
    }

    public void update(Integer eID,EventDTO eventDTO) {
        try {
            Event event = eventRepository.findById(eID).get();
            event.setTitle(eventDTO.getTitle());
            event.setDescription(eventDTO.getDescription());
            event.setStartDateTime(eventDTO.getStartDateTime());
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update event");
        }
    }

    public void assignAnnouncement(Integer eID, Integer aID) {
        try {
            Event event = eventRepository.findById(eID).get();
            event.setAnnouncement(announcementRepository.findById(aID).get());
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign announcement");
        }
    }

    public Integer removeAnnouncement(Integer eID) {
        try {
            Event event = eventRepository.findById(eID).get();
            Integer aID = event.getAnnouncement().getAID();
            event.setAnnouncement(null);
            eventRepository.save(event);
            return aID;
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove announcement");
        }
    }

    public void delete(Integer eID) {
        try {
            Event event = eventRepository.findById(eID).get();
            if(event.getAnnouncement().getIsSent()) {
                throw new RuntimeException("Cannot delete event with sent announcement");
            }
            event.setIsActive(false);
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete event");
        }
    }

    public Event getEvent(Integer eID) {
        try {
            return eventRepository.findById(eID).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get event");
        }
    }

    public List<EventDTO> getEvents(EventType eventType) {
        try {
            List<EventDTO> eventDTOs = new ArrayList<>();
            eventRepository.findAllByEventTypeAndIsActiveTrue(eventType).forEach(event -> {
                EventDTO eventDTO = new EventDTO();
                eventDTO.setEID(event.getEID());
                eventDTO.setTitle(event.getTitle());
                eventDTO.setDescription(event.getDescription());
                eventDTO.setStartDateTime(event.getStartDateTime());
                eventDTO.setEventType(event.getEventType());
                eventDTO.setIsActive(event.getIsActive());
                eventDTO.setCreatedBy(event.getCreatedBy().getUID().intValue());
                if (event.getAnnouncement() != null) {
                    eventDTO.setAID(event.getAnnouncement().getAID());
                }
                eventDTOs.add(eventDTO);
            });
            return eventDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get events");
        }
    }
}
