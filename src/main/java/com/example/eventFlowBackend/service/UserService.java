package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Batch;
import com.example.eventFlowBackend.entity.Role;
import com.example.eventFlowBackend.entity.User;
import com.example.eventFlowBackend.payload.RegisterRequest;
import com.example.eventFlowBackend.repository.BatchRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setNic(request.getNic());
        user.setCreatedBy(request.getCreatedBy());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole().name()));
        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setMobile(updatedUser.getMobile());
            user.setNic(updatedUser.getNic());
            user.setCreatedBy(updatedUser.getCreatedBy());
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void delete(Long id) {
        userRepository.findById(id).map(user -> {
            user.setIsActive(false);
            return userRepository.save(user);
        }).orElseThrow(()-> new RuntimeException("User not found"));
    }

    public Optional<User> findByID(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
