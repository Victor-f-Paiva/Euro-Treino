package com.paiva.eurotreino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paiva.eurotreino.model.User;

/**
 * Repository interface for {@link User} entities.
 * <p>
 * Provides CRUD operations for application users
 */
public interface UserRepository extends JpaRepository<User, Long>{

}
