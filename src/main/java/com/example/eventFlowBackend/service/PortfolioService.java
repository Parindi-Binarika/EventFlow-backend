package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Portfolio;
import com.example.eventFlowBackend.payload.PortfolioDTO;
import com.example.eventFlowBackend.repository.PortfolioRepository;
import com.example.eventFlowBackend.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public void createPortfolio(PortfolioDTO portfolioDTO) {
        try {
            Portfolio portfolio = new Portfolio();
            portfolio.setDisplayName(portfolioDTO.getDisplayName());
            portfolio.setDescription(portfolioDTO.getDescription());
            portfolio.setTechnologies(portfolioDTO.getTechnologies());
            portfolio.setGithubUsername(portfolioDTO.getGithubUsername());
            portfolio.setLinkedinUsername(portfolioDTO.getLinkedinUsername());
            portfolioRepository.save(portfolio);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create portfolio");
        }
    }

    public void updatePortfolio(Integer id, PortfolioDTO portfolioDTO) {
        try {
            Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new RuntimeException("Portfolio not found"));
            portfolio.setDisplayName(portfolioDTO.getDisplayName());
            portfolio.setDescription(portfolioDTO.getDescription());
            portfolio.setTechnologies(portfolioDTO.getTechnologies());
            portfolio.setGithubUsername(portfolioDTO.getGithubUsername());
            portfolio.setLinkedinUsername(portfolioDTO.getLinkedinUsername());
            portfolioRepository.save(portfolio);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update portfolio");
        }
    }

    public void updateIsPublic(Integer id, Boolean isPublic) {
        try {
            Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new RuntimeException("Portfolio not found"));
            portfolio.setIsPublic(isPublic);
            portfolioRepository.save(portfolio);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update portfolio");
        }
    }

    public PortfolioDTO getPortfolio(Integer id) {
        try {
            Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new RuntimeException("Portfolio not found"));
            PortfolioDTO portfolioDTO = new PortfolioDTO();
            portfolioDTO.setDisplayName(portfolio.getDisplayName());
            portfolioDTO.setDescription(portfolio.getDescription());
            portfolioDTO.setTechnologies(portfolio.getTechnologies());
            portfolioDTO.setGithubUsername(portfolio.getGithubUsername());
            portfolioDTO.setLinkedinUsername(portfolio.getLinkedinUsername());
            portfolioDTO.setIsPublic(portfolio.getIsPublic());
            portfolioDTO.setUserId(portfolio.getUser().getUID());
            return portfolioDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get portfolio");
        }
    }

    public PortfolioDTO getPortfolioByUserId(Integer userId) {
        try {
            Portfolio portfolio = portfolioRepository.findByUser_uID(userId);
            PortfolioDTO portfolioDTO = new PortfolioDTO();
            portfolioDTO.setPID(portfolio.getPID());
            portfolioDTO.setDisplayName(portfolio.getDisplayName());
            portfolioDTO.setDescription(portfolio.getDescription());
            portfolioDTO.setTechnologies(portfolio.getTechnologies());
            portfolioDTO.setGithubUsername(portfolio.getGithubUsername());
            portfolioDTO.setLinkedinUsername(portfolio.getLinkedinUsername());
            portfolioDTO.setIsPublic(portfolio.getIsPublic());
            portfolioDTO.setUserId(portfolio.getUser().getUID());
            return portfolioDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get portfolio");
        }
    }

    public PortfolioDTO getPublicPortfoliosByUserId(Integer userId) {
        try {
            Portfolio portfolio = portfolioRepository.findByUser_uIDAndIsPublic_True(userId);
            PortfolioDTO portfolioDTO = new PortfolioDTO();
            portfolioDTO.setPID(portfolio.getPID());
            portfolioDTO.setDisplayName(portfolio.getDisplayName());
            portfolioDTO.setDescription(portfolio.getDescription());
            portfolioDTO.setTechnologies(portfolio.getTechnologies());
            portfolioDTO.setGithubUsername(portfolio.getGithubUsername());
            portfolioDTO.setLinkedinUsername(portfolio.getLinkedinUsername());
            portfolioDTO.setIsPublic(portfolio.getIsPublic());
            portfolioDTO.setUserId(portfolio.getUser().getUID());
            return portfolioDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get portfolio");
        }
    }

}
