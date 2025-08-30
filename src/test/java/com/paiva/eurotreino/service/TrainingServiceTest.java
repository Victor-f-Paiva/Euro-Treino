package com.paiva.eurotreino.service;

import com.paiva.eurotreino.model.*;
import com.paiva.eurotreino.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainingService trainingService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // User inicial SEM ciclos
        user = new User("Victor Paiva", "victor@email.com", new ArrayList<>());
        user.setId(1L);
    }

    @Test
    void testGetOrCreateCurrentMacro_WhenEmpty_ShouldCreateNewMacro() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Macro macro = trainingService.getOrCreateCurrentMacro(1L);

        assertThat(user.getMacroCycles()).hasSize(1);
        assertThat(macro.getInitialDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void testGetOrCreateCurrentMacro_WhenExists_ShouldReturnLast() {
        Macro macro1 = new Macro(LocalDate.now().minusDays(10), new ArrayList<>());
        Macro macro2 = new Macro(LocalDate.now(), new ArrayList<>());
        user.setMacroCycles(new ArrayList<>(List.of(macro1, macro2)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Macro result = trainingService.getOrCreateCurrentMacro(1L);

        assertThat(result).isEqualTo(macro2);
    }

    @Test
    void testGetOrCreateCurrentMeso_WhenEmpty_ShouldCreateNewMeso() {
        Macro macro = new Macro(LocalDate.now(), new ArrayList<>());

        Meso meso = trainingService.getOrCreateCurrentMeso(macro);

        assertThat(macro.getMesoCycles()).hasSize(1);
        assertThat(meso.getInitialDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void testGetOrCreateCurrentMeso_WhenExists_ShouldReturnLast() {
        Meso meso1 = new Meso(LocalDate.now().minusDays(5), new ArrayList<>());
        Meso meso2 = new Meso(LocalDate.now(), new ArrayList<>());
        Macro macro = new Macro(LocalDate.now(), new ArrayList<>(List.of(meso1, meso2)));

        Meso result = trainingService.getOrCreateCurrentMeso(macro);

        assertThat(result).isEqualTo(meso2);
    }

    @Test
    void testGetOrCreateCurrentMicro_WhenEmpty_ShouldCreateNewMicro() {
        Meso meso = new Meso(LocalDate.now(), new ArrayList<>());

        Micro micro = trainingService.getOrCreateCurrentMicro(meso);

        assertThat(meso.getMicroCycles()).hasSize(1);
        assertThat(micro.getInitialDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void testGetOrCreateCurrentMicro_WhenExists_ShouldReturnLast() {
        Micro micro1 = new Micro(LocalDate.now().minusDays(2), new ArrayList<>());
        Micro micro2 = new Micro(LocalDate.now(), new ArrayList<>());
        Meso meso = new Meso(LocalDate.now(), new ArrayList<>(List.of(micro1, micro2)));

        Micro result = trainingService.getOrCreateCurrentMicro(meso);

        assertThat(result).isEqualTo(micro2);
    }

    @Test
    void testAddWorkout_ShouldCreateCyclesAndAddWorkout() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Workout workout = new Workout("Treino A", new ArrayList<>());

        trainingService.addWorkout(1L, workout);

        assertThat(user.getMacroCycles()).hasSize(1);
        Macro macro = user.getMacroCycles().get(0);
        assertThat(macro.getMesoCycles()).hasSize(1);
        Meso meso = macro.getMesoCycles().get(0);
        assertThat(meso.getMicroCycles()).hasSize(1);
        Micro micro = meso.getMicroCycles().get(0);
        assertThat(micro.getWorkouts()).contains(workout);
    }
}
