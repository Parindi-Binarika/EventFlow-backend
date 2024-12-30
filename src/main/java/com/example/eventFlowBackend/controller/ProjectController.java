package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.payload.ProjectDTO;
import com.example.eventFlowBackend.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/create/{pid}")
    public ResponseEntity<?> createProject(@PathVariable Integer pid, @RequestBody ProjectDTO projectDTO) {
        try {
            projectService.createProject(pid, projectDTO);
            return ResponseEntity.status(200).body("Project created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to create project");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Integer id, @RequestBody ProjectDTO projectDTO) {
        try {
            projectService.updateProject(id, projectDTO);
            return ResponseEntity.status(200).body("Project updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to update project");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.status(200).body("Project deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to delete project");
        }
    }

    @GetMapping("/{pid}")
    public ResponseEntity<?> getProjects(@PathVariable Integer pid) {
        try {
            return ResponseEntity.status(200).body(projectService.getProjectsByPortfolio(pid));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to get projects");
        }
    }


}
