package com.paiva.eurotreino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a set (series) of an exercise, including reps and weight.
 * Each set has a specific order, number of repetitions, and weight used.
 * It belongs to an ExerciseSession.
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Set {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private int order;
	private int reps;
	private double weight;
	
	@ManyToOne
	@JoinColumn(name = "exercise_session_id")
	private ExerciseSession exerciseSession;
	
	   /**
     * Constructs a Set with all required fields.
     *
     * @param order the set order in the exercise session
     * @param reps the number of repetitions
     * @param weight the weight used in the set
     * @param exerciseSession the parent ExerciseSession
     */
    public Set(int order, int reps, double weight) {
        this.order = order;
        this.reps = reps;
        this.weight = weight;
    }
	
    /**
     * Calculates the volume of this set (reps * weight).
     *
     * @return the total volume of this set
     */
    public double getVolume() {
    	return reps * weight;
    }
}
