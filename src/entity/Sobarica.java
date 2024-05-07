package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Pol;
import enums.StatusSobe;
import enums.StrucnaSprema;

public class Sobarica extends Korisnik{
	private int plata;
	private int staz;
	private StrucnaSprema strucnaSprema;
	List<Soba> sobeZaSpremanje;
	
	//konstruktori
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
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.plata = plata;
		this.staz = staz;
		this.strucnaSprema = strucnaSprema;
		sobeZaSpremanje = new ArrayList<Soba>();
	}
	//metode
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
	
	public void dodajSobu(Soba s) {
		sobeZaSpremanje.add(s);
	}
	public void spremiSobu(Soba s) {
		sobeZaSpremanje.remove(s);
		s.setStatus(StatusSobe.SLOBODNA);
	}
	
}
