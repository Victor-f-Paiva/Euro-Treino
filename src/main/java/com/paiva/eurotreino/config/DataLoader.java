package com.paiva.eurotreino.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.model.Exercise;
import com.paiva.eurotreino.repository.ExerciseRepository;

/**
 * Loads initial data into the database when the application starts.
 * <p>
 * Ensures that a predefined list of exercises is available for creating workouts.
 * This avoids the need to manually insert initial exercises into the database.
 */
@Configuration
public class DataLoader {

    /**
     * Initializes the database with a default set of exercises.
     * Runs only if the table is empty, preventing duplicate entries.
     *
     * @param exerciseRepo the repository used to persist exercises
     * @return a CommandLineRunner bean executed at application startup
     */

    @Bean
    CommandLineRunner initDataExercises(ExerciseRepository exerciseRepo){
        return args -> {
            if (exerciseRepo.count() == 0) {
                exerciseRepo.saveAll(List.of(
                    // Peito
                    new Exercise("Supino Reto", MuscularGroup.PECS, MuscularGroup.TRICEPS),
                    new Exercise("Supino Inclinado", MuscularGroup.PECS, MuscularGroup.TRICEPS),
                    new Exercise("Crucifixo", MuscularGroup.PECS, MuscularGroup.SHOULDER),
                    
                    // Costas
                    new Exercise("Remada Aberta", MuscularGroup.BACK, MuscularGroup.BICEPS),
                    new Exercise("Remada Curvada", MuscularGroup.BACK, MuscularGroup.BICEPS),
                    new Exercise("Puxada Frontal", MuscularGroup.BACK, MuscularGroup.BICEPS),
                    
                    // Bíceps
                    new Exercise("Rosca Direta", MuscularGroup.BICEPS, MuscularGroup.FOREARM),
                    new Exercise("Rosca Martelo", MuscularGroup.BICEPS, MuscularGroup.FOREARM),
                    new Exercise("Rosca Scott", MuscularGroup.BICEPS, MuscularGroup.FOREARM),
                    
                    // Tríceps
                    new Exercise("Tríceps Corda", MuscularGroup.TRICEPS, MuscularGroup.SHOULDER),
                    new Exercise("Tríceps Testa", MuscularGroup.TRICEPS, MuscularGroup.SHOULDER),
                    new Exercise("Mergulho em Paralelas", MuscularGroup.TRICEPS, MuscularGroup.PECS),
                    
                    // Ombros
                    new Exercise("Desenvolvimento Militar", MuscularGroup.SHOULDER, MuscularGroup.TRICEPS),
                    new Exercise("Elevação Lateral", MuscularGroup.SHOULDER, MuscularGroup.FOREARM),
                    new Exercise("Elevação Frontal", MuscularGroup.SHOULDER, MuscularGroup.FOREARM),
                    
                    // Pernas
                    new Exercise("Agachamento Livre", MuscularGroup.QUADS, MuscularGroup.GLUTE),
                    new Exercise("Leg Press", MuscularGroup.QUADS, MuscularGroup.GLUTE),
                    new Exercise("Avanço", MuscularGroup.QUADS, MuscularGroup.GLUTE),
                    
                    // Posterior / Glúteo
                    new Exercise("Levantamento Terra", MuscularGroup.HAMS, MuscularGroup.BACK),
                    new Exercise("Mesa Flexora", MuscularGroup.HAMS, MuscularGroup.GLUTE),
                    new Exercise("Stiff", MuscularGroup.HAMS, MuscularGroup.GLUTE)
                ));
                System.out.println("✅ Initial exercises loaded!");
            } else {
                System.out.println("ℹ️ The DataBase already contains exercises.");
            }
        };
    }
}
