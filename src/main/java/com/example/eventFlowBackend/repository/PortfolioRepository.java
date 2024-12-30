package com.example.eventFlowBackend.repository;

import com.example.eventFlowBackend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio,Integer> {
    Portfolio findByUser_uID(Integer userUID);
    Portfolio findByUser_uIDAndIsPublic_True(Integer userUID);
}
