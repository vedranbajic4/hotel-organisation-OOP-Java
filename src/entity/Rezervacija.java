package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.StatusRezervacije;

public class Rezervacija {
	private int id;
	private StatusRezervacije status;		//status (Na cekanju, Potvrdjena...)
	private TipSobe tipSobe;				//tip sobe koju gost zeli
	private Soba soba = null;				//soba koja je dodeljena gostu
	private LocalDate datumDolaska;
	private LocalDate datumOdlaska;
	private List<DodatnaUsluga> usluge;		//usluge koje gost zeli 
	private float cena = 0;					//cena rezervacije
	private static int ID_GENERATOR = 1;
	
	//konstruktori
	public Rezervacija() {
		this.id = ID_GENERATOR++;
		this.usluge = new ArrayList<DodatnaUsluga>();
	}
	public Rezervacija(TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska) {
		this.id = ID_GENERATOR++;
		this.status = StatusRezervacije.NACEKANJU;
		this.tipSobe = tipSobe;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.usluge = new ArrayList<DodatnaUsluga>();
	}
	public Rezervacija(int id, StatusRezervacije status, TipSobe tipSobe, Soba soba, LocalDate datumDolaska, LocalDate datumOdlaska, float cena) {
		if(id >= ID_GENERATOR) ID_GENERATOR = id+1;
		this.id = id;
        this.status = status;
        this.tipSobe = tipSobe;
        this.soba = soba;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = new ArrayList<DodatnaUsluga>();
        this.cena = cena;
    }
	public Rezervacija(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska, ArrayList<DodatnaUsluga> usluge) {
		if(id >= ID_GENERATOR) ID_GENERATOR = id+1;
		this.id = id;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = usluge;
    }
	
	//geteri i seteri
	public float getUkupnaCena() {
		return this.cena;
	}
	public LocalDate getDatumDolaska() {
		return datumDolaska;
	}

	public TipSobe getTipSobe() {
		return tipSobe;
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

	public void dodajUslugu(DodatnaUsluga usluga) {
		this.usluge.add(usluga);
	}
	public List<DodatnaUsluga> getUsluge() {
		return this.usluge;
	}

	public void setUsluge(List<DodatnaUsluga> usluge) {
		this.usluge = usluge;
	}
	
	public int getId() {
		return id;
	}

	public float getCena() {
		return cena;
	}
	public void addCena(float kes) {
		this.cena += kes;
	}
	public void setCena(float kes) {
		this.cena = kes;
	}

	public void setDatumDolaska(LocalDate datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

	public void setDatumOdlaska(LocalDate datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}

	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}
	@Override 
	public String toString() {
		return "Rezervacija [id=" + id + ", status=" + status + ", tipSobe=" + tipSobe 
				+ ", soba=" + soba + ", datumDolaska=" + datumDolaska + ", datumOdlaska=" + datumOdlaska +  "]";
	}
	
	public String toFileString() {
		int sobaId = -1;
		if(this.soba != null) sobaId = this.soba.getId();
		return (id+","+status+","+tipSobe.getBrojKreveta()+"," +tipSobe.getRaspored()+","+sobaId+","+datumDolaska+","+datumOdlaska+","+cena);
	}

	public String smallString() {
		return id + ", " + tipSobe + ", od "
				+ datumDolaska + ", do " + datumOdlaska;
	}
}
