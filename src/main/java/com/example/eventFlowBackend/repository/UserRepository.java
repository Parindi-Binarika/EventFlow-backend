package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByIsActiveTrue();
    List<User> findByNicStartingWithAndIsActiveTrue(String nic);
}