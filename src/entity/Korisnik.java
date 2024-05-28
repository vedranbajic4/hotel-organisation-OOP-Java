package entity;

import java.time.LocalDate;

import enums.Pol;

public abstract class Korisnik {
	public String ime;
	public String prezime;
	private int id;
	Pol pol;
	public LocalDate datumRodjenja;
	public String telefon;
	public String adresa;
	public String korisnickoIme;
	public String lozinka;
	
	public Korisnik() {}
	
	public Korisnik(int id, String ime, String prezime) {
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
	}

	public Korisnik(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
	}
	public int getId() {
		return id;
	}
	public boolean postojiKorisnik(String korisnickoIme, String lozinka) {
		return this.korisnickoIme.equals(korisnickoIme) && this.lozinka.equals(lozinka);
	}
	@Override
	public String toString() {
		return "Korisnik [id=" + id + " ime=" + ime + ", prezime=" + prezime + ", pol=" + pol + ", datumRodjenja=" + datumRodjenja
                + ", telefon=" + telefon + ", adresa=" + adresa + ", korisnickoIme=" + korisnickoIme + ", lozinka="
                + lozinka + "]";
	}
	public String toFileStringK() {
		String polString = "null", datumString = "null";
		if(pol != null) polString = pol.toString();
		if(datumRodjenja != null) datumString = datumRodjenja.toString();
		
		return String.join(",", String.valueOf(id), ime, prezime, polString,
				datumString, telefon, adresa, korisnickoIme, lozinka);
	}
}
