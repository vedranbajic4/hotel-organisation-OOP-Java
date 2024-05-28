package entity;

public class Stavka {
	private int idCenovnika;
	private DodatnaUsluga dodatnaUsluga;
	private int id;
	
	//konstruktori
	public Stavka() {}
	public Stavka(int id, String naziv) {
		this.dodatnaUsluga = new DodatnaUsluga(naziv);
		this.id = id;
	}
	public Stavka(int id, DodatnaUsluga du) {
		this.dodatnaUsluga = du;
		this.id = id;
	}
	public Stavka(int id, DodatnaUsluga du, int idCenovnika) {
		this.dodatnaUsluga = du;
		this.id = id;
		this.idCenovnika = idCenovnika;
	}
	//geteri, seteri
	public int getId() {
		return this.id;
	}
	public void setDodatnuUslugu(DodatnaUsluga t) {
		this.dodatnaUsluga = t;
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
		return id + "," + dodatnaUsluga.getId() + "," + idCenovnika;
	}
}
