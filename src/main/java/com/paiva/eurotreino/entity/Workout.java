package com.paiva.eurotreino.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a workout session composed of multiple exercise sessions.
 * 
 * Each Workout can have a list of {@link ExerciseSession}, where each one contains sets (series),
 * repetitions, and weights used during training.
 * 
 * This entity also provides a method to calculate the total volume of the workout.
 * The volume is typically calculated as: sum of (reps × weight) for all sets.
 */
@Entity
@Getter @Setter
@NoArgsConstructor
public class Workout {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
    /**
     * Name of the workout. E.g. "Leg Day", "Push Workout", etc.
     */
	private String name;
	
	 /**
     * List of exercise sessions that make up this workout.
     * 
     * This is a one-to-many relationship. When a Workout is saved or deleted,
     * its ExerciseSessions are also persisted or removed accordingly.
     */
	@OneToMany
	private List<ExerciseSession> exercises;
	
	 /**
     * Constructs a Workout with with all required fields.
     *
     * @param name the name of the workout
     * @param exercises the list of exercise sessions included in this workout
     */
	public Workout(String name, List<ExerciseSession> exercises) {
		this.name = name;
		this.exercises = exercises;
	}

	/**
     * Calculates the total volume of all exercise sessions in this workout.
     * 
     * The volume is the sum of all (reps × weight) for all sets in each exercise session.
     *
     * @return the total workout volume
     */
	public double getTotalVolume() {
		return exercises.stream().mapToDouble(ExerciseSession :: getTotalVolume).sum();
	}
	
}
