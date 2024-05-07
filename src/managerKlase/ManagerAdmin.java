package managerKlase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Administrator;
import enums.Pol;
import enums.StrucnaSprema;

public class ManagerAdmin {
	List<Administrator> lista;
	
	public ManagerAdmin() {
		lista = new ArrayList<Administrator>();
	}
	// dodavanje Administratora
	public void dodajAdmina(int id, String ime, String prezime) {
		lista.add(new Administrator(id, ime, prezime));
	}
	public void dodajAdmina(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Administrator(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}
	public void dodajAdmina(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		lista.add(new Administrator(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema));
	}
	public Administrator getAdminById(int id) {
		for (Administrator a : lista) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}
	public void prikaziSveAdmine() {
		System.out.println("Svi admini u sistemu su: ");
		for (Administrator a : lista) {
			System.out.println(a);
		}
	}
}
