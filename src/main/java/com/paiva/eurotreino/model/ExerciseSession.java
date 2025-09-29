package com.paiva.eurotreino.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ExerciseSession {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "workout_id")
	@JsonIgnore
	private Workout workout;
	
 	/**
     * The list of sets associated with this exercise session.
     * 
     * This is a one-to-many relationship. When a ExerciseSession is saved or deleted,
     * its Sets are also persisted or removed accordingly.
     */
	@OneToMany(mappedBy = "exerciseSession", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Set> sets;

	@OneToOne
	private Exercise exercise;
	
	public ExerciseSession (Exercise exercise, List<Set> sets){
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
