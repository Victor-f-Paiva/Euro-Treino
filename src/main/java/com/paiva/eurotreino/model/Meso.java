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
 * Represents a mesocycle, which is a training cycle containing several microcycles.
 * Responsible for aggregating group volume history by muscular group.
 */
@Entity
@Getter @Setter
@NoArgsConstructor
public class Meso extends Cycle{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	

    /**
     * List of microcycles that belong to this mesocycle.
     */
	@OneToMany(mappedBy = "meso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Micro> microCycles;
	
	@ManyToOne 
	@JoinColumn(name= "macro_id")
	@JsonIgnore
	private Macro macro;

    /**
     * Constructs a Meso with an initial date and a list of microCycles.
     * 
     * @param initialDate the start date of the mesocycle
     * @param microCycles the list of microCycles included in this mesocycle
     */
	public Meso(LocalDate initialDate, List<Micro> microCycles) {
		super(initialDate);
		this.microCycles = microCycles;
	}	

	public void addMicro(Micro micro){
		this.microCycles.add(micro);
		micro.setMeso(this);
	}

}
