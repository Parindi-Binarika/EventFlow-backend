package com.example.eventFlowBackend.service;

import com.example.eventFlowBackend.entity.Batch;
import com.example.eventFlowBackend.entity.Role;
import com.example.eventFlowBackend.entity.StudentBatch;
import com.example.eventFlowBackend.entity.User;
import com.example.eventFlowBackend.payload.AttendanceDTO;
import com.example.eventFlowBackend.payload.BatchDTO;
import com.example.eventFlowBackend.payload.UserDTO;
import com.example.eventFlowBackend.repository.BatchRepository;
import com.example.eventFlowBackend.repository.StudentBatchRepository;
import com.example.eventFlowBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {
    private final BatchRepository batchRepository;
    private final StudentBatchRepository studentBatchRepository;
    private final UserRepository userRepository;

    public BatchService(BatchRepository batchRepository, StudentBatchRepository studentBatchRepository, UserRepository userRepository) {
        this.batchRepository = batchRepository;
        this.studentBatchRepository = studentBatchRepository;
        this.userRepository = userRepository;
    }

    public void create(BatchDTO batch) {
        try {
            Batch newBatch = new Batch();
            newBatch.setBatchName(batch.getBatchName());
            newBatch.setCommonEmail(batch.getCommonEmail());
            batchRepository.save(newBatch);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error creating batch");
        }
    }

    public void assignUser(Long bID, AttendanceDTO attendanceDTO) {
        try {
            if (batchRepository.findById(bID).isEmpty()) {
                throw new RuntimeException("Batch not found");
            }
            attendanceDTO.getStudents().forEach(student -> {
                if (userRepository.findById(student).isPresent()) {
                    if (studentBatchRepository.findByUser_uIDAndBatch_bID(student, bID).isEmpty()) {
                        User user = userRepository.findById(student).get();
                        StudentBatch studentBatch = new StudentBatch();
                        studentBatch.setBatch(batchRepository.findById(bID).get());
                        studentBatch.setUser(user);
                        studentBatchRepository.save(studentBatch);
                    }
                }
            });
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error assigning user to batch: " + e.getMessage());
        }
    }

    public void update(Long id, BatchDTO updatedBatch) {
        try {
            batchRepository.findById(id).map(batch -> {
                batch.setBatchName(updatedBatch.getBatchName());
                batch.setCommonEmail(updatedBatch.getCommonEmail());
                return batchRepository.save(batch);
            }).orElseThrow(() -> new RuntimeException("Batch not found"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error updating batch");
        }
    }

    public void delete(Long id) {
        batchRepository.findById(id).map(batch -> {
            batch.setIsActive(false);
            return batchRepository.save(batch);
        }).orElseThrow(() -> new RuntimeException("Batch not found"));
    }

    public void unassignUser(Long id) {
        try {
            studentBatchRepository.deleteById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error unassigning user from batch");
        }
    }

    public List<BatchDTO> findAll() {
        try {
            List<BatchDTO> batches = new ArrayList<>();
            batchRepository.findByIsActiveTrue().forEach(batch -> {
                BatchDTO batchDTO = new BatchDTO();
                batchDTO.setBID(batch.getBID());
                batchDTO.setBatchName(batch.getBatchName());
                batchDTO.setCommonEmail(batch.getCommonEmail());
                batches.add(batchDTO);
            });
            return batches;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error finding all batches");
        }
    }

    public List<UserDTO> findUsersByBatch(Long bID) {
        List<UserDTO> students = new ArrayList<>();
        studentBatchRepository.findByBatch_bIDAndUser_IsActive(bID,Boolean.TRUE).forEach(studentBatch -> {
            User user = studentBatch.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setUID(user.getUID());
            userDTO.setName(user.getName());
            userDTO.setNic(user.getNic());
            userDTO.setEmail(user.getEmail());
            students.add(userDTO);
        });
        return students;
    }

    public Batch findById(Long id) {
        return batchRepository.findById(id).orElseThrow(() -> new RuntimeException("Batch not found"));
    }

    public List<BatchDTO> findBatchesByUser(Long uID) {
        try {
            List<BatchDTO> batches = new ArrayList<>();
            studentBatchRepository.findByUser_uID(uID).forEach(studentBatch -> {
                Batch batch = studentBatch.getBatch();
                BatchDTO batchDTO = new BatchDTO();
                batchDTO.setBID(batch.getBID());
                batchDTO.setBatchName(batch.getBatchName());
                batchDTO.setCommonEmail(batch.getCommonEmail());
                batches.add(batchDTO);
            });
            return batches;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error finding batches by user");
        }
    }
}
