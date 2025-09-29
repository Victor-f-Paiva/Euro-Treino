package com.paiva.eurotreino.model;

import java.time.LocalDate;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a macrocycle in a periodized training plan.
 * 
 * A Macro contains a list of {@link Meso} cycles (mesocycles),
 * which themselves contain {@link Micro} cycles and {@link Workout}s.
 * 
 * A macrocycle typically represents a long-term training period,
 * such as 6 to 12 months, and helps structure the training strategy.
 */
@Entity
@Getter @Setter
@NoArgsConstructor
public class Macro extends Cycle{ 
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
    /**
     * List of mesocycles that belong to this macrocycle.
     * 
     * This is a bidirectional one-to-many relationship.
     * Each Meso has a reference back to its Macro via the 'macro' field.
     */
	@OneToMany(mappedBy = "macro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Meso> mesoCycles;
	
	@ManyToOne 
    @JoinColumn(name = "user_id")
    @JsonIgnore
	private User user;
	
    /**
     * Constructs a Macrocycle with an initial date and a list of mesocycles.
     *
     * @param initialDate the start date of the macrocycle
     * @param mesoCycles the list of mesocycles in this macrocycle
     */
	public Macro(LocalDate initialDate, List<Meso> mesoCycles) {
		super(initialDate);
		this.mesoCycles = mesoCycles;
	}

    public void addMeso(Meso meso){
        this.mesoCycles.add(meso);
        meso.setMacro(this);
    }
}
