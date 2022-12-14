package com.example.demo.business.mappers;

import com.example.demo.business.repository.model.TrainingDAO;
import com.example.demo.model.Training;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrainingMapStructMapper {

    TrainingDAO mapToDAO(Training training);

    Training mapFromDAO(TrainingDAO trainingDAO);

}
