package com.paiva.eurotreino.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.paiva.eurotreino.enums.MuscularGroup;
import com.paiva.eurotreino.model.Meso;
import com.paiva.eurotreino.model.Micro;

class MesoTest2 {
	
    // internal class to simulate a Micro
    class MicroFake extends Micro {
        private Long id;
        private double volume;

        public MicroFake(Long id, double volume) {
            this.id = id;
            this.volume = volume;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public double getGroupVolume(MuscularGroup group) {
            return volume;
        }
    }

    @Test
    public void testGetHistoricGroupVolume() {
        // Arrange - prepare data
        MicroFake micro1 = new MicroFake(1L, 120.0);
        MicroFake micro2 = new MicroFake(2L, 150.0);
        List<Micro> microCycles = Arrays.asList(micro1, micro2);
        Meso meso = new Meso(LocalDate.now(), microCycles);

        // Act - run method
        Map<Long, Double> result = meso.getHistoricGroupVolume(MuscularGroup.BACK);

        // Assert - check method
        assertEquals(2, result.size());
        assertEquals(120.0, result.get(1L));
        assertEquals(150.0, result.get(2L));
    }
}
