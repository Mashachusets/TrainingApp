package com.example.demo.controller;

import com.example.demo.business.service.TrainingService;
import com.example.demo.model.Training;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@Api
@Log4j2
@RestController
@RequestMapping("/api")
public class TrainingController {

    @Autowired
    TrainingService trainingService;

    @GetMapping("/trainings")
    @ApiOperation(value = "Finds all trainings status info",
            notes = "Returns the entire list of trainings",
            response = Training.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded", response = Training.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    public ResponseEntity<List<Training>> findAllTrainings(Model theModel) {
        List<Training> trainingList = trainingService.findAll();
        return ResponseEntity.ok(trainingList);
    }

    @GetMapping("/trainings/{id}")
    @ApiOperation(value = "Finds training by Id",
            notes = "If provided valid training Id, returns it",
            response = Training.class)
    public ResponseEntity<Training> getByIdTraining(@ApiParam(value = "id of training", required = true)
                                                    @lombok.NonNull @PathVariable Long idTraining, Model theModel) {
        Optional<Training> theTraining = trainingService.findTrainingById(idTraining);
        return ResponseEntity.of(theTraining);
    }

    @PostMapping("/trainings")
    @ApiOperation(value = "Saves training database",
            notes = "If provided valid training, saves it",
            response = Training.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The training is successfully saved"),
            @ApiResponse(code = 400, message = "Missed required parameters, parameters are not valid"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Training> saveTraining(@RequestBody Training training, BindingResult bindingResult ) throws Exception {
//        if(training.getId()!=0){
//            return ResponseEntity.badRequest().build();
//        }
        Training trainingPost = trainingService.save(training.getId(), training);
        return new ResponseEntity<>(trainingPost, HttpStatus.CREATED);
    }

    @PutMapping("/trainings/{id}")
    @ApiOperation(value = "Updates training",
            notes = "Updates training if provided id exists",
            response = Training.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The training is successfully updated"),
            @ApiResponse(code = 400, message = "Missed required parameters, parameters are not valid"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<Training> updateTraining(@ApiParam(value = "The id of training", required = true)
                                            @NonNull @PathVariable Long id, @RequestBody Training training) throws Exception {
        log.info("Update existing training with ID: {} and new body: {}", id, training);
        Optional<Training> trainingFound = trainingService.findTrainingById(id);
        if (!(trainingFound.isPresent())) {
            log.warn("Training for update with id {} is not found.", id);
            return ResponseEntity.notFound().build();
        }
        trainingService.updateTrainingById(id, training);
        log.info("Training with id {} is updated: {}", id, training);
        return new ResponseEntity<>(training, HttpStatus.CREATED);
    }

    @DeleteMapping("/trainings/{id}")
    @ApiOperation(value = "Deletes training by id",
            notes = "Deletes training if provided id exists",
            response = Training.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The training is successfully deleted"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    public ResponseEntity<Void> deleteTrainingById(@ApiParam(value = "The id of training", required = true) @NonNull @PathVariable Long id) {
        log.info("Delete training by passing ID, where ID is:{}", id);
        Optional<Training> training = trainingService.findTrainingById(id);
        if (!(training.isPresent())) {
            log.warn("Training for deletion with id {} is not found.", id);
            return ResponseEntity.notFound().build();
        }
        trainingService.deleteTrainingById(id);
        log.info("Training with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}