package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Cenovnik;
import entity.DodatnaUsluga;
import entity.TipSobe;
import managerKlase.ManagerCenovnik;

public class ManagerCenovnikTest {
	ManagerCenovnik mc = new ManagerCenovnik();
	@Before
	public void setUp() throws Exception {
		mc = new ManagerCenovnik();
		System.out.println("Testiranje klase ManagerCenovnik");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Testiranje klase ManagerCenovnik je zavrseno\n");
	}
	
	@Test
	public void testKreiranjeCenovnika() {
		Cenovnik c = new Cenovnik(3, LocalDate.of(2020,01,01));
		TipSobe tipSobe = new TipSobe(2, "1+1");
		mc.kreirajCenovnik(tipSobe, c);
		assertTrue(mc.getCenovnici().containsKey(tipSobe));
		
		DodatnaUsluga dodatnaUsluga = new DodatnaUsluga(1, "Dorucak");
		mc.dodajStavku(1, dodatnaUsluga, 3, 1500);
		
		assertTrue(mc.getStavkeByCenovnik(3).size() == 1);
		
		assertTrue(mc.dodatnaUslugaNaCenovniku(1, 3));
		assertFalse(mc.dodatnaUslugaNaCenovniku(1, 1));
	}
	@Test
	public void testUkloniStavku() {
		Cenovnik c = new Cenovnik(3, LocalDate.of(2020,01,01));
		TipSobe tipSobe = new TipSobe(2, "1+1");
		mc.kreirajCenovnik(tipSobe, c);
		assertTrue(mc.getCenovnici().containsKey(tipSobe));
		
		DodatnaUsluga dodatnaUsluga1 = new DodatnaUsluga(1, "Dorucak");
		DodatnaUsluga dodatnaUsluga2 = new DodatnaUsluga(2, "Rucak");
		DodatnaUsluga dodatnaUsluga3 = new DodatnaUsluga(3, "Vecera");
		
		mc.dodajStavku(1, dodatnaUsluga1, 3, 1500);
		mc.dodajStavku(2, dodatnaUsluga2, 3, 1050);
		mc.dodajStavku(3, dodatnaUsluga3, 3, 800);
		
		mc.ukloniStavku(1);
		assertTrue(mc.getStavkeByCenovnik(3).size() == 2);
		mc.ukloniStavku(1);
		assertTrue(mc.getStavkeByCenovnik(3).size() == 2);
	}
	@Test
	public void setCenaTest() {
		Cenovnik c = new Cenovnik(3, LocalDate.of(2020,01,01));
		TipSobe tipSobe = new TipSobe(2, "1+1");
		mc.kreirajCenovnik(tipSobe, c);
		assertTrue(mc.getCenovnici().containsKey(tipSobe));
		
		DodatnaUsluga dodatnaUsluga1 = new DodatnaUsluga(1, "Dorucak");
		DodatnaUsluga dodatnaUsluga2 = new DodatnaUsluga(2, "Rucak");
		DodatnaUsluga dodatnaUsluga3 = new DodatnaUsluga(3, "Vecera");
		
		mc.dodajStavku(1, dodatnaUsluga1, 3, 1500);
		mc.dodajStavku(2, dodatnaUsluga2, 3, 1050);
		mc.dodajStavku(3, dodatnaUsluga3, 3, 800);
		
		mc.setCenaForUsluga(dodatnaUsluga3, 3, 999);
		assertEquals(999, (int)mc.getCena(3, 3));
	
		mc.setCenaForStavka(3, 3, 777);
		assertEquals(777, (int)mc.getCena(3, 3));
	}
	@Test
	public void removeTest() {
		Cenovnik c = new Cenovnik(3, LocalDate.of(2020,01,01));
		TipSobe tipSobe = new TipSobe(2, "1+1");
		mc.kreirajCenovnik(tipSobe, c);
		assertTrue(mc.getCenovnici().containsKey(tipSobe));
		
		DodatnaUsluga dodatnaUsluga1 = new DodatnaUsluga(1, "Dorucak");
		DodatnaUsluga dodatnaUsluga2 = new DodatnaUsluga(2, "Rucak");
		DodatnaUsluga dodatnaUsluga3 = new DodatnaUsluga(3, "Vecera");
		
		mc.dodajStavku(1, dodatnaUsluga1, 3, 1500);
		mc.dodajStavku(2, dodatnaUsluga2, 3, 1050);
		mc.dodajStavku(3, dodatnaUsluga3, 3, 800);
		
		mc.removeStavka(3, 3);
		assertTrue(mc.getStavkeByCenovnik(3).size() == 2);
		
		mc.ukloniCenovnik(3);
		assertEquals(mc.getCenovnikById(3), null);
	}
} 
