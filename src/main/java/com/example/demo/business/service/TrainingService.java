package com.example.demo.business.service;

import com.example.demo.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {

    void deleteTrainingById(Long id);

    Training updateTrainingById(Long id, Training training);

    Optional<Training> findTrainingById(Long id);

    Training save(Long id, Training training) throws Exception;

    List<Training> findAll();
}
