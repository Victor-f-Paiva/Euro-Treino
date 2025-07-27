package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.entity.Meso;

/**
 * Repository interface for {@link Meso} entities.
 * <p>
 * Supports CRUD operations for mesocycles,
 * which represent medium-term training blocks within a macrocycle.
 */
public interface MesoRepository extends JpaRepository<Meso, Long>{

}
