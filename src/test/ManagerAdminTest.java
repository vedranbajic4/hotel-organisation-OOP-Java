package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Administrator;
import enums.Pol;
import managerKlase.ManagerAdmin;

public class ManagerAdminTest {
	private ManagerAdmin ma;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		System.out.println("ManagerAdminTest start");
		ma = new ManagerAdmin();
	}

	@After
	public void tearDownAfterClass() throws Exception {
		System.out.println("ManagerAdminTest end");
	}
	
	@Test
	public void testDodajAdmina1() {
		ma.dodajAdmina(1, "Pera", "Peric");
		assertEquals("Pera", ma.getAdminById(1).getIme());
		assertEquals("Peric", ma.getAdminById(1).getPrezime());
	}
	@Test
	public void testDodajAdmina2() {
        ma.dodajAdmina(1, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "korime", "lozinka");
        
        assertEquals("Pera", ma.getAdminById(1).getIme());
        assertEquals("Peric", ma.getAdminById(1).getPrezime());
        assertEquals(Pol.MUSKI, ma.getAdminById(1).getPol());
        assertEquals(LocalDate.now(), ma.getAdminById(1).getDatumRodjenja());
        assertEquals("123456", ma.getAdminById(1).getTelefon());
        assertEquals("adresa", ma.getAdminById(1).getAdresa());
        assertEquals("korime", ma.getAdminById(1).getKorisnickoIme());
        assertEquals("lozinka", ma.getAdminById(1).getLozinka());       
	}
	
	@Test
	public void testUkloniAdmina() {
		ma.dodajAdmina(1, "Pera", "Peric");
		ma.dodajAdmina(2, "Mika", "Mikic");
		ma.dodajAdmina(3, "Zika", "Zikic");
		ma.ukloniAdmina(2);
		assertEquals(2, ma.getAdmini().size());
		ma.ukloniAdmina(10);
		assertEquals(2, ma.getAdmini().size());
	}
	@Test
	public void testPostojiKorisnik() {
		ma.dodajAdmina(5, "Pera", "Peric", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "pera", "lozinka");
		ma.dodajAdmina(6, "Mika", "Mikic", Pol.MUSKI, LocalDate.now(), "123456", "adresa", "mika", "lozinka");
		ma.dodajAdmina(7, "Zika", "Zikic", Pol.ZENSKI, LocalDate.now(), "123456", "adresa", "zika", "lozinka");
		Administrator a = ma.postojiKorisnik("korime", "lozinka");
		assertEquals(null, a);
		a = ma.postojiKorisnik("mika", "lozinka");
		assertEquals(6, a.getId());
	}
}
