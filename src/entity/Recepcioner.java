package entity;

import java.time.LocalDate;
import enums.Pol;
import enums.StrucnaSprema;
import managerKlase.ManagerGost;

public class Recepcioner extends Zaposleni{
	public Recepcioner() {
		super();
	}

	public Recepcioner(int id, String ime, String prezime) {
		super(id, ime, prezime);
	}

	public Recepcioner(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon,
			String adresa, String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
	}
	public Recepcioner(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon,
			String adresa, String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema);
	}
	public void izracunajPlatu() {
		//fuja koja izracunava platu preko staza i strucne spreme
    }
	//dodavanje gostiju
	public void dodajGosta(int id, String ime, String prezime, ManagerGost mg) {
		mg.dodajGosta(id, ime, prezime);
	}
	public void dodajGosta(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, ManagerGost mg) {
		mg.dodajGosta(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
	}
}
