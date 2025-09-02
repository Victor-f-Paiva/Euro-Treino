package com.paiva.eurotreino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.exception.NotFoundException;
import com.paiva.eurotreino.model.Exercise;
import com.paiva.eurotreino.model.ExerciseSession;
import com.paiva.eurotreino.model.Set;
import com.paiva.eurotreino.model.Workout;
import com.paiva.eurotreino.service.TrainingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainingController.class)
class TrainingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TrainingService trainingService;

    private Workout workout;

    @BeforeEach
    void setUp() {
        Exercise exercise = new Exercise("Supino reto", MuscularGroup.PECS, MuscularGroup.TRICEPS);
        Set set1 = new Set(1, 10, 50);
        ExerciseSession session = new ExerciseSession(exercise, List.of(set1));
        workout = new Workout("Treino Peito e Tríceps", List.of(session));
    }

    @Test
    void testAddWorkoutSuccess() throws Exception {
        Mockito.doNothing().when(trainingService).addWorkout(eq(1L), any(Workout.class));

        mockMvc.perform(put("/training/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workout)))
                .andExpect(status().isNoContent()); // agora é 204
    }

    @Test
    void testAddWorkoutUserNotFound() throws Exception {
        Mockito.doThrow(new NotFoundException("User not found. ID 99"))
                .when(trainingService).addWorkout(eq(99L), any(Workout.class));

        mockMvc.perform(put("/training/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workout)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("User not found. ID 99"))
                .andExpect(jsonPath("$.path").value("/training/99"));
    }
}
