package com.paiva.eurotreino.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a training cycle with a start date.
 * This is an abstract base class for Macro, Meso and Micro cycles.
 */
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@Getter @Setter
public class Cycle {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private LocalDate initialDate;


    /**
     * Default constructor required by JPA.
     */
	public Cycle() {}

    /**
     * Constructs a training cycle with the specified start and end dates.
     * 
     * @param startDate the start date of the cycle
     */
	public Cycle(LocalDate initialDate) {
		this.initialDate = initialDate;
	}
	
}
