package com.example.demo.business.mappers;

import com.example.demo.business.repository.model.TrainingDAO;
import com.example.demo.model.Training;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-22T12:36:37+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Oracle Corporation)"
)
@Component
public class TrainingMapStructMapperImpl implements TrainingMapStructMapper {

    @Override
    public TrainingDAO mapToDAO(Training training) {
        if ( training == null ) {
            return null;
        }

        TrainingDAO.TrainingDAOBuilder trainingDAO = TrainingDAO.builder();

        trainingDAO.id( training.getId() );
        trainingDAO.title( training.getTitle() );
        trainingDAO.description( training.getDescription() );

        return trainingDAO.build();
    }

    @Override
    public Training mapFromDAO(TrainingDAO trainingDAO) {
        if ( trainingDAO == null ) {
            return null;
        }

        Training.TrainingBuilder training = Training.builder();

        training.id( trainingDAO.getId() );
        training.title( trainingDAO.getTitle() );
        training.description( trainingDAO.getDescription() );

        return training.build();
    }
}
