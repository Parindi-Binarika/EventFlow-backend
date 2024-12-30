package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.StudentSuggestion;
import com.example.eventFlowBackend.entity.Suggestion;
import com.example.eventFlowBackend.entity.User;
import com.example.eventFlowBackend.payload.SuggestionDTO;
import com.example.eventFlowBackend.repository.StudentSuggestionRepository;
import com.example.eventFlowBackend.repository.SuggestionRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionService {

    SuggestionRepository suggestionRepository;
    StudentSuggestionRepository studentSuggestionRepository;
    UserRepository userRepository;

    public SuggestionService(SuggestionRepository suggestionRepository,StudentSuggestionRepository studentSuggestionRepository, UserRepository userRepository) {
        this.suggestionRepository = suggestionRepository;
        this.studentSuggestionRepository = studentSuggestionRepository;
        this.userRepository = userRepository;
    }

    public void Create(SuggestionDTO suggestionDTO) {
        try {
            Integer countOfNotExpired = suggestionRepository.countAllByExpired(false);
            if (countOfNotExpired >= 6) {
                throw new Exception("You can't create more than 5 suggestions");
            }
            Suggestion suggestion = new Suggestion();
            suggestion.setTitle(suggestionDTO.getTitle());
            suggestion.setDescription(suggestionDTO.getDescription());
            suggestion.setCreatedBy(userRepository.findById(Long.valueOf(suggestionDTO.getCreatedBy())).orElseThrow(() -> new Exception("User not found")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void vote(Integer sID, Integer uID) {
        try {
            Suggestion suggestion = suggestionRepository.findById(sID).orElseThrow(() -> new Exception("Suggestion not found"));
            User user = userRepository.findById(Long.valueOf(uID)).orElseThrow(() -> new Exception("User not found"));
            boolean isVoted = studentSuggestionRepository.existsByUserAndSuggestion(user, suggestion);
            if (isVoted) {
                studentSuggestionRepository.delete(studentSuggestionRepository.findByUserAndSuggestion(user, suggestion));
            } else {
                StudentSuggestion studentSuggestion = new StudentSuggestion();
                studentSuggestion.setSuggestion(suggestion);
                studentSuggestion.setUser(user);
                studentSuggestionRepository.save(studentSuggestion);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SuggestionDTO> getAllByExpired(Boolean expired) {
        try {
            List<Suggestion> suggestions = suggestionRepository.findAllByExpired(expired);
            List<SuggestionDTO> suggestionDTOS = new ArrayList<>();
            for (Suggestion suggestion : suggestions) {
                SuggestionDTO suggestionDTO = new SuggestionDTO();
                suggestionDTO.setSID(suggestion.getSuID());
                suggestionDTO.setTitle(suggestion.getTitle());
                suggestionDTO.setDescription(suggestion.getDescription());
                suggestionDTO.setCreatedAt(suggestion.getCreatedAt());
                suggestionDTO.setExpired(suggestion.isExpired());
                suggestionDTO.setCreatedBy(suggestion.getCreatedBy().getUID());
                suggestionDTO.setVotes(studentSuggestionRepository.countAllBySuggestion(suggestion));
                suggestionDTOS.add(suggestionDTO);
            }
            return suggestionDTOS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void expireSuggestions() {
        try {
            List<Suggestion> suggestions = suggestionRepository.findAllByExpired(false);
            for (Suggestion suggestion : suggestions) {
                if (suggestion.getCreatedAt().plusDays(5).isBefore(java.time.LocalDateTime.now())) {
                    suggestion.setExpired(true);
                    suggestionRepository.save(suggestion);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
