package entity;

import java.time.LocalDate;

import enums.Pol;
import enums.StrucnaSprema;

public class Zaposleni extends Korisnik{
	private int plata;
	private int staz;
	private StrucnaSprema strucnaSprema;
	
	public Zaposleni() {}

	public Zaposleni(int id, String ime, String prezime) {
		super(id, ime, prezime);
	}
	public Zaposleni(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
	}
	public Zaposleni(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.plata = plata;
		this.staz = staz;
		this.strucnaSprema = strucnaSprema;
	}

	public Zaposleni(int id, String ime, String prezime, int plata, int staz, StrucnaSprema strucnaSprema) {
		super(id, ime, prezime);
		this.plata = plata;
		this.staz = staz;
		this.strucnaSprema = strucnaSprema;
	}
	
	public void setPlata(int plata) {
        this.plata = plata;
    }
	public int getPata() {
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
	
	public String toFileString() {
		String ssString = null;
		if(strucnaSprema != null) ssString = strucnaSprema.toString();
		return String.join(",", this.toFileStringK(), String.valueOf(plata), String.valueOf(staz), ssString);
	}
}
