package com.example.eventFlowBackend.controller;

import com.example.eventFlowBackend.entity.User;
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

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserDTO request) {
        try {
            Optional<User> user = userService.findByEmail(request.getEmail());
            if (user.isPresent()) {
                return ResponseEntity.status(403).build();
            }
            userService.create(request);
            return ResponseEntity.status(200).body("User created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            userService.update(id, updatedUser);
            return ResponseEntity.status(200).body("User updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody User request) {
        try {
            userService.updatePassword(id, request);
            return ResponseEntity.status(200).body("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(200).body("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findByIsActiveTrue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findByID(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{nic}")
    public ResponseEntity<List<UserDTO>> searchByNic(@PathVariable String nic) {
        try {
            return ResponseEntity.ok(userService.findByNicStartingWith(nic));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }




}
