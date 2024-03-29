package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@ApiModel(description = "Model of trainings data ")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @ApiModelProperty(notes = "Unique id of trainings")
    private Long id;
    @ApiModelProperty(notes = "Training title")
    private String title;
    @ApiModelProperty(notes = "Training description")
    private String description;
}