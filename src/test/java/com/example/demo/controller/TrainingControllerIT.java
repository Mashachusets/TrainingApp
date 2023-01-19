package com.example.demo.controller;

import com.example.demo.business.mappers.TrainingMapStructMapper;
import com.example.demo.business.repository.TrainingRepository;
import com.example.demo.business.repository.model.TrainingDAO;
import com.example.demo.model.Training;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TrainingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingRepository repository;

    @Autowired
    private TrainingMapStructMapper trainingMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final String DELETE_PATH = "/trainings/{id}";
    private final String UPDATE_PATH ="/trainings/{id}";

    @Test
    void updateTrainingNotFound() throws Exception {

        Training notExistingTraining = createTrainingBadId();

        Training updatedTraining = Training.builder()
                .id(1L)
                .title("title")
                .description("description").build();

        TrainingDAO updatedTrainingDAO = trainingMapper.mapToDAO(updatedTraining);

        when(repository.findById(updatedTraining.getId())).thenReturn(Optional.ofNullable(updatedTrainingDAO));
        when(repository.save(any(TrainingDAO.class))).thenReturn(updatedTrainingDAO);

        mockMvc.perform(put(UPDATE_PATH, notExistingTraining.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTraining)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteTrainingByIdNotFound() throws Exception {

        Training training = createTraining();
        TrainingDAO trainingDAO = trainingMapper.mapToDAO(training);

        when(repository.findById(training.getId())).thenReturn(Optional.empty() );
        doNothing().when(repository).deleteById(training.getId());

        mockMvc.perform(delete(DELETE_PATH, training.getId()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    private Training createTraining(){
        return  Training.builder()
                .id(1L)
                .title("title")
                .description("description").build();
    }

    private Training createTrainingBadId(){
        return  Training.builder()
                .id(20L)
                .title("title")
                .description("description").build();
    }
}