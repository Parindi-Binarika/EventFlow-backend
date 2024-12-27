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

    @DeleteMapping("/{eID}")
    public ResponseEntity<?> delete(@PathVariable Integer eID) {
        try {
            eventService.delete(eID);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete event");
        }
    }

    @GetMapping("/interview")
    public ResponseEntity<?> getInterviews() {
        try {
            return ResponseEntity.ok(eventService.getEvents(EventType.interview));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get interviews");
        }
    }

    @GetMapping("/workshop")
    public ResponseEntity<?> getWorkshops() {
        try {
            return ResponseEntity.ok(eventService.getEvents(EventType.workshop));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get workshops");
        }
    }

    @GetMapping("/eID")
    public ResponseEntity<Event> getEvent(@PathVariable Integer eID) {
        try {
            return ResponseEntity.ok(eventService.getEvent(eID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



}
