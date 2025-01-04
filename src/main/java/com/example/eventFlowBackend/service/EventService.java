package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.*;
import com.example.eventFlowBackend.payload.*;
import com.example.eventFlowBackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    EventRepository eventRepository;
    UserRepository userRepository;
    AnnouncementRepository announcementRepository;
    StudentEventFeedbackRepository studentEventFeedbackRepository;

    StudentEventRepository studentEventRepository;

    AnnouncementService announcementService;
    BatchService batchService;
    UserService userService;

    public EventService(UserService userService,BatchService batchService,EventRepository eventRepository, UserRepository userRepository, AnnouncementRepository announcementRepository, StudentEventRepository studentEventRepository, StudentEventFeedbackRepository studentEventFeedbackRepository, AnnouncementService announcementService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
        this.studentEventRepository = studentEventRepository;
        this.studentEventFeedbackRepository = studentEventFeedbackRepository;
        this.announcementService = announcementService;
        this.batchService = batchService;
        this.userService = userService;
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

    public void assignAnnouncement(Integer eID, AnnouncementDTO announcementDTO) {
        try {
            AnnouncementDTO resannouncementDTO = announcementService.createAnnouncement(announcementDTO);
            Event event = eventRepository.findById(eID).orElseThrow(() -> new RuntimeException("Event not found"));
            if (event.getAnnouncement() != null) {
                throw new RuntimeException("Announcement already assigned");
            }
            Announcement announcement = announcementRepository.findById(resannouncementDTO.getAID()).orElseThrow(() -> new RuntimeException("Announcement not found"));
            event.setAnnouncement(announcement);
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign announcement: " + e.getMessage());
        }
    }

    public void complete(Integer eID) {
        try {
            Event event = eventRepository.findById(eID).get();
            event.setIsActive(false);
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to complete event");
        }
    }

    public Event getEvent(Integer eID) {
        try {
            return eventRepository.findById(eID).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get event");
        }
    }

    public List<EventDTO> getEvents(EventType eventType, Boolean isActive) {
        try {
            System.out.println(eventType);
            List<EventDTO> eventDTOs = new ArrayList<>();
            eventRepository.findAllByEventTypeAndIsActive(eventType,isActive).forEach(event -> {
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
                    StudentEventFeedback studentEventFeedback = studentEventFeedbackRepository.findByStudentEvent_IdAndFeedback_FeedbackType(studentEventRepository.findTopByEvent_eID(event.getEID()).getId(),FeedbackType.group);
                    if (studentEventFeedback != null){
                        eventDTO.setGroup_fID(studentEventFeedback.getFeedback().getFID());
                    }
                }
                eventDTOs.add(eventDTO);
            });
            return eventDTOs;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            throw new RuntimeException("Failed to get events: " + e.getMessage());
        }
    }

    public void markAttendance(Integer eID, AttendanceDTO attendanceDTO) {
        try {
            Event event = eventRepository.findById(eID).orElseThrow(() -> new RuntimeException("Event not found"));
            if (event.getAnnouncement() == null) {
                throw new RuntimeException("Can not make attendance for event without announcement");
            }
            if (!studentEventRepository.existsByEvent_eID(eID)) {
                List<UserDTO> userDTOS = new ArrayList<>();
                List<AssignedBatchResponse> batchDTOS = announcementService.getAssignBatchesByaID(event.getAnnouncement().getAID());
                List<AssignedStudentResponse> studentDTOS = announcementService.getAssignStudentsByaID(event.getAnnouncement().getAID());
                batchDTOS.forEach(batchDTO -> {
                    userDTOS.addAll(batchService.findUsersByBatch(Long.valueOf(batchDTO.getBID())));
                });
                studentDTOS.forEach(studentDTO -> {
                    userDTOS.add(userService.findByID(Long.valueOf(studentDTO.getUID())));
                });
                userDTOS.forEach(userDTO -> {
                    if (studentEventRepository.findByEvent_eIDAndUser_uID(eID, Long.valueOf(userDTO.getUID())).isEmpty()) {
                        StudentEvent studentEvent = new StudentEvent();
                        studentEvent.setEvent(event);
                        studentEvent.setUser(userRepository.findById(Long.valueOf(userDTO.getUID())).get());
                        studentEventRepository.save(studentEvent);
                    }
                });
                System.out.println("Attendance marked for all students");
            }
            attendanceDTO.getStudents().forEach(uID -> {
                if (studentEventRepository.findByEvent_eIDAndUser_uID(eID, uID).isPresent()) {
                    StudentEvent studentEvent = studentEventRepository.findByEvent_eIDAndUser_uID(eID, uID).get();
                    studentEvent.setAttended(true);
                    studentEventRepository.save(studentEvent);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark attendance: " + e.getMessage());
        }
    }

    public void deleteAttendance(Integer seID) {
        try {
            studentEventRepository.deleteById(seID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete attendance");
        }
    }

    public void delete(Integer eID) {
        try {
            Event event = eventRepository.findById(eID).get();
            if (event.getAnnouncement() != null) {
                throw new RuntimeException("Announcement already assigned cannot delete event");
            }
            eventRepository.deleteById(eID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete event");
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
                eventAttendanceDTO.setAttended(studentEvent.getAttended());
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
                eventAttendanceDTO.setAttended(studentEvent.getAttended());
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

    public EventAttendanceAnalysisDTO getAttendanceAnalysis(Integer eID) {
        try {
            EventAttendanceAnalysisDTO eventAttendanceAnalysisDTO = new EventAttendanceAnalysisDTO();
            eventAttendanceAnalysisDTO.setAllStudents(studentEventRepository.countAllByEvent_eID(eID));
            eventAttendanceAnalysisDTO.setAttendedStudents(studentEventRepository.countAllByEvent_eIDAndAttended(eID,true));
            eventAttendanceAnalysisDTO.setAbsentStudents(studentEventRepository.countAllByEvent_eIDAndAttended(eID,false));
            return eventAttendanceAnalysisDTO;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attendance analysis");
        }
    }
}
