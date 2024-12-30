package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Portfolio;
import com.example.eventFlowBackend.entity.Project;
import com.example.eventFlowBackend.payload.ProjectDTO;
import com.example.eventFlowBackend.repository.PortfolioRepository;
import com.example.eventFlowBackend.repository.ProjectRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    PortfolioRepository portfolioRepository;
    ProjectRepository projectRepository;

    public ProjectService(PortfolioRepository portfolioRepository, ProjectRepository projectRepository) {
        this.portfolioRepository = portfolioRepository;
        this.projectRepository = projectRepository;
    }


    public void createProject(Integer portfolioId, ProjectDTO projectDTO) {
        try {
            Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(() -> new RuntimeException("Portfolio not found"));
            Project project = new Project();
            project.setDisplayName(projectDTO.getDisplayName());
            project.setDescription(projectDTO.getDescription());
            project.setTechnologies(projectDTO.getTechnologies());
            project.setResourcesLink(projectDTO.getResourcesLink());
            project.setPortfolio(portfolio);
            projectRepository.save(project);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create project");
        }
    }

    public void updateProject(Integer id, ProjectDTO projectDTO) {
        try {
            Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
            project.setDisplayName(projectDTO.getDisplayName());
            project.setDescription(projectDTO.getDescription());
            project.setTechnologies(projectDTO.getTechnologies());
            project.setResourcesLink(projectDTO.getResourcesLink());
            projectRepository.save(project);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update project");
        }
    }

    public void deleteProject(Integer id) {
        try {
            projectRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete project");
        }
    }

    public void assignProjectToPortfolio(Integer projectId, Integer portfolioId) {
        try {
            Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
            Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(() -> new RuntimeException("Portfolio not found"));
            project.setPortfolio(portfolio);
            projectRepository.save(project);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to assign project to portfolio");
        }
    }

    public List<Project> getProjectsByPortfolio(Integer portfolioId) {
        try {
            Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(() -> new RuntimeException("Portfolio not found"));
            return projectRepository.findByPortfolio_pID(portfolio.getPID());
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get projects by portfolio");
        }
    }

}
