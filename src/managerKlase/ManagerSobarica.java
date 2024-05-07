package managerKlase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Administrator;
import entity.Sobarica;
import enums.Pol;
import enums.StrucnaSprema;

public class ManagerSobarica {
	List<Sobarica> lista;

	public ManagerSobarica() {
		lista = new ArrayList<Sobarica>();
	}
	// dodavanje sobarice
	public void dodajSobaricu(int id, String ime, String prezime) {
		lista.add(new Sobarica(id, ime, prezime));
	}
	public void dodajSobaricu(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Sobarica(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}
	public void dodajSobaricu(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		lista.add(new Sobarica(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema));
	}
	public Sobarica getSobaricaById(int id) {
		for (Sobarica s : lista) {
			if (s.getId() == id) {
				return s;
			}
		}
		return null;
	}
	//uklanjanje sobarice
	public void ukloniSobaricu(int id) {
		Sobarica s = getSobaricaById(id);
		if (s != null) {
			lista.remove(s);
		}
	}
	//prikaz svih sobarica
	public void prikaziSveSobarice() {
		System.out.println("Sve sobarice u sistemu su: ");
        for (Sobarica s : lista) {
            System.out.println(s);
        }
	}
}
