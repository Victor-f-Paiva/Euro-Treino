package com.paiva.eurotreino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.exception.NotFoundException;
import com.paiva.eurotreino.model.*;
import com.paiva.eurotreino.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // carrega apenas o controller
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // usado para converter objetos em JSON

    @MockitoBean
    private UserService userService; // mockado, diferente do teste de service

    private User user1;
    private Workout treinoA;
    private Workout treinoB;

    @BeforeEach
    void setUp() {
        Exercise supinoReto = new Exercise("Supino reto", MuscularGroup.PECS, MuscularGroup.TRICEPS);
        Set set1SR = new Set(1, 10, 50);
        ExerciseSession supinoRetoW = new ExerciseSession(supinoReto, List.of(set1SR));
        treinoA = new Workout("Peito e triceps", new ArrayList<>(List.of(supinoRetoW)));

        Exercise remadaAberta = new Exercise("Remada aberta", MuscularGroup.BACK, MuscularGroup.BICEPS);
        Set set1RA = new Set(1, 12, 40);
        ExerciseSession remadaAbertaW = new ExerciseSession(remadaAberta, List.of(set1RA));
        treinoB = new Workout("Dorsal e biceps", new ArrayList<>(List.of(remadaAbertaW)));

        Micro micro1 = new Micro(LocalDate.now(), List.of(treinoA, treinoB));
        Meso meso1 = new Meso(LocalDate.now(), List.of(micro1));
        Macro macro1 = new Macro(LocalDate.now(), List.of(meso1));

        user1 = new User("Victor Paiva", "victor@email.com", List.of(macro1));
        user1.setId(1L);
    }

    @Test
    void testFindAll() throws Exception {
        when(userService.findAll()).thenReturn(List.of(user1));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Victor Paiva"))
                .andExpect(jsonPath("$[0].email").value("victor@email.com"));
    }

    @Test
    void testFindByIdSuccess() throws Exception {
        when(userService.findById(1L)).thenReturn(user1);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Victor Paiva"))
                .andExpect(jsonPath("$.email").value("victor@email.com"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(userService.findById(99L)).thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.creatUser(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("Victor Paiva"))
                .andExpect(jsonPath("$.email").value("victor@email.com"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User updated = new User("Novo Nome", "novo@email.com", List.of());
        updated.setId(1L);

        when(userService.updatUser(eq(1L), any(User.class))).thenReturn(updated);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Novo Nome"))
                .andExpect(jsonPath("$.email").value("novo@email.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testVisualizeWorkoutSuccess() throws Exception {
        when(userService.visualizeWorkout(1L)).thenReturn(treinoA);

        System.out.println("Teste: " + userService.visualizeWorkout(1L));

        mockMvc.perform(get("/users/workout/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Peito e triceps"))
                .andExpect(jsonPath("$.exercises[0].exercise.name").value("Supino reto"))
                .andExpect(jsonPath("$.exercises[0].sets[0].reps").value(10))
                .andExpect(jsonPath("$.exercises[0].sets[0].weight").value(50.0));
    }

    @Test
    void testVisualizeWorkoutNotFound() throws Exception {
        when(userService.visualizeWorkout(99L)).thenThrow(new NotFoundException("User 99 has no workout found."));

        mockMvc.perform(get("/users/workout/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetWorkoutVolume() throws Exception {
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.getWorkoutVolume(treinoA)).thenReturn(500.0);
        when(userService.getWorkoutVolume(treinoB)).thenReturn(480.0);

        mockMvc.perform(get("/users/getVolume/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Peito e triceps']").value(500.0))
                .andExpect(jsonPath("$.['Dorsal e biceps']").value(480.0));
    }
}
