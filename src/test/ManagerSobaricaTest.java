package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Recepcioner;
import entity.Soba;
import entity.Sobarica;
import entity.TipSobe;
import enums.Pol;
import managerKlase.ManagerSobarica;

public class ManagerSobaricaTest {
	ManagerSobarica mr;
	
	@Before
	public void setUp() throws Exception {
		mr = new ManagerSobarica();
		System.out.println("ManagerSobaricaTest start");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("ManagerSobaricaTest end\n");
	}

	@Test
	public void testDodajSobaricu2() {
		mr.dodajSobaricu(1, "Pera", "Peric");
		assertEquals(1, mr.getSobarice().size());
		assertEquals("Pera", mr.getSobaricaById(1).getIme());
		assertEquals("Peric", mr.getSobaricaById(1).getPrezime());
	}
	@Test
	public void testDodajSobaricu3() {
        mr.dodajSobaricu(1, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "korime", "lozinka");
        assertEquals(1, mr.getSobarice().size());
        assertEquals("Pera", mr.getSobaricaById(1).getIme());
        assertEquals("Peric", mr.getSobaricaById(1).getPrezime());
        assertEquals(Pol.MUSKI, mr.getSobaricaById(1).getPol());
        assertEquals(LocalDate.now(), mr.getSobaricaById(1).getDatumRodjenja());
        assertEquals("123456", mr.getSobaricaById(1).getTelefon());
        assertEquals("adresa", mr.getSobaricaById(1).getAdresa());
        assertEquals("korime", mr.getSobaricaById(1).getKorisnickoIme());
        assertEquals("lozinka", mr.getSobaricaById(1).getLozinka());                
	}
	@Test
	public void testGetSobarice() {
		mr.dodajSobaricu(1, "Pera", "Peric");
		mr.dodajSobaricu(2, "Mika", "Mikic");
		mr.dodajSobaricu(3, "Zika", "Zikic");
		assertEquals(3, mr.getSobarice().size());
	}
	@Test
	public void testUkloniSobaricu() {
		mr.dodajSobaricu(1, "Pera", "Peric");
		mr.dodajSobaricu(2, "Mika", "Mikic");
		mr.dodajSobaricu(3, "Zika", "Zikic");
		mr.ukloniSobaricu(2);
		assertEquals(2, mr.getSobarice().size());
		mr.ukloniSobaricu(10);
		assertEquals(2, mr.getSobarice().size());
	}
	@Test
	public void testPostojiKorisnik() {
		mr.dodajSobaricu(5, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		mr.dodajSobaricu(6, "Mika", "Mikic", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "mika", "lozinka");
		mr.dodajSobaricu(7, "Zika", "Zikic", Pol.ZENSKI, LocalDate.now(), "123456", "adresa", "zika", "lozinka");
		Sobarica r = mr.postojiKorisnik("korime", "lozinka");
		assertEquals(null, r);
		r = mr.postojiKorisnik("mika", "lozinka");
		assertEquals(6, r.getId());
	}
	@Test
	public void testgetSobaricaByKorisnickoIme() {
		mr.dodajSobaricu(8, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		mr.dodajSobaricu(9, "Mika", "Mikic", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "mika", "lozinka");
		mr.dodajSobaricu(10, "Zika", "Zikic", Pol.ZENSKI, LocalDate.now(), "123456", "adresa", "zika", "lozinka");
		Sobarica r = mr.getSobaricaByKorisnickoIme("zika");
		assertEquals(10, r.getId());
		r = mr.getSobaricaByKorisnickoIme("korime");
		assertEquals(null, r);
	}
	@Test
	public void testDodeliSobu() {
		mr.dodajSobaricu(5, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		Sobarica s = mr.getSobaricaById(5);
		TipSobe tipSobe = new TipSobe(1, "jednokrevetna");
		Soba soba = new Soba(5, tipSobe);
		
		mr.dodeliSobu(soba);
		assertEquals(1, s.brojSobaZaSpremanje());
	}
}
