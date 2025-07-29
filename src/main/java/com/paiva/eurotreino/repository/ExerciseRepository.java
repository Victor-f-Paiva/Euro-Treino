package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.model.Exercise;

/**
 * Repository interface for {@link Exercise} entities.
 * <p>
 * Provides CRUD operations and access to exercise definitions,
 * including name, prime target muscles, and second target muscle.
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{

}
