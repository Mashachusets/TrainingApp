package com.example.demo.controller;

import com.example.demo.business.service.TrainingService;
import com.example.demo.model.Training;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingController.class)
public class TrainingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TrainingService trainingService;
	@InjectMocks
	private TrainingController trainingController;

	@Test
	public void updateTrainingTest_CorrectId() throws Exception {
		Training training = createTraining();
		when(trainingService.updateTrainingById(1L, training)).thenReturn(training);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainings/1")
						.content(asJsonString(training))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(status().isCreated());
		verify(trainingService, times(1)).updateTrainingById(training.getId(), training);
	}

	@Test
	public void deleteTrainingTest_CorrectId() throws Exception {
		Training training = createTraining();
		when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.of(training));
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainings/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
		verify(trainingService, times(1)).deleteTrainingById(training.getId());
	}

	@Test
	public void deleteTrainingTest_NoObjectToDelete() throws Exception {
		Training training = createTraining();
		when(trainingService.findTrainingById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainings/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
		verify(trainingService, times(0)).deleteTrainingById(training.getId());
	}

	@Test
	public void deleteTrainingTest_WrongId() throws Exception {
		Training training = createTraining();
		when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.of(training));
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainings/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
		verify(trainingService, times(0)).deleteTrainingById(training.getId());
	}

	private Training createTraining() {
		Training training = new Training();
		training.setId(1L);
		training.setTitle("some Title");
		training.setDescription("some Description");
		return training;
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testFindTrainingByIdController_successful() throws Exception {
		Training training = createTraining();

		when(trainingService.findTrainingById(training.getId())).thenReturn(Optional.of(training));

		ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.get("/api/trainings/1")
						.content(asJsonString(training))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(status().isOk());

		verify(trainingService, times(1)).findTrainingById(training.getId());
	}

	@Test
	void testFindTrainingByIdController_invalid() throws Exception {
		Training training = new Training();

		when(trainingService.findTrainingById(anyLong())).thenReturn(Optional.empty());

		ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.get("/api/trainings/0")
						.content(asJsonString(training))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(trainingService, times(1)).findTrainingById(anyLong());
	}

	@Test
	void testSaveTrainingController_successful() throws Exception {
		Training training = new Training();
		training.setTitle("title");
		training.setDescription("description");

		when(trainingService.save(training)).thenReturn(training);

		ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/api/trainings")
						.content(asJsonString(training))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		verify(trainingService, times(1)).save(training);
	}

	@Test
	void testSaveTraining_invalid() throws Exception {

		ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.post("/api/trainings")
						.content(asJsonString("training"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		verify(trainingService, times(0)).save(any());
	}
}