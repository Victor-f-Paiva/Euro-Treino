package com.paiva.eurotreino.service;

import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.exception.NotFoundException;
import com.paiva.eurotreino.model.Exercise;
import com.paiva.eurotreino.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepo;

    @InjectMocks
    private ExerciseService exerciseService;

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        exercise = new Exercise(
                "Supino Reto",
                MuscularGroup.PECS,
                MuscularGroup.TRICEPS
        );
        exercise.setId(1L);
    }

    @Test
    void testFindAllExercises() {
        when(exerciseRepo.findAll()).thenReturn(List.of(exercise));

        List<Exercise> exercises = exerciseService.findAllExercises();

        assertEquals(1, exercises.size());
        assertEquals("Supino Reto", exercises.get(0).getName());
        verify(exerciseRepo, times(1)).findAll();
    }

    @Test
    void testFindExerciseById_Success() {
        when(exerciseRepo.findById(1L)).thenReturn(Optional.of(exercise));

        Exercise found = exerciseService.findExerciseById(1L);

        assertNotNull(found);
        assertEquals("Supino Reto", found.getName());
        verify(exerciseRepo, times(1)).findById(1L);
    }

    @Test
    void testFindExerciseById_NotFound() {
        when(exerciseRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exerciseService.findExerciseById(99L));
        verify(exerciseRepo, times(1)).findById(99L);
    }

    @Test
    void testCreateExercise() {
        when(exerciseRepo.save(exercise)).thenReturn(exercise);

        Exercise created = exerciseService.creatExercise(exercise);

        assertNotNull(created);
        assertEquals("Supino Reto", created.getName());
        verify(exerciseRepo, times(1)).save(exercise);
    }

    @Test
    void testUpdateExercise_Success() {
        Exercise newData = new Exercise("Supino Inclinado", MuscularGroup.PECS, MuscularGroup.TRICEPS);

        when(exerciseRepo.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepo.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exercise updated = exerciseService.updateExercise(1L, newData);

        assertEquals("Supino Inclinado", updated.getName());
        assertEquals(MuscularGroup.PECS, updated.getPrimaryGroup());
        verify(exerciseRepo, times(1)).findById(1L);
        verify(exerciseRepo, times(1)).save(exercise);
    }

    @Test
    void testUpdateExercise_NotFound() {
        Exercise newData = new Exercise("Supino Inclinado", MuscularGroup.PECS, MuscularGroup.TRICEPS);

        when(exerciseRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exerciseService.updateExercise(99L, newData));
        verify(exerciseRepo, times(1)).findById(99L);
    }

    @Test
    void testDeleteExercise_Success() {
        doNothing().when(exerciseRepo).deleteById(1L);

        exerciseService.deleteExercise(1L);

        verify(exerciseRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteExercise_NotFound() {
        doThrow(new RuntimeException("not found")).when(exerciseRepo).deleteById(99L);

        assertThrows(NotFoundException.class, () -> exerciseService.deleteExercise(99L));
        verify(exerciseRepo, times(1)).deleteById(99L);
    }
}
