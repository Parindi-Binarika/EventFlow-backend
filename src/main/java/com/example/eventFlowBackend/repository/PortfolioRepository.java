package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio,Integer> {
    Portfolio findByUser_uID(Integer userUID);
    Optional<Portfolio> findByUser_uIDAndIsPublic_True(Integer userUID);
}
