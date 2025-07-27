package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.entity.Macro;

/**
 * Repository interface for {@link Macro} entities.
 * <p>
 * Handles persistence and data access for macrocycles,
 * the highest-level structure in periodized training programs.
 */
public interface MacroRepository extends JpaRepository<Macro, Long>{

}
