package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.model.Workout;

/**
 * Repository interface for {@link Workout} entities.
 * <p>
 * Handles data access and persistence for training sessions
 * associated with microcycles and specific muscle groups.
 */
public interface WorkoutRepository extends JpaRepository<Workout, Long>{

}
