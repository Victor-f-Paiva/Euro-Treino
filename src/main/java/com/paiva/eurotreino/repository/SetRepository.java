package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link com.paiva.eurotreino.entity.Set} entities.
 * <p>
 * Supports operations related to workout sets, such as
 * order, repetitions, and weight used to calculates training volume.
 */
public interface SetRepository extends JpaRepository<com.paiva.eurotreino.entity.Set, Long>{

}
