package managerKlase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Gost;
import entity.Recepcioner;
import enums.Pol;
import enums.StrucnaSprema;

public class ManagerGost {
	List<Gost> lista;
	public ManagerGost() {
		lista = new ArrayList<Gost>();
	}
	// dodavanje gosta
	public void dodajGosta(int id, String ime, String prezime) {
		lista.add(new Gost(id, ime, prezime));
	}
	public void dodajGosta(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Gost(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}

	public Gost getGostById(int id) {
		for (Gost g : lista) {
			if (g.getId() == id) {
				return g;
			}
		}
		return null;
	}
	public void prikaziSveGoste() {
		System.out.println("Svi gosti u sistemu su: ");
		for (Gost g : lista) {
			System.out.println(g);
		}
	}
}
