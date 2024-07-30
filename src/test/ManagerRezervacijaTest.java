package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Cenovnik;
import entity.DodatnaUsluga;
import entity.Rezervacija;
import entity.TipSobe;
import managerKlase.ManagerCenovnik;
import managerKlase.ManagerRezervacija;

public class ManagerRezervacijaTest {
	ManagerRezervacija mr;
	TipSobe tipSobe1;
	@Before
	public void setUp() throws Exception {
		ManagerCenovnik mc = new ManagerCenovnik();
		Cenovnik c = new Cenovnik(3, LocalDate.of(2020,01,01));
		tipSobe1 = new TipSobe(2, "1+1");
		mc.kreirajCenovnik(tipSobe1, c);
		assertTrue(mc.getCenovnici().containsKey(tipSobe1));
		
		DodatnaUsluga dodatnaUsluga1 = new DodatnaUsluga(1, "Dorucak");
		DodatnaUsluga dodatnaUsluga2 = new DodatnaUsluga(2, "Rucak");
		DodatnaUsluga dodatnaUsluga3 = new DodatnaUsluga(3, "Vecera");
		
		mc.dodajStavku(1, dodatnaUsluga1, 3, 1500);
		mc.dodajStavku(2, dodatnaUsluga2, 3, 1050);
		mc.dodajStavku(3, dodatnaUsluga3, 3, 800);
		mr = new ManagerRezervacija(mc);
		System.out.println("Testiranje klase ManagerRezervacija");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Testiranje klase ManagerRezervacija kraj\n");
	}

	@Test
	public void testDodajRezervaciju() {
		mr.dodajRezervaciju(tipSobe1, LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 10));
		assertTrue(mr.getRezervacije().size() == 1);
		Rezervacija r = mr.getRezervacijaById(1);
		assertNotNull(r);
		r = mr.getRezervacijaById(2);
		assertNull(r);
		ArrayList<DodatnaUsluga> usluge = new ArrayList<DodatnaUsluga>();
		usluge.add(new DodatnaUsluga(1, "Dorucak"));
		usluge.add(new DodatnaUsluga(2, "Rucak"));
		mr.dodajRezervaciju(3, tipSobe1, LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 10), usluge);
		assertTrue(mr.getRezervacije().size() == 2);
		r = mr.getRezervacijaById(3);
		assertEquals(r.getUsluge().size(), 2);
	}
}
