package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.entity.Role;
import com.example.eventFlowBackend.entity.User;
import com.example.eventFlowBackend.payload.BatchDTO;
import com.example.eventFlowBackend.payload.UserDTO;
import com.example.eventFlowBackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/admin")
    public ResponseEntity<?> create(@RequestBody UserDTO request) {
        try {
            userService.create(Role.admin,request);
            return ResponseEntity.status(200).body("User created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("User not created");
        }
    }

    @PostMapping("/create/student")
    public ResponseEntity<?> createStudent(@RequestBody UserDTO request) {
        try {
            userService.create(Role.student,request);
            return ResponseEntity.status(200).body("User created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("User not created");
        }
    }

    @PostMapping("/create/lecturer")
    public ResponseEntity<?> createTeacher(@RequestBody UserDTO request) {
        try {
            userService.create(Role.lecturer,request);
            return ResponseEntity.status(200).body("User created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("User not created");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) {
        try {
            userService.update(id, updatedUser);
            return ResponseEntity.status(200).body("User updated successfully");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body("User not updated");
        }
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UserDTO request) {
        try {
            userService.updatePassword(id, request);
            return ResponseEntity.status(200).body("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Password not updated");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(200).body("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("User not deleted");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findByIsActiveTrue());
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        try {
            return ResponseEntity.ok(userService.findByRole(Role.student));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Students not found");
        }
    }

    @GetMapping("/lecturers")
    public ResponseEntity<?> getAllLecturers() {
        try {
            return ResponseEntity.ok(userService.findByRole(Role.lecturer));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Lecturers not found");
        }
    }

    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins() {
        try {
            return ResponseEntity.ok(userService.findByRole(Role.admin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Admins not found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findByID(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("User not found");
        }
    }

    @GetMapping("/search/{nic}")
    public ResponseEntity<?> searchByNic(@PathVariable String nic) {
        try {
            return ResponseEntity.ok(userService.findByNicStartingWith(nic));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("User not found");
        }
    }




}
