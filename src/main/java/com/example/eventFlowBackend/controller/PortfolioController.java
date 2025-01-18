package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.payload.PortfolioDTO;
import com.example.eventFlowBackend.service.PortfolioService;
import jakarta.persistence.PrePersist;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/create/{uid}")
    public ResponseEntity<?> createPortfolio(@PathVariable Integer uid,@RequestBody PortfolioDTO portfolioDTO) {
        try {
            portfolioService.createPortfolio(uid,portfolioDTO);
            return ResponseEntity.status(200).body("Portfolio created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to create portfolio: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePortfolio(@PathVariable Integer id, @RequestBody PortfolioDTO portfolioDTO) {
        try {
            portfolioService.updatePortfolio(id, portfolioDTO);
            return ResponseEntity.status(200).body("Portfolio updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to update portfolio");
        }
    }

    @PutMapping("/public/{pid}")
    public ResponseEntity<?> updateIsPublic(@PathVariable Integer pid) {
        try {
            portfolioService.updateIsPublic(pid, true);
            return ResponseEntity.status(200).body("Portfolio updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to update portfolio");
        }
    }

    @PutMapping("/private/{pid}")
    public ResponseEntity<?> updateIsPrivate(@PathVariable Integer pid) {
        try {
            portfolioService.updateIsPublic(pid, false);
            return ResponseEntity.status(200).body("Portfolio updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to update portfolio");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPortfolio(@PathVariable Integer id) {
        try {
            PortfolioDTO portfolioDTO = portfolioService.getPortfolio(id);
            return ResponseEntity.status(200).body(portfolioDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to get portfolio: " + e.getMessage());
        }
    }

    @GetMapping("/by_user/{uid}")
    public ResponseEntity<?> getPortfolioByUID(@PathVariable Integer uid) {
        try {
            PortfolioDTO portfolioDTO = portfolioService.getPortfolioByUserId(uid);
            return ResponseEntity.status(200).body(portfolioDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to get portfolio");
        }
    }

    @GetMapping("/public/{uid}")
    public ResponseEntity<?> getPublicPortfoliosByUID(@PathVariable Integer uid) {
        try {
            PortfolioDTO portfolioDTO = portfolioService.getPublicPortfoliosByUserId(uid);
            return ResponseEntity.status(200).body(portfolioDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to get portfolio: " + e.getMessage());
        }
    }
}
