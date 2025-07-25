package com.paiva.eurotreino.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.paiva.eurotreino.enums.MuscularGroup;

class UserTest {

	@Test
	public void testConstructorandGetters() {
		Exercise supinoReto = new Exercise("Supino reto", MuscularGroup.PECS, MuscularGroup.TRICEPS);
		Set set1SR = new Set(1, 10, 50); 
		Set set2SR = new Set(1, 10, 50); 
		Set set3SR = new Set(1, 10, 50); 
		
		Exercise supinoInclinado = new Exercise("Supino inclinado", MuscularGroup.PECS, MuscularGroup.TRICEPS);
		Set set1SI = new Set(1, 10, 40); 
		Set set2SI = new Set(1, 10, 40); 
		Set set3SI = new Set(1, 10, 40); 
		
		ExerciseSession supinoRetoW = new ExerciseSession(supinoReto, List.of(set1SR, set2SR, set3SR));
		ExerciseSession supinoInclinadoW = new ExerciseSession(supinoInclinado, List.of(set1SI, set2SI, set3SI));
		
		Workout treinoA = new Workout("Peito e triceps", List.of(supinoRetoW, supinoInclinadoW));
		
		Micro micro1 = new Micro(LocalDate.now(), List.of(treinoA));
		
		Meso meso1 = new Meso(LocalDate.now(), List.of(micro1));
		
		Macro macro1 = new Macro(LocalDate.now(), List.of(meso1));
		
		User user1 = new User("Victor Paiva", "victor@email.com", List.of(macro1));
		
		assertEquals("Victor Paiva", user1.getName()); //user name
		assertEquals(LocalDate.now(), user1.getMacroCycles().get(0).getInitialDate()); //macrocycle initial date
		assertEquals(2700, micro1.getGroupVolume(MuscularGroup.PECS)); // group volume should be 2700
	}

}
