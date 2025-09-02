package com.paiva.eurotreino.config;

import com.paiva.eurotreino.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.*;

class DataLoaderTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldInsertExercisesWhenDatabaseIsEmpty() throws Exception {
        // given
        when(exerciseRepository.count()).thenReturn(0L);

        // when
        dataLoader.initDataExercises(exerciseRepository).run();

        // then
        verify(exerciseRepository, times(1)).saveAll(anyList());
    }

    @Test
    void shouldNotInsertExercisesWhenDatabaseIsNotEmpty() throws Exception {
        // given
        when(exerciseRepository.count()).thenReturn(5L);

        // when
        dataLoader.initDataExercises(exerciseRepository).run();

        // then
        verify(exerciseRepository, never()).saveAll(anyList());
    }
}
