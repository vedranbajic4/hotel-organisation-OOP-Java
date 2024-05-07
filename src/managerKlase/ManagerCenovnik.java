package managerKlase;

import java.nio.file.StandardWatchEventKinds;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Cenovnik;
import entity.Stavka;

public class ManagerCenovnik{
	private List<Stavka> stavke;
	private List<Cenovnik>[] cenovnici = new ArrayList[50];
	private int brSoba;
	
	public ManagerCenovnik(int brSoba) {
		this.brSoba = brSoba;
		stavke = new ArrayList<Stavka>();
		for(int i = 1; i <= brSoba; i++) {
			cenovnici[i] = new ArrayList<Cenovnik>();
		}
	}

	public void dodajStavku(Stavka s) {
		stavke.add(s);
	}
	public void dodajStavku(int id, String naziv, int idCenovnika, double cena) {
		stavke.add(new Stavka(id, naziv, idCenovnika, cena));
	}
	public void dodajStavku(int id, String naziv, double cena) {
		stavke.add(new Stavka(id, naziv, cena));
	}
	//kreiranje cenovnika
	public void kreirajCenovnik(int q, Cenovnik c){
		cenovnici[q].add(c);
	}
	public void kreirajCenovnik(int q, int id, LocalDate poc) {
		cenovnici[q].add(new Cenovnik(id, poc));
	}
	public void kreirajCenovnik(int q, int id, LocalDate poc, LocalDate kraj) {
		cenovnici[q].add(new Cenovnik(id, poc, kraj));
	}
	//kreiranje cenovnika koji vazi za sve tipove sobe
	public void kreirajCenovnik(Cenovnik c){
		for(int i = 1; i <= brSoba; i++) {
			cenovnici[i].add(c);
		}
	}
	public void kreirajCenovnik(int id, LocalDate poc) {
		for(int i = 1; i <= brSoba; i++) {
			cenovnici[i].add(new Cenovnik(id, poc));
		}
	}
	public void kreirajCenovnik(int id, LocalDate poc, LocalDate kraj) {
		for (int i = 1; i <= brSoba; i++) {
			cenovnici[i].add(new Cenovnik(id, poc, kraj));
		}
	}
    //izmena cenovnika
	public void ukloniStavku(int id) {
		for (Stavka s : stavke) {
			if (s.getId() == id) {
				stavke.remove(s);
				break;
			}
		}
	}
	public void dodajStavkuNaCenovnik(int idCenovnika, int idStavke) {
		for(Stavka s : stavke) {
			if (s.getId() == idStavke) {
				s.setIdCenovnika(idCenovnika);
				break;
			}
		}
	}
	//geter za cenovnik
	public Cenovnik getCenovnikById(int idCenovnika) {
		for (int i = 1; i <= brSoba; i++) {
			for (Cenovnik c : cenovnici[i]) {
				if (c.getId() == idCenovnika) {
					return c;
				}
			}
		}
		return null;
	}
	//seter za cenu stavke
	public void setCenaForStavka(int idStavke, int idCenovnika, double cena) {
		for (Stavka s : stavke) {
			if (s.getId() == idStavke && s.getCenovnik() == idCenovnika) {
				s.setCena(cena);
				//break; 	nema break jer moze da se desi da se stavka nalazi u vise cenovnika
			}
		}
		
	}
	
	
}
