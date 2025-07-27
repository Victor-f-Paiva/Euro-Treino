package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.entity.Micro;

/**
 * Repository interface for {@link Micro} entities.
 * <p>
 * Provides basic CRUD operations and database interaction
 * for microcycles within the periodized training model.
 */
public interface MicroRepository extends JpaRepository<Micro, Long>{

}
