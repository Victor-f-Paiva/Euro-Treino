package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.model.Cycle;

/**
 * Repository interface for {@link Cycle} entities.
 * <p>
 * Provides access to all training cycles, including macrocycles,
 * mesocycles, and microcycles, as they inherit from the abstract base class {@code Cycle}.
 */
public interface CycleRepository extends JpaRepository<Cycle, Long>{

}
