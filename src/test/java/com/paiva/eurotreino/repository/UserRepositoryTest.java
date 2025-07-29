package com.paiva.eurotreino.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.model.Exercise;
import com.paiva.eurotreino.model.ExerciseSession;
import com.paiva.eurotreino.model.Macro;
import com.paiva.eurotreino.model.Meso;
import com.paiva.eurotreino.model.Micro;
import com.paiva.eurotreino.model.Set;
import com.paiva.eurotreino.model.User;
import com.paiva.eurotreino.model.Workout;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testPersistComplete() {
		
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
		
		User saved = userRepository.save(user1);
		
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getMacroCycles()).hasSize(1);
		assertThat(saved.getMacroCycles().get(0).getMesoCycles()).hasSize(1);
		assertThat(saved.getMacroCycles().get(0).getMesoCycles().get(0).getMicroCycles()).hasSize(1);
		assertEquals(2700, saved.getMacroCycles().get(0)
				.getMesoCycles().get(0)
				.getMicroCycles().get(0)
				.getGroupVolume(MuscularGroup.PECS));
		
	}

}
