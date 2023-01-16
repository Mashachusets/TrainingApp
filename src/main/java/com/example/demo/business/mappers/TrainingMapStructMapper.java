package com.example.demo.business.mappers;

import com.example.demo.business.repository.model.TrainingDAO;
import com.example.demo.model.Training;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingMapStructMapper {

    TrainingDAO mapToDAO(Training training);

    Training mapFromDAO(TrainingDAO trainingDAO);

}
