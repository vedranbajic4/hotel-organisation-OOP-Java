package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Pol;
import enums.StrucnaSprema;
import managerKlase.ManagerGost;
import managerKlase.ManagerRecepcioner;

public class Recepcioner extends Korisnik{
	private int plata;
	private int staz;
	private StrucnaSprema strucnaSprema;
	
	//konstruktori
	public Recepcioner() {}
	public Recepcioner(int id, String ime, String prezime) {
		super(id, ime, prezime);
	}
	public Recepcioner(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
	}
	public Recepcioner(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.plata = plata;
		this.staz = staz;
		this.strucnaSprema = strucnaSprema;
	}

	public void setPlata(int plata) {
		this.plata = plata;
	}

	public int getPlata() {
		return this.plata;
	}

	public void setStaz(int staz) {
		this.staz = staz;
	}

	public int getStaz() {
		return this.staz;
	}

	public void setStrucnaSprema(StrucnaSprema strucnaSprema) {
		this.strucnaSprema = strucnaSprema;
	}
	public StrucnaSprema getStrucnaSprema() {
		return this.strucnaSprema;
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
