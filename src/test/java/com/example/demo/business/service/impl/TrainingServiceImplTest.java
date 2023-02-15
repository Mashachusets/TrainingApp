package com.example.demo.business.service.impl;

import com.example.demo.business.mappers.TrainingMapStructMapper;
import com.example.demo.business.repository.TrainingRepository;
import com.example.demo.business.repository.model.TrainingDAO;
import com.example.demo.model.Training;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

	@InjectMocks
	TrainingServiceImpl trainingServiceImpl;
	@Mock
	TrainingRepository trainingRepository;
	@Mock
	TrainingMapStructMapper trainingMapStructMapper;

	TrainingDAO trainingDAO;
	Training training;

	@BeforeEach
	public void init() {

		training = createTraining(1L,"some title", "some description");
		trainingDAO = createTrainingDAO(1L,"some title", "some description");
	}

	@Test
	public void updateTrainingById() {

		when(trainingMapStructMapper.mapToDAO(training)).thenReturn(trainingDAO);
		when(trainingMapStructMapper.mapFromDAO(trainingDAO)).thenReturn(training);
		when(trainingRepository.save(trainingDAO)).thenReturn(trainingDAO);
        Training savedTraining = trainingServiceImpl.updateTrainingById(anyLong(), training);
		verify(trainingRepository, times(1)).save(trainingDAO);
		assertEquals(trainingDAO.getId(), savedTraining.getId());
		assertEquals(trainingDAO.getTitle(), savedTraining.getTitle());
        assertEquals(trainingDAO.getDescription(), savedTraining.getDescription());
	}

	@Test
	public void updateTrainingWithInvalidInformation()   {
		when(trainingMapStructMapper.mapToDAO(training)).thenReturn(trainingDAO);
		when(trainingRepository.save(trainingDAO)).thenThrow(new IllegalArgumentException());
		Exception exception = assertThrows(IllegalArgumentException.class, () -> trainingServiceImpl.updateTrainingById(anyLong(), training));
		assertEquals(IllegalArgumentException.class,exception.getClass());
	}

	@Test
	public void findTrainingById() {

		when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(trainingDAO));
		when(trainingMapStructMapper.mapFromDAO(trainingDAO)).thenReturn(training);
        trainingServiceImpl.findTrainingById(training.getId());
		verify(trainingRepository, times(1)).findById(1L);
		Assertions.assertTrue(trainingServiceImpl.findTrainingById(anyLong()).isPresent());
		assertThat(trainingServiceImpl.findTrainingById(trainingDAO.getId()).get().getId()).isEqualTo(1L);
		assertThat(trainingServiceImpl.findTrainingById(trainingDAO.getId()).get().getTitle()).isEqualTo("some title");
        assertThat(trainingServiceImpl.findTrainingById(trainingDAO.getId()).get().getDescription()).isEqualTo("some description");
	}

	@Test
	public void findTrainingByInvalidId() {
		when(trainingRepository.findById(anyLong())).thenReturn(Optional.empty());
		Optional<Training> foundTraining = trainingServiceImpl.findTrainingById(anyLong());
		Assertions.assertFalse(foundTraining.isPresent());
		assertThat(foundTraining).isEmpty();
		verify(trainingRepository, times(1)).findById(anyLong());
	}

	@Test
	public void deleteTrainingById() {

		trainingServiceImpl.deleteTrainingById(anyLong());
		verify(trainingRepository).deleteById(anyLong());
		verify(trainingRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void deleteTrainingByInvalidId() {
		doThrow(new IllegalArgumentException()).when(trainingRepository).deleteById(anyLong());
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> trainingServiceImpl.deleteTrainingById(anyLong()));
		assertEquals(IllegalArgumentException.class,exception.getClass());
	}

	@Test
	void saveTraining_successful() throws Exception {
		when(trainingRepository.save(trainingDAO)).thenReturn(trainingDAO);
		when(trainingMapStructMapper.mapFromDAO(trainingDAO)).thenReturn(training);
		when(trainingMapStructMapper.mapToDAO(training)).thenReturn(trainingDAO);
        Training trainingSaved = trainingServiceImpl.save(training);
		Assert.assertEquals(training, trainingSaved);
		verify(trainingRepository, times(1)).save(trainingDAO);
	}

	@Test
	void saveTraining_invalid() {
		when(trainingRepository.save(trainingDAO)).thenThrow(new IllegalArgumentException());
		when(trainingMapStructMapper.mapToDAO(training)).thenReturn(trainingDAO);
		Assertions.assertThrows(IllegalArgumentException.class, () -> trainingServiceImpl.save(training));
		verify(trainingRepository, times(1)).save(trainingDAO);
	}

	public Training createTraining(Long id, String title, String description) {
        Training training = new Training();
		training.setId(id);
		training.setTitle(title);
        training.setDescription(description);
		return training;
	}

	public TrainingDAO createTrainingDAO(Long id, String title, String description) {
        TrainingDAO trainingDAO = new TrainingDAO();
		trainingDAO.setId(id);
		trainingDAO.setTitle(title);
        trainingDAO.setDescription(description);
		return trainingDAO;
	}
}