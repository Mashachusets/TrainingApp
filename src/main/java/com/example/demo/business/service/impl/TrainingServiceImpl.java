package com.example.demo.business.service.impl;

import com.example.demo.business.mappers.TrainingMapStructMapper;
import com.example.demo.business.repository.TrainingRepository;
import com.example.demo.business.repository.model.TrainingDAO;
import com.example.demo.business.service.TrainingService;
import com.example.demo.model.Training;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    TrainingMapStructMapper trainingMapper;

    @Override
    public void deleteTrainingById(Long id){
        trainingRepository.deleteById(id);
        log.info("Training with id {} is deleted", id);
    }

    @Override
    public Training updateTrainingById(Long id, Training training) {
        TrainingDAO trainingUpdatedDAO =
                trainingRepository.save(trainingMapper.mapToDAO(training));
        log.info("Training saved: {}", () -> trainingUpdatedDAO);
        return trainingMapper.mapFromDAO(trainingUpdatedDAO);
    }

    @Override
    public Optional<Training> findTrainingById(Long id) {
        Optional<Training> trainingById = trainingRepository.findById(id)
                .map(training -> (trainingMapper.mapFromDAO(training)));
        log.info("Training with id {} is {}", id, trainingById);
        return trainingById;
    }

    @Override
    public List<Training> findAll() {
        log.info("Retrieve list of intern project histories");
        List<TrainingDAO> trainingDAOList = trainingRepository.findAll();
        if (trainingDAOList.isEmpty()) {
            log.warn("Intern project history list is empty! {}", trainingDAOList);
            return null;
        }
        log.debug("Intern project history list is found. Size: {}", trainingDAOList::size);
        return trainingDAOList.stream().map(trainingMapper::mapFromDAO).collect(Collectors.toList());
    }

    @Override
    public Training save(Long id, Training training) throws Exception {
        log.info("Create new Training by passing : {}", training);
        TrainingDAO trainingSaved = trainingRepository.save(trainingMapper.mapToDAO(training));
        log.info("New Training saved: {}", () -> trainingSaved);
        return trainingMapper.mapFromDAO(trainingSaved);
    }
}
