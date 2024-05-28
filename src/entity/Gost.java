package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Pol;
import managerKlase.ManagerRezervacija;

public class Gost extends Korisnik{
	List<Rezervacija> rezervacije;	//gost vidi sve svoje rezervacije
	//konstruktori
	public Gost() {
		rezervacije = new ArrayList<Rezervacija>();
	}
	public Gost(int id, String ime, String prezime) {
		super(id, ime, prezime);
		rezervacije = new ArrayList<Rezervacija>();
	}
	public Gost(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		rezervacije = new ArrayList<Rezervacija>();
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
	public void zahtevRezervacije(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, datumDolaska, datumOdlaska);
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska));
	}
	public void zahtevRezervacije(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<DodatnaUsluga> usluge, ManagerRezervacija mr) {
		mr.dodajRezervaciju(id, tipSobe, datumDolaska, datumOdlaska, usluge);
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska, usluge));
	}
}
