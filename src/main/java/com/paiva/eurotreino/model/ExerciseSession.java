package com.paiva.eurotreino.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an exercise performed in a workout session,
 * containing the sets that make up this exercise.
 */
@Entity
@Getter @Setter
@NoArgsConstructor
public class ExerciseSession {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	  /**
     * The exercise being performed in this session (e.g., Bench Press).
     */
	@ManyToOne
	private Exercise exercise;
	
 	/**
     * The list of sets associated with this exercise session.
     * 
     * This is a one-to-many relationship. When a ExerciseSession is saved or deleted,
     * its Sets are also persisted or removed accordingly.
     */
	@OneToMany(mappedBy = "exerciseSession", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Set> sets;
	
	/**
	 * Constructs a ExerciseSession with all required fields.
	 * 
	 * @param exercise the exercise to be executed
	 * @param sets the sets that make up the session
	 */
	public ExerciseSession(Exercise exercise, List<Set> sets) {
		this.exercise = exercise;
		this.sets = sets;
	}
	
	  /**
     * Calculates the total volume of the session by summing all set volumes
     * (reps × weight for each set).
     */
	public double getTotalVolume() {
		return sets.stream().mapToDouble(n -> n.getVolume()).sum();
	}
	
}
