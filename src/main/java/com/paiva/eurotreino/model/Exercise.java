package com.paiva.eurotreino.model;

import com.paiva.eurotreino.enums.MuscularGroup;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a physical exercise, including its name, primary and secondary muscle groups involved.
 */
@Entity
@Getter @Setter
public class Exercise {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Enumerated(EnumType.STRING)
	private MuscularGroup primaryGroup;
	
	@Enumerated(EnumType.STRING)
	private MuscularGroup secondaryGroup;
	
	/**
	 * Default constructor required by JPA.
	 */
	public Exercise() {}

	/**
	 * Constructs an exercise with the specified name, primary and secondary muscle group,
	 * 
	 * @param name the name of the exercise
	 * @param primaryGroup the primary muscle group targeted by the exercise
	 * @param secondaryGroup the secondary muscle group involved in the exercise
	 */
	public Exercise(String name, MuscularGroup primaryGroup, MuscularGroup secondaryGroup) {
		this.name = name;
		this.primaryGroup = primaryGroup;
		this.secondaryGroup = secondaryGroup;
	}

}
