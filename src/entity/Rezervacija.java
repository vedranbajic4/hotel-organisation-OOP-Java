package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.StatusRezervacije;

public class Rezervacija {
	private int id;
	private StatusRezervacije status;	//status (Na cekanju, Potvrdjena...)
	private TipSobe tipSobe;			//tip sobe koju gost zeli
	private Soba soba;					//soba koja je dodeljena gostu
	private LocalDate datumDolaska;
	private LocalDate datumOdlaska;
	private List<DodatnaUsluga> usluge;		//usluge koje gost zeli 
	
	//konstruktori
	public Rezervacija() {
		this.usluge = new ArrayList<DodatnaUsluga>();
	}

	public Rezervacija(int id, StatusRezervacije status, TipSobe tipSobe, Soba soba, LocalDate datumDolaska, LocalDate datumOdlaska) {
        this.id = id;
        this.status = status;
        this.tipSobe = tipSobe;
        this.soba = soba;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = new ArrayList<DodatnaUsluga>();
    }
	public Rezervacija(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska) {
        this.id = id;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
        this.usluge = new ArrayList<DodatnaUsluga>();
    }
	public Rezervacija(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska, ArrayList<DodatnaUsluga> usluge) {
		this.id = id;
		this.status = StatusRezervacije.NACEKANJU;
        this.tipSobe = tipSobe;
        this.datumDolaska = datumDolaska;
        this.datumOdlaska = datumOdlaska;
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

	public void dodajUslugu(DodatnaUsluga usluga) {
		this.usluge.add(usluga);
	}
	public List<DodatnaUsluga> getUsluge() {
		return this.usluge;
	}

	public int getId() {
		return id;
	}
	@Override 
	public String toString() {
		return "Rezervacija [id=" + id + ", status=" + status + ", tipSobe=" + tipSobe 
				+ ", soba=" + soba + ", datumDolaska=" + datumDolaska + ", datumOdlaska=" + datumOdlaska +  "]";
	}
	
	public String toFileString() {
		int sobaId = -1;
		if(soba != null) sobaId = soba.getId();
		return (id+","+status+","+tipSobe.getBrojKreveta()+"," +tipSobe.getRaspored()+","+sobaId+","+datumDolaska+","+datumOdlaska);
	}
}
