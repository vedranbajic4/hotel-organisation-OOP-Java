package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.StatusRezervacije;

public class Rezervacija {
	private int id;
	private StatusRezervacije status;	//status (Na cekanju, Potvrdjena...)
	private int tipSobe;				//zahtevani tip sobe
	private String raspored;			//zahtevani broj kreveta
	private Soba soba;					//soba koja je dodeljena gostu
	private LocalDate datumDolaska;
	private LocalDate datumOdlaska;
	private List<String> usluge;		//usluge koje gost zeli 
	
	//konstruktori
	public Rezervacija() {
		this.usluge = new ArrayList<String>();
	}
	public Rezervacija(int id, int tipSobe, String raspored, LocalDate datumDolaska, LocalDate datumOdlaska) {
        this.id = id;
        this.raspored = raspored;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = new ArrayList<String>();
    }
	public Rezervacija(int id, int tipSobe, String raspored, LocalDate datumDolaska) {
		this.id = id;
		this.raspored = raspored;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.usluge = new ArrayList<String>();
    }
	public Rezervacija(int id, int tipSobe, String raspored, LocalDate datumDolaska, LocalDate datumOdlaska, ArrayList<String> usluge) {
		this.id = id;
		this.raspored = raspored;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = usluge;
    }
	public Rezervacija(int id, int tipSobe, String raspored, LocalDate datumDolaska, ArrayList<String> usluge) {
		this.id = id;
		this.raspored = raspored;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.usluge = usluge;
    }
	public Rezervacija(int id, int tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska) {
        this.id = id;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = new ArrayList<String>();
    }
	public Rezervacija(int id, int tipSobe, LocalDate datumDolaska) {
		this.id = id;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.usluge = new ArrayList<String>();
    }
	public Rezervacija(int id, int tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska, ArrayList<String> usluge) {
		this.id = id;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = usluge;
    }
	public Rezervacija(int id, int tipSobe, LocalDate datumDolaska, ArrayList<String> usluge) {
		this.id = id;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.usluge = usluge;
    }
	//geteri i seteri
	public LocalDate getDatumDolaska() {
		return datumDolaska;
	}

	public LocalDate getDatumOdlaska() {
		return datumOdlaska;
	}
	//metode
	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}
	public StatusRezervacije getStatus() {
		return this.status;
	}
	
	public void setSoba(Soba s) {		//kad se odredi koja soba ispunjava uslove, moze da se dodeli
		this.soba = s;
	}
	public Soba getSoba() {
		return this.soba;
	}

	public void dodajUslugu(String usluga) {
		this.usluge.add(usluga);
	}
	public List<String> getUsluge() {
		return this.usluge;
	}

	public int getId() {
		return id;
	}
	@Override 
	public String toString() {
		return "Rezervacija [id=" + id + ", status=" + status + ", tipSobe=" + tipSobe 
				+ ", soba=" + soba + ", datumDolaska=" + datumDolaska + ", datumOdlaska=" + datumOdlaska + ", usluge="
				+ usluge + "]";
	}
}
