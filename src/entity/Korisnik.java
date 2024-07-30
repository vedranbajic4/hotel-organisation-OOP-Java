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
	private static int id_gen = 1;
<<<<<<< HEAD
=======

>>>>>>> e5a83006847e2c36fff78d44ebc6f7f1ee431116
	
	public Korisnik() {}
	
	public Korisnik(int id, String ime, String prezime) {
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		if(id_gen <= id) id_gen = id + 1;
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
		if(id_gen <= id) id_gen = id + 1;
	}
	
	public Korisnik(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		this.id = id_gen++;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		if(id_gen <= id) id_gen = id + 1;
	}
<<<<<<< HEAD
	//GETERI
=======
	
>>>>>>> e5a83006847e2c36fff78d44ebc6f7f1ee431116
	public int getId() {
		return id;
	}
	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}
<<<<<<< HEAD
=======

>>>>>>> e5a83006847e2c36fff78d44ebc6f7f1ee431116
	public String getIme() {
		return this.ime;
	}
	public String getPrezime() {
		return this.prezime;
	}
<<<<<<< HEAD
	public String getLozinka() {
		return this.lozinka;
	}
	public String getAdresa() {
		return this.adresa;
	}

	public Pol getPol() {
		return this.pol;
	}
	public String getTelefon() {
		return this.telefon;
	}

	public LocalDate getDatumRodjenja() {
		return this.datumRodjenja;
	}
	
	//SETERI
	public void setIme(String ime) {
		this.ime = ime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
	public void setPol(Pol pol) {
		this.pol = pol;
	}
	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	
=======
>>>>>>> e5a83006847e2c36fff78d44ebc6f7f1ee431116
	public boolean postojiKorisnik(String korisnickoIme, String lozinka) {
		return this.korisnickoIme.equals(korisnickoIme) && this.lozinka.equals(lozinka);
	}
	public boolean postojiKorisnik(String korisnickoIme) {
		return this.korisnickoIme.equals(korisnickoIme);
	}
<<<<<<< HEAD
=======

	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
>>>>>>> e5a83006847e2c36fff78d44ebc6f7f1ee431116
	
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
