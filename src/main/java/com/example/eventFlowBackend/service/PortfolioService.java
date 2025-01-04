package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Portfolio;
import com.example.eventFlowBackend.payload.PortfolioDTO;
import com.example.eventFlowBackend.repository.PortfolioRepository;
import com.example.eventFlowBackend.repository.ProjectRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    PortfolioRepository portfolioRepository;
    UserRepository userRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    public void createPortfolio(Integer uid,PortfolioDTO portfolioDTO) {
        try {
            if (userRepository.findById(Long.valueOf(uid)).isEmpty()) {
                throw new RuntimeException("User not found");
            }
            Portfolio portfolio = new Portfolio();
            portfolio.setDisplayName(portfolioDTO.getDisplayName());
            portfolio.setDescription(portfolioDTO.getDescription());
            portfolio.setTechnologies(portfolioDTO.getTechnologies());
            portfolio.setGithubUsername(portfolioDTO.getGithubUsername());
            portfolio.setLinkedinUsername(portfolioDTO.getLinkedinUsername());
            portfolio.setUser(userRepository.findById(Long.valueOf(uid)).get());
            portfolioRepository.save(portfolio);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create portfolio: " + e.getMessage());
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
            return getPortfolioDTO(portfolio, portfolioDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get portfolio: " + e.getMessage());
        }
    }

    private PortfolioDTO getPortfolioDTO(Portfolio portfolio, PortfolioDTO portfolioDTO) {
        portfolioDTO.setDisplayName(portfolio.getDisplayName());
        portfolioDTO.setDescription(portfolio.getDescription());
        portfolioDTO.setTechnologies(portfolio.getTechnologies());
        portfolioDTO.setGithubUsername(portfolio.getGithubUsername());
        portfolioDTO.setLinkedinUsername(portfolio.getLinkedinUsername());
        portfolioDTO.setIsPublic(portfolio.getIsPublic());
        portfolioDTO.setUserId(portfolio.getUser().getUID());
        return portfolioDTO;
    }

    public PortfolioDTO getPortfolioByUserId(Integer userId) {
        try {
            Portfolio portfolio = portfolioRepository.findByUser_uID(userId);
            PortfolioDTO portfolioDTO = new PortfolioDTO();
            portfolioDTO.setPID(portfolio.getPID());
            return getPortfolioDTO(portfolio, portfolioDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get portfolio");
        }
    }

    public PortfolioDTO getPublicPortfoliosByUserId(Integer userId) {
        try {
            if (userRepository.findById(Long.valueOf(userId)).isEmpty()) {
                throw new RuntimeException("User not found");
            }
            if (portfolioRepository.findByUser_uIDAndIsPublic_True(userId).isEmpty()) {
                throw new RuntimeException("Portfolio not public");
            }
            Portfolio portfolio = portfolioRepository.findByUser_uIDAndIsPublic_True(userId).get();
            PortfolioDTO portfolioDTO = new PortfolioDTO();
            portfolioDTO.setPID(portfolio.getPID());
            return getPortfolioDTO(portfolio, portfolioDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get portfolio: " + e.getMessage());
        }
    }

}
