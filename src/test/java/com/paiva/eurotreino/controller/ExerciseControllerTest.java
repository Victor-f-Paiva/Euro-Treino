package com.paiva.eurotreino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.model.Exercise;
import com.paiva.eurotreino.service.ExerciseService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExerciseController.class)
class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExerciseService exerciseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new Exercise("Supino Reto", MuscularGroup.PECS, MuscularGroup.TRICEPS);
        exercise.setId(1L);
    }

    @Test
    void testFindAll() throws Exception {
        Mockito.when(exerciseService.findAllExercises()).thenReturn(List.of(exercise));

        mockMvc.perform(get("/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(exercise.getId()))
                .andExpect(jsonPath("$[0].name").value("Supino Reto"));
    }

    @Test
    void testFindById() throws Exception {
        Mockito.when(exerciseService.findExerciseById(1L)).thenReturn(exercise);

        mockMvc.perform(get("/exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exercise.getId()))
                .andExpect(jsonPath("$.name").value("Supino Reto"));
    }

    @Test
    void testCreateExercise() throws Exception {
        Mockito.when(exerciseService.creatExercise(any(Exercise.class))).thenReturn(exercise);

        mockMvc.perform(post("/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exercise)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(exercise.getId()))
                .andExpect(jsonPath("$.name").value("Supino Reto"));
    }

    @Test
    void testUpdateExercise() throws Exception {
        Exercise updated = new Exercise("Supino Inclinado", MuscularGroup.PECS, MuscularGroup.TRICEPS);
        updated.setId(1L);

        Mockito.when(exerciseService.updateExercise(eq(1L), any(Exercise.class))).thenReturn(updated);

        mockMvc.perform(put("/exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Supino Inclinado"));
    }

    @Test
    void testDeleteExercise() throws Exception {
        Mockito.doNothing().when(exerciseService).deleteExercise(1L);

        mockMvc.perform(delete("/exercises/1"))
                .andExpect(status().isNoContent());
    }
}
