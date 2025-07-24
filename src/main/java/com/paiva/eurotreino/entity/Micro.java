package com.paiva.eurotreino.entity;

import java.time.LocalDate;
import java.util.List;

import com.paiva.eurotreino.enums.MuscularGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a microcycle in a periodized training plan.
 * 
 * A Micro is a short training cycle, typically corresponding to a week,
 * and contains a list of {@link Workout}, each one usually representing a training day.
 * 
 * This entity extends {@link Cycle}, which provides the initial date and common cycle behavior.
 * 
 * It also includes a method to calculate the total training volume for a specific {@link MuscularGroup}.
 * 
 * <p><strong>Example:</strong>  
 * A microcycle might include 5 workouts:
 * <ul>
 *   <li>Day 1: Chest and Triceps</li>
 *   <li>Day 2: Back and Biceps</li>
 *   <li>...</li>
 * </ul>
 * </p>
 */
@Entity
@Getter @Setter
@NoArgsConstructor
public class Micro extends Cycle{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
    /**
     * List of workouts scheduled within this microcycle.
     * Each workout represents a training session on a given day.
     * 
     * This is a many-to-one relationship, meaning that multiple workouts
     * are associated with one microcycle.
     * 
     * Note: The cascade option ensures workouts are persisted/deleted with this micro.
     */
	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "micro_id")
	private List<Workout> workouts;
	
	@ManyToOne
	@JoinColumn(name = "meso_id")
	private Meso meso;
	
    /**
     * Constructs a Micro with an initial date and a list of workouts.
     * 
     * @param initialDate the start date of the microcycle
     * @param workouts the list of workouts included in this microcycle
     */
	public Micro(LocalDate initialDate, List<Workout> workouts) {
		super(initialDate);
		this.workouts = workouts;
	}
	
	/**
     * Calculates the total training volume for a specific muscular group
     * across all workouts and exercises in this microcycle.
     * 
     * The volume is the sum of all (reps × weight) from sets of exercises
     * where the given muscular group is either primary or secondary.
     * 
     * @param group the targeted muscular group
     * @return the total volume for the specified muscular group
     */
	public double getGroupVolume(MuscularGroup group) {
		return workouts.stream()
				.flatMap(w -> w.getExercises().stream())
				.filter(ex -> ex.getExercise().getPrimaryGroup().equals(group)
						|| ex.getExercise().getSecondaryGroup().equals(group))
				.mapToDouble(n -> n.getTotalVolume())
				.sum();
	}

}
