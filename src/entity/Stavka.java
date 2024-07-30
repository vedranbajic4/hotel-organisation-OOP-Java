package entity;

import java.util.List;

public class Stavka {
	private int idCenovnika;
	private DodatnaUsluga dodatnaUsluga;
	private float cena = -1;
	private int id;
	private static int ID_GENERATOR = 1;
	
	//konstruktori
	public Stavka() {}
	public Stavka(int id, String naziv) {
		this.dodatnaUsluga = new DodatnaUsluga(naziv);
		this.id = id;
		if(id >= ID_GENERATOR) ID_GENERATOR = id+1;
	}
	public Stavka(int id, DodatnaUsluga du) {
		this.dodatnaUsluga = du;
		this.id = id;
		if(id >= ID_GENERATOR) ID_GENERATOR = id+1;
	}
	public Stavka(int id, DodatnaUsluga du, int idCenovnika, float cena) {
		this.dodatnaUsluga = du;
		this.id = id;
		this.idCenovnika = idCenovnika;
		this.cena = cena;
		if(id >= ID_GENERATOR) ID_GENERATOR = id+1;
	}
	public Stavka(DodatnaUsluga du, int idCenovnika, float cena) {
		this.dodatnaUsluga = du;
		this.id = ID_GENERATOR++;
		this.idCenovnika = idCenovnika;
		this.cena = cena;
	}
	
	//geteri, seteri
	public int getId() {
		return this.id;
	}
	public void setDodatnuUslugu(DodatnaUsluga t) {
		this.dodatnaUsluga = t;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}
	public float getCena() {
		return this.cena;
	}
	public DodatnaUsluga getDodatnaUsluga() {
		return this.dodatnaUsluga;
	}
	public void setIdCenovnika(int idCenovnika) {
		this.idCenovnika = idCenovnika;
	}
	public int getCenovnik() {
		return this.idCenovnika;
	}
	public String toFileString() {
		return id + "," + dodatnaUsluga.getId() + "," + idCenovnika + "," + cena;
	}
	@Override
	public String toString() {
		return "Stavka [dodatnaUsluga=" + dodatnaUsluga + ", cena=" + cena + "]";
	}
}
