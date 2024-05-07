package managerKlase;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Administrator;
import entity.Recepcioner;
import entity.Sobarica;
import enums.Pol;
import enums.StrucnaSprema;

public class ManagerRecepcioner {
	ArrayList<Recepcioner> lista;
	public ManagerRecepcioner() {
		lista = new ArrayList<Recepcioner>();
	}
	// dodavanje recepcionera
	public void dodajRecepcionera(int id, String ime, String prezime) {
		lista.add(new Recepcioner(id, ime, prezime));
	}
	public void dodajRecepcionera(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Recepcioner(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}
	public void dodajRecepcionera(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		lista.add(new Recepcioner(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema));
	}

	public Recepcioner getRecepcionerById(int id) {
		for (Recepcioner r : lista) {
			if (r.getId() == id) {
				return r;
			}
		}
		return null;
	}
	//uklanjanje recepcionera
	public void ukloniRecepcionera(int id) {
		Recepcioner r = getRecepcionerById(id);
		if (r != null) {
			lista.remove(r);
		}
	}
	public void prikaziSveRecepcionere() {
		System.out.println("Svi recepcioneri u sistemu su: ");
		for (Recepcioner r : lista) {
			System.out.println(r);
		}
	}
}
