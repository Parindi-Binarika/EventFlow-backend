package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.payload.SuggestionDTO;
import com.example.eventFlowBackend.service.SuggestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SuggestionDTO request) {
        try {
            suggestionService.Create(request);
            return ResponseEntity.status(200).body("Suggestion created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/vote/{sID}/{uID}")
    public ResponseEntity<?> vote(@RequestBody Integer sID, @RequestBody Integer uID) {
        try {
            suggestionService.vote(sID, uID);
            return ResponseEntity.status(200).body("Voted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/suggestion/active")
    public ResponseEntity<?> getActiveSuggestions() {
        try {
            return ResponseEntity.status(200).body(suggestionService.getAllByExpired(false));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/suggestion/expired")
    public ResponseEntity<?> getExpiredSuggestions() {
        try {
            return ResponseEntity.status(200).body(suggestionService.getAllByExpired(true));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


}
