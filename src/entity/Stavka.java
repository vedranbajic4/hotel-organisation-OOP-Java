package entity;

public class Stavka {
	private String naziv;
	private int idCenovnika;
	private double cena;
	private int id;
	
	//konstruktori
	public Stavka() {}
	public Stavka(int id, String naziv) {
		this.naziv = naziv;
		this.id = id;
	}
	public Stavka(int id, String naziv, double cena) {
		this.naziv = naziv;
		this.cena = cena;
		this.id = id;
	}
	public Stavka(int id, String naziv, int idCenovnika, double cena) {
		this.naziv = naziv;
		this.idCenovnika = idCenovnika;
		this.cena = cena;
		this.id = id;
	}
	//geteri, seteri
	public int getId() {
		return this.id;
	}
	public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
	public String getNaziv() {
		return this.naziv;
	}
	public void setIdCenovnika(int idCenovnika) {
		this.idCenovnika = idCenovnika;
	}
	public int getCenovnik() {
		return this.idCenovnika;
	}
	public void setCena(double cena) {
        this.cena = cena;
    }
	public double getCena() {
	    return this.cena;
	}
}
