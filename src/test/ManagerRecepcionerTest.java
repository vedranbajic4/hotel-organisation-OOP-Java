package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Recepcioner;
import enums.Pol;
import enums.StrucnaSprema;
import managerKlase.ManagerRecepcioner;

public class ManagerRecepcionerTest {
	private ManagerRecepcioner mr;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		System.out.println("ManagerRecepcionerTest start");
		mr = new ManagerRecepcioner();
	}

	@After
	public void tearDownAfterClass() throws Exception {
		System.out.println("ManagerRecepcionerTest end");
	}
	/*@Test
	public void testDodajRecepcionera1() {
		mr.dodajRecepcionera("Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "korime", "lozinka", 50000, 5, StrucnaSprema.SEDMI_STEPEN);
		assertEquals(1, mr.getRecepcioneri().size());
		assertEquals("Pera", mr.getRecepcionerById(1).getIme());
		assertEquals("Peric", mr.getRecepcionerById(1).getPrezime());
		assertEquals(Pol.MUSKI, mr.getRecepcionerById(1).getPol());
		assertEquals(LocalDate.now(), mr.getRecepcionerById(1).getDatumRodjenja());
		assertEquals("123456", mr.getRecepcionerById(1).getTelefon());
		assertEquals("adresa", mr.getRecepcionerById(1).getAdresa());
		assertEquals("korime", mr.getRecepcionerById(1).getKorisnickoIme());
		assertEquals("lozinka", mr.getRecepcionerById(1).getLozinka());
		assertEquals(50000, mr.getRecepcionerById(1).getPlata());
		assertEquals(5, mr.getRecepcionerById(1).getStaz());
		assertEquals(StrucnaSprema.SEDMI_STEPEN, mr.getRecepcionerById(1).getStrucnaSprema());
	}*/
	@Test
	public void testDodajRecepcionera2() {
		mr.dodajRecepcionera(1, "Pera", "Peric");
		assertEquals(1, mr.getRecepcioneri().size());
		assertEquals("Pera", mr.getRecepcionerById(1).getIme());
		assertEquals("Peric", mr.getRecepcionerById(1).getPrezime());
	}
	@Test
	public void testDodajRecepcionera3() {
        mr.dodajRecepcionera(1, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "korime", "lozinka");
        assertEquals(1, mr.getRecepcioneri().size());
        assertEquals("Pera", mr.getRecepcionerById(1).getIme());
        assertEquals("Peric", mr.getRecepcionerById(1).getPrezime());
        assertEquals(Pol.MUSKI, mr.getRecepcionerById(1).getPol());
        assertEquals(LocalDate.now(), mr.getRecepcionerById(1).getDatumRodjenja());
        assertEquals("123456", mr.getRecepcionerById(1).getTelefon());
        assertEquals("adresa", mr.getRecepcionerById(1).getAdresa());
        assertEquals("korime", mr.getRecepcionerById(1).getKorisnickoIme());
        assertEquals("lozinka", mr.getRecepcionerById(1).getLozinka());                
	}
	@Test
	public void testGetRecepcioneri() {
		mr.dodajRecepcionera(1, "Pera", "Peric");
		mr.dodajRecepcionera(2, "Mika", "Mikic");
		mr.dodajRecepcionera(3, "Zika", "Zikic");
		assertEquals(3, mr.getRecepcioneri().size());
	}
	@Test
	public void testUkloniRecepcionera() {
		mr.dodajRecepcionera(1, "Pera", "Peric");
		mr.dodajRecepcionera(2, "Mika", "Mikic");
		mr.dodajRecepcionera(3, "Zika", "Zikic");
		mr.ukloniRecepcionera(2);
		assertEquals(2, mr.getRecepcioneri().size());
		mr.ukloniRecepcionera(10);
		assertEquals(2, mr.getRecepcioneri().size());
	}
	@Test
	public void testPostojiKorisnik() {
		mr.dodajRecepcionera(5, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		mr.dodajRecepcionera(6, "Mika", "Mikic", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "mika", "lozinka");
		mr.dodajRecepcionera(7, "Zika", "Zikic", Pol.ZENSKI, LocalDate.now(), "123456", "adresa", "zika", "lozinka");
		Recepcioner r = mr.postojiKorisnik("korime", "lozinka");
		assertEquals(null, r);
		r = mr.postojiKorisnik("mika", "lozinka");
		assertEquals(6, r.getId());
	}
	@Test
	public void testgetRecepcionerByKorisnickoIme() {
		mr.dodajRecepcionera(8, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		mr.dodajRecepcionera(9, "Mika", "Mikic", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "mika", "lozinka");
		mr.dodajRecepcionera(10, "Zika", "Zikic", Pol.ZENSKI, LocalDate.now(), "123456", "adresa", "zika", "lozinka");
		Recepcioner r = mr.getRecepcionerByKorisnickoIme("zika");
		assertEquals(10, r.getId());
		r = mr.getRecepcionerByKorisnickoIme("korime");
		assertEquals(null, r);
	}
}
