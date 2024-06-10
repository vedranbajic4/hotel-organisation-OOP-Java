package entity;

import java.time.LocalDate;

public class Cenovnik {
	private int id;
	private LocalDate datumPocetka;
	private LocalDate datumKraja;
	private static int ID_GENERATOR = 1;
	
	//konstruktori
	public Cenovnik() {}
	public Cenovnik(int id, LocalDate poc) {
		this.datumPocetka = poc;
		this.datumKraja = null;
		this.id = id;
		if(id >= ID_GENERATOR) ID_GENERATOR = id+1;
	}
	public Cenovnik(int id, LocalDate poc, LocalDate kraj) {
		this.datumPocetka = poc;
		this.datumKraja = kraj;
		this.id = id;
	}
	public Cenovnik(LocalDate poc, LocalDate kraj) {
		this.datumPocetka = poc;
		this.datumKraja = kraj;
		this.id = ID_GENERATOR++;
	}
	public int getId() {
		return this.id;
	}

	public LocalDate getDatumPocetak() {
		return this.datumPocetka;
	}

	public LocalDate getDatumKraj() {
		return this.datumKraja;
	}

	public void setDatumPocetak(LocalDate poc) {
		this.datumPocetka = poc;
	}

	public void setDatumKraj(LocalDate kraj) {
		this.datumKraja = kraj;
	}
	
	public String toFileString() {
		return id + "," + datumPocetka + "," + datumKraja;
	}
	@Override
	public String toString() {
		return "Cenovnik [id=" + id + ", datumPocetka=" + datumPocetka + ", datumKraja=" + datumKraja + "]";
	}
}
