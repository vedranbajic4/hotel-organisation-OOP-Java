package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Gost;
import enums.Pol;
import managerKlase.ManagerGost;

public class ManagerGostTest {
	ManagerGost mr;
	@Before
	public void setUp() throws Exception {
		mr = new ManagerGost();
		System.out.println("ManagerGostTest start");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("ManagerGostTest end\n");
	}

	@Test
	public void testDodajGosta2() {
		mr.dodajGosta(1, "Pera", "Peric");
		assertEquals(1, mr.getGosti().size());
		assertEquals("Pera", mr.getGostById(1).getIme());
		assertEquals("Peric", mr.getGostById(1).getPrezime());
	}
	@Test
	public void testDodajGosta3() {
        mr.dodajGosta(1, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "korime", "lozinka");
        assertEquals(1, mr.getGosti().size());
        assertEquals("Pera", mr.getGostById(1).getIme());
        assertEquals("Peric", mr.getGostById(1).getPrezime());
        assertEquals(Pol.MUSKI, mr.getGostById(1).getPol());
        assertEquals(LocalDate.now(), mr.getGostById(1).getDatumRodjenja());
        assertEquals("123456", mr.getGostById(1).getTelefon());
        assertEquals("adresa", mr.getGostById(1).getAdresa());
        assertEquals("korime", mr.getGostById(1).getKorisnickoIme());
        assertEquals("lozinka", mr.getGostById(1).getLozinka());                
	}
	@Test
	public void testGetGosti() {
		mr.dodajGosta(1, "Pera", "Peric");
		mr.dodajGosta(2, "Mika", "Mikic");
		mr.dodajGosta(3, "Zika", "Zikic");
		assertEquals(3, mr.getGosti().size());
	}
	@Test
	public void testPostojiKorisnik() {
		mr.dodajGosta(5, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		mr.dodajGosta(6, "Mika", "Mikic", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "mika", "lozinka");
		mr.dodajGosta(7, "Zika", "Zikic", Pol.ZENSKI, LocalDate.now(), "123456", "adresa", "zika", "lozinka");
		Gost r = mr.postojiKorisnik("korime", "lozinka");
		assertEquals(null, r);
		r = mr.postojiKorisnik("mika", "lozinka");
		assertEquals(6, r.getId());
	}
	@Test
	public void testgetGostByKorisnickoIme() {
		mr.dodajGosta(8, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		mr.dodajGosta(9, "Mika", "Mikic", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "mika", "lozinka");
		mr.dodajGosta(10, "Zika", "Zikic", Pol.ZENSKI, LocalDate.now(), "123456", "adresa", "zika", "lozinka");
		Gost r = mr.postojiKorisnik("zika");
		assertEquals(10, r.getId());
		r = mr.postojiKorisnik("korime");
		assertEquals(null, r);
	}
}
