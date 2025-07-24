package com.paiva.eurotreino.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paiva.eurotreino.enums.MuscularGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    /**
     * Returns the historic volume of training for a specific muscular group across all microcycles.
     * @param group The muscular group.
     * @return A map of micro IDs and their corresponding volume.
     */
	public Map<Long, Double> getHistoricGroupVolume(MuscularGroup group) {
		Map<Long, Double> historic = new HashMap<Long, Double>();
		for (int i=0; i<microCycles.size(); i++) {
			Micro current = microCycles.get(i);
			historic.put(current.getId(), current.getGroupVolume(group));
		}
		return historic;
	}
	

}
