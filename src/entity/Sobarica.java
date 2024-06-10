package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Pol;
import enums.StatusSobe;
import enums.StrucnaSprema;

public class Sobarica extends Zaposleni{
	List<Soba> sobeZaSpremanje = new ArrayList<Soba>();
	
	public Sobarica() {
		sobeZaSpremanje = new ArrayList<Soba>();
	}
	public Sobarica(int id, String ime, String prezime) {
		super(id, ime, prezime);
		sobeZaSpremanje = new ArrayList<Soba>();
	}
	public Sobarica(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		sobeZaSpremanje = new ArrayList<Soba>();
	}

	public Sobarica(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz,
				strucnaSprema);
	}
	public Sobarica(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz,
				strucnaSprema);
	}

	
	public List<Soba> getSobeZaSpremanje() {
		return sobeZaSpremanje;
	}

	
	public int brojSobaZaSpremanje() {
		return this.sobeZaSpremanje.size();
	}
	public void dodajSobu(Soba s) {
		sobeZaSpremanje.add(s);
	}
	public void spremiSobu(Soba s) {
		sobeZaSpremanje.remove(s);
		s.setStatus(StatusSobe.SLOBODNA);
	}
	
}
