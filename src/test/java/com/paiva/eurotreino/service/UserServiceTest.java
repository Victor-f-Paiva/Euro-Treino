package com.paiva.eurotreino.service;

import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.exception.NotFoundException;
import com.paiva.eurotreino.model.*;
import com.paiva.eurotreino.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository; // mockado

    @InjectMocks
    private UserService userService; // service real, mas com repo falso injetado

    private User user1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Montando os objetos como no seu teste de repositório
        Exercise supinoReto = new Exercise("Supino reto", MuscularGroup.PECS, MuscularGroup.TRICEPS);
        Set set1SR = new Set(1, 10, 50);
        Set set2SR = new Set(1, 10, 50);
        Set set3SR = new Set(1, 10, 50);

        Exercise supinoInclinado = new Exercise("Supino inclinado", MuscularGroup.PECS, MuscularGroup.TRICEPS);
        Set set1SI = new Set(1, 10, 40);
        Set set2SI = new Set(1, 10, 40);
        Set set3SI = new Set(1, 10, 40);

        ExerciseSession supinoRetoW = new ExerciseSession(supinoReto, List.of(set1SR, set2SR, set3SR));
        ExerciseSession supinoInclinadoW = new ExerciseSession(supinoInclinado, List.of(set1SI, set2SI, set3SI));

        Workout treinoA = new Workout("Peito e triceps", List.of(supinoRetoW, supinoInclinadoW));

        Micro micro1 = new Micro(LocalDate.now(), List.of(treinoA));
        Meso meso1 = new Meso(LocalDate.now(), List.of(micro1));
        Macro macro1 = new Macro(LocalDate.now(), List.of(meso1));

        user1 = new User("Victor Paiva", "victor@email.com", List.of(macro1));
        user1.setId(1L); // simulando ID gerado
    }

    @Test
    void testFindByIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        User found = userService.findById(1L);

        assertThat(found.getName()).isEqualTo("Victor Paiva");
        assertThat(found.getMacroCycles().get(0).getMesoCycles().get(0).getMicroCycles().get(0)
                .getGroupVolume(MuscularGroup.PECS)).isEqualTo(2700);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(99L));

        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user1)).thenReturn(user1);

        User saved = userService.creatUser(user1);

        assertThat(saved.getEmail()).isEqualTo("victor@email.com");
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testUpdateUser() {
        User updatedData = new User("Novo Nome", "novo@email.com", List.of());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User updated = userService.updatUser(1L, updatedData);

        assertThat(updated.getName()).isEqualTo("Novo Nome");
        assertThat(updated.getEmail()).isEqualTo("novo@email.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user1);
    }
}
