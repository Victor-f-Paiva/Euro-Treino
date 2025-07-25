package com.paiva.eurotreino.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class MacroTest {

	@Test
	public void testConstructorAndGetter() {
		Meso meso1= new Meso();
		Meso meso2= new Meso();
		
		Macro macro = new Macro(LocalDate.now(), List.of(meso1, meso2));
		
		assertEquals(LocalDate.now(), macro.getInitialDate());
		assertEquals(2, macro.getMesoCycles().size());
	}

}
