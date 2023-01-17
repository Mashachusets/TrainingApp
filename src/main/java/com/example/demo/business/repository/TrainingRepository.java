package com.example.demo.business.repository;

import com.example.demo.business.repository.model.TrainingDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingDAO, Long> {
}