package entity;

public class DodatnaUsluga {
	private String naziv;
	private double cena;
    private int id;
    private static int ID_GENERATOR = 1;
	
	public DodatnaUsluga() {}

	public DodatnaUsluga(String naziv) {
		this.naziv = naziv;
		this.id = ID_GENERATOR++;
	}
	public DodatnaUsluga(String naziv, double cena) {
		this.naziv = naziv;
		this.cena = cena;
		this.id = ID_GENERATOR++;
	}
	public DodatnaUsluga(int id, String naziv, double cena) {
		this.naziv = naziv;
		this.cena = cena;
		this.id = id;
		if(id >= ID_GENERATOR) ID_GENERATOR = id+1;	//pri ucitavanju dodatnih usluga, pratim ID i postavljam na max
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public double getCena() {
		return this.cena;
	}

	public int getId() {
		return this.id;
	}
	public String toFileString() {
		return this.id + "," + this.naziv + "," + this.cena;
	}
}
