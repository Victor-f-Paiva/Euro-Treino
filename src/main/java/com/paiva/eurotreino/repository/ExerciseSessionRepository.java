package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.entity.Exercise;
import com.paiva.eurotreino.entity.ExerciseSession;
import com.paiva.eurotreino.entity.Set;

/**
 * Repository interface for {@link ExerciseSessionRepository} entities.
 * <p>
 * Handles database operations for exercise session records,
 * which store information about performed {@link Exercise}s,
 * , and their corresponding {@link Set}s.
 */
public interface ExerciseSessionRepository extends JpaRepository<ExerciseSession, Long>{

}
