package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.entity.Event;
import com.example.eventFlowBackend.entity.EventType;
import com.example.eventFlowBackend.payload.EventDTO;
import com.example.eventFlowBackend.service.AnnouncementService;
import com.example.eventFlowBackend.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventController {

    EventService eventService;
    AnnouncementService announcementService;

    public EventController(EventService eventService, AnnouncementService announcementService) {
        this.eventService = eventService;
        this.announcementService = announcementService;
    }

    @PostMapping("/interview")
    public ResponseEntity<?> createInterview(@RequestBody EventDTO event) {
        try {
            eventService.create(event, EventType.interview);
            return ResponseEntity.ok("Interview created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create event");
        }
    }

    @PostMapping("/workshop")
    public ResponseEntity<?> createWorkshop(@RequestBody EventDTO event) {
        try {
            eventService.create(event, EventType.workshop);
            return ResponseEntity.ok("Workshop created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create event");
        }
    }

    @PostMapping("/attendance/{eID}/{uID}")
    public ResponseEntity<?> markAttendance(@PathVariable Integer eID, @PathVariable Integer uID) {
        try {
            eventService.markAttendance(eID, Long.valueOf(uID));
            return ResponseEntity.ok("Attendance marked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to mark attendance");
        }
    }


    @PutMapping("/{eID}")
    public ResponseEntity<?> update(@PathVariable Integer eID, @RequestBody EventDTO event) {
        try {
            eventService.update(eID, event);
            return ResponseEntity.ok("Event updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update event");
        }
    }

    @PutMapping("/assign/{eID}/{aID}")
    public ResponseEntity<?> assignAnnouncement(@PathVariable Integer eID, @PathVariable Integer aID) {
        try {
            eventService.assignAnnouncement(eID, aID);
            return ResponseEntity.ok("Announcement assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to assign announcement");
        }
    }

    @PutMapping("/unassign/{eID}")
    public ResponseEntity<?> unassignAnnouncement(@PathVariable Integer eID) {
        try {
            Integer aID = eventService.removeAnnouncement(eID);
            if(aID == null) {
                return ResponseEntity.badRequest().body("Failed to unassign announcement");
            } else {
                announcementService.deleteAnnouncement(aID);
            }
            return ResponseEntity.ok("Announcement unassigned successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to unassign announcement");
        }
    }

    @PutMapping("/attendance/{seID}/{points}")
    public ResponseEntity<?> updateAttendance(@PathVariable Integer seID, @PathVariable Integer points) {
        try {
            eventService.updateAttendance(seID, points);
            return ResponseEntity.ok("Attendance updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update attendance");
        }
    }

    @PutMapping("/complete/{eID}")
    public ResponseEntity<?> completeEvent(@PathVariable Integer eID) {
        try {
            eventService.complete(eID);
            return ResponseEntity.ok("Event completed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to complete event");
        }
    }

    @DeleteMapping("/{eID}")
    public ResponseEntity<?> delete(@PathVariable Integer eID) {
        try {
            eventService.delete(eID);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete event");
        }
    }

    @DeleteMapping("/attendance/{seID}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Integer seID) {
        try {
            eventService.deleteAttendance(seID);
            return ResponseEntity.ok("Attendance deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete attendance");
        }
    }

    @GetMapping("/interview")
    public ResponseEntity<?> getInterviews() {
        try {
            return ResponseEntity.ok(eventService.getEvents(EventType.interview, true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get interviews: " + e.getMessage());
        }
    }

    @GetMapping("/workshop")
    public ResponseEntity<?> getWorkshops() {
        try {
            return ResponseEntity.ok(eventService.getEvents(EventType.workshop, true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get workshops: " + e.getMessage());
        }
    }

    @GetMapping("/{eID}")
    public ResponseEntity<Event> getEvent(@PathVariable Integer eID) {
        try {
            return ResponseEntity.ok(eventService.getEvent(eID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/attendance/event/{eID}")
    public ResponseEntity<?> getAttendance(@PathVariable Integer eID) {
        try {
            return ResponseEntity.ok(eventService.getAttendance(eID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get attendance");
        }
    }

    @GetMapping("/attendance/user/{uID}")
    public ResponseEntity<?> getAttendanceByUser(@PathVariable Integer uID) {
        try {
            return ResponseEntity.ok(eventService.getAttendanceByUser(Long.valueOf(uID)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get attendance");
        }
    }



}
