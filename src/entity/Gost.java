package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Pol;
import managerKlase.ManagerRezervacija;

public class Gost extends Korisnik{
	Soba soba;						//gost ima sobu u kojoj je odseo
	List<Rezervacija> rezervacije;	//gost vidi sve svoje rezervacije
	//konstruktori
	public Gost() {
		rezervacije = new ArrayList<Rezervacija>();
	}
	public Gost(int id, String ime, String prezime) {
		super(id, ime, prezime);
		rezervacije = new ArrayList<Rezervacija>();
	}
	public Gost(int id, String ime, String prezime, Soba soba) {
		super(id, ime, prezime);
		this.soba = soba;
		rezervacije = new ArrayList<Rezervacija>();
	}

	public Gost(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		rezervacije = new ArrayList<Rezervacija>();
	}
	
	public Gost(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, Soba soba) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.soba = soba;
		rezervacije = new ArrayList<Rezervacija>();
	}
	
	// Metode
	public void setSoba(Soba S) {
        this.soba = S;
    }
	public Soba getSoba() {
		return this.soba;
	}
	
	public void dodajRezervaciju(Rezervacija r) {
		rezervacije.add(r);
	}
	public void ispisiRezervacije() {
		System.out.println("Rezervacije:");
		for (Rezervacija r : rezervacije) {
			System.out.println(r);
		}
	}

	public void zahtevRezervacije(Rezervacija r, ManagerRezervacija mr) {
		mr.dodajRezervaciju(r);
		rezervacije.add(r);
	}
	public void zahtevRezervacije(int id, int tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, datumDolaska, datumOdlaska);
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska));
	}
	public void zahtevRezervacije(int id, int tipSobe, LocalDate datumDolaska, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, datumDolaska);
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska));
	}
	public void zahtevRezervacije(int id, int tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<String> usluge, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, datumDolaska, datumOdlaska, usluge);
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska, usluge));
	}
	public void zahtevRezervacije(int id, int tipSobe, LocalDate datumDolaska, ArrayList<String> usluge,
			ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, datumDolaska, usluge);
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, usluge));
	}
	public void zahtevRezervacije(int id, int tipSobe, String raspored, LocalDate datumDolaska, LocalDate datumOdlaska, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, raspored, datumDolaska, datumOdlaska);
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska, datumOdlaska));
	}
	public void zahtevRezervacije(int id, int tipSobe, String raspored, LocalDate datumDolaska, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, raspored, datumDolaska);
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska));
	}
	public void zahtevRezervacije(int id, int tipSobe, String raspored, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<String> usluge, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, raspored, datumDolaska, datumOdlaska, usluge);
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska, datumOdlaska, usluge));
	}
	public void zahtevRezervacije(int id, int tipSobe, String raspored, LocalDate datumDolaska, ArrayList<String> usluge,
			ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, raspored, datumDolaska, usluge);
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska, usluge));
	}
}
