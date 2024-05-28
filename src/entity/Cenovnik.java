package entity;

import java.time.LocalDate;

public class Cenovnik {
	private int id;
	private LocalDate datumPocetka;
	private LocalDate datumKraja;
	
	//konstruktori
	public Cenovnik() {}
	public Cenovnik(int id, LocalDate poc) {
		this.datumPocetka = poc;
		this.datumKraja = null;
		this.id = id;
	}
	public Cenovnik(int id, LocalDate poc, LocalDate kraj) {
		this.datumPocetka = poc;
		this.datumKraja = kraj;
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public String toFileString() {
		return id + "," + datumPocetka + "," + datumKraja;
	}
}
