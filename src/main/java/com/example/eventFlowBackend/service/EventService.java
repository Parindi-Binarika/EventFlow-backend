package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Event;
import com.example.eventFlowBackend.entity.EventType;
import com.example.eventFlowBackend.entity.FeedbackType;
import com.example.eventFlowBackend.entity.StudentEvent;
import com.example.eventFlowBackend.payload.EventAttendanceDTO;
import com.example.eventFlowBackend.payload.EventDTO;
import com.example.eventFlowBackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    EventRepository eventRepository;
    UserRepository userRepository;
    AnnouncementRepository announcementRepository;
    StudentEventFeedbackRepository studentEventFeedbackRepository;

    StudentEventRepository studentEventRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, AnnouncementRepository announcementRepository, StudentEventRepository studentEventRepository, StudentEventFeedbackRepository studentEventFeedbackRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
        this.studentEventRepository = studentEventRepository;
        this.studentEventFeedbackRepository = studentEventFeedbackRepository;
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
            System.out.println(eventType);
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
                if (studentEventRepository.findTopByEvent_eID(event.getEID()) != null) {
                    eventDTO.setGroup_fID(studentEventFeedbackRepository.findByStudentEvent_IdAndFeedback_FeedbackType(studentEventRepository.findTopByEvent_eID(event.getEID()).getId(),FeedbackType.group).getFeedback().getFID());
                }
                eventDTOs.add(eventDTO);
            });
            return eventDTOs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to get events");
        }
    }

    public void markAttendance(Integer eID, Long uID) {
        try {
            StudentEvent studentEvent = new StudentEvent();
            studentEvent.setEvent(eventRepository.findById(eID).get());
            studentEvent.setUser(userRepository.findById(uID).get());
            studentEventRepository.save(studentEvent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark attendance");
        }
    }

    public void deleteAttendance(Integer seID) {
        try {
            studentEventRepository.deleteById(seID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete attendance");
        }
    }

    public void updateAttendance(Integer seID, Integer points) {
        try {
            StudentEvent studentEvent = studentEventRepository.findById(seID).get();
            studentEvent.setPoints(points);
            studentEventRepository.save(studentEvent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update attendance");
        }
    }

    public List<EventAttendanceDTO> getAttendance(Integer eID) {
        try {
            List<EventAttendanceDTO> eventAttendanceDTOs = new ArrayList<>();
            studentEventRepository.findAllByEvent_eID(eID).forEach(studentEvent -> {
                EventAttendanceDTO eventAttendanceDTO = new EventAttendanceDTO();
                eventAttendanceDTO.setSeID(studentEvent.getId());
                eventAttendanceDTO.setUID(studentEvent.getUser().getUID().intValue());
                eventAttendanceDTO.setEID(studentEvent.getEvent().getEID());
                eventAttendanceDTO.setTitle(studentEvent.getEvent().getTitle());
                eventAttendanceDTO.setDescription(studentEvent.getEvent().getDescription());
                eventAttendanceDTO.setPoints(studentEvent.getPoints());
                eventAttendanceDTO.setStudentName(studentEvent.getUser().getName());
                eventAttendanceDTO.setMobile(studentEvent.getUser().getMobile());
                eventAttendanceDTO.setDate(studentEvent.getDate());
                if (studentEventFeedbackRepository.findByStudentEvent_IdAndFeedback_FeedbackType(studentEvent.getId(),FeedbackType.individual) != null){
                    eventAttendanceDTO.setIndividual_fID(studentEventFeedbackRepository.findByStudentEvent_IdAndFeedback_FeedbackType(studentEvent.getId(),FeedbackType.individual).getFeedback().getFID());
                }
                eventAttendanceDTOs.add(eventAttendanceDTO);
            });
            return eventAttendanceDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attendance");
        }
    }

    public List<EventAttendanceDTO> getAttendanceByUser(Long uID) {
        try {
            List<EventAttendanceDTO> eventAttendanceDTOs = new ArrayList<>();
            studentEventRepository.findAllByUser_uID(Math.toIntExact(uID)).forEach(studentEvent -> {
                EventAttendanceDTO eventAttendanceDTO = new EventAttendanceDTO();
                eventAttendanceDTO.setSeID(studentEvent.getId());
                eventAttendanceDTO.setUID(studentEvent.getUser().getUID().intValue());
                eventAttendanceDTO.setEID(studentEvent.getEvent().getEID());
                eventAttendanceDTO.setTitle(studentEvent.getEvent().getTitle());
                eventAttendanceDTO.setDescription(studentEvent.getEvent().getDescription());
                eventAttendanceDTO.setPoints(studentEvent.getPoints());
                eventAttendanceDTO.setStudentName(studentEvent.getUser().getName());
                eventAttendanceDTO.setMobile(studentEvent.getUser().getMobile());
                eventAttendanceDTO.setDate(studentEvent.getDate());
                if (studentEventFeedbackRepository.findByStudentEvent_IdAndFeedback_FeedbackType(studentEvent.getId(),FeedbackType.individual) != null){
                    eventAttendanceDTO.setIndividual_fID(studentEventFeedbackRepository.findByStudentEvent_IdAndFeedback_FeedbackType(studentEvent.getId(),FeedbackType.individual).getFeedback().getFID());
                }
                eventAttendanceDTOs.add(eventAttendanceDTO);
            });
            return eventAttendanceDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attendance");
        }
    }
}
