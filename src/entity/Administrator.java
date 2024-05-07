package entity;

import java.time.LocalDate;

import enums.Pol;
import enums.StrucnaSprema;
import managerKlase.ManagerRecepcioner;
import managerKlase.ManagerSobarica;

public class Administrator extends Korisnik{
	private int plata;
	private int staz;
	private StrucnaSprema strucnaSprema;
	
	//konstruktori
	public Administrator() {}
	public Administrator(int id, String ime, String prezime) {
		super(id, ime, prezime);
	}
	public Administrator(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
	}
	public Administrator(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
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
	//dodavanje sobarice
	public void dodajSobaricu(int id, String ime, String prezime, ManagerSobarica ms) {
		ms.dodajSobaricu(id, ime, prezime);
	}
	public void dodajSobaricu(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, ManagerSobarica ms) {
		ms.dodajSobaricu(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
	}
	public void dodajSobaricu(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema, ManagerSobarica ms) {
		ms.dodajSobaricu(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema);
	}
	//dodavanje recepcionera
	public void dodajRecepcionera(int id, String ime, String prezime, ManagerRecepcioner mr) {
		mr.dodajRecepcionera(id, ime, prezime);
	}
	public void dodajRecepcionera(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, ManagerRecepcioner mr) {
		mr.dodajRecepcionera(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
	}
	public void dodajRecepcionera(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema, ManagerRecepcioner mr) {
		mr.dodajRecepcionera(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema);
	}
	//uklanjanje sobarice
	public void ukloniSobaricu(int id, ManagerSobarica ms) {
		ms.ukloniSobaricu(id);
	}
	//uklanjanje recepcionera
	public void ukloniRecepcionera(int id, ManagerRecepcioner mr) {
		mr.ukloniRecepcionera(id);
	}
	
}
