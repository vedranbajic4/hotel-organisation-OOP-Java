package managerKlase;
import entity.Rezervacija;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerRezervacija {
	private List<Rezervacija> rezervacije;
	
	public ManagerRezervacija() {
		rezervacije = new ArrayList<Rezervacija>();
	}

	public void dodajRezervaciju(Rezervacija r) {
		rezervacije.add(r);
	}
	public void dodajRezervaciju(int id, int tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska) {
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska));
	}
	public void dodajRezervaciju(int id, int tipSobe, LocalDate datumDolaska) {
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska));
	}
	public void dodajRezervaciju(int id, int tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<String> usluge) {
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska, usluge));
	}
	public void dodajRezervaciju(int id, int tipSobe, LocalDate datumDolaska, ArrayList<String> usluge) {
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, usluge));
	}
	public void dodajRezervaciju(int id, int tipSobe, String raspored, LocalDate datumDolaska, LocalDate datumOdlaska) {
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska, datumOdlaska));
	}
	public void dodajRezervaciju(int id, int tipSobe, String raspored, LocalDate datumDolaska) {
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska));
	}
	public void dodajRezervaciju(int id, int tipSobe, String raspored, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<String> usluge) {
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska, datumOdlaska, usluge));
	}
	public void dodajRezervaciju(int id, int tipSobe, String raspored, LocalDate datumDolaska, ArrayList<String> usluge) {
		rezervacije.add(new Rezervacija(id, tipSobe, raspored, datumDolaska, usluge));
	}
	
	public Rezervacija getRezervacijaById(int id) {
		for (Rezervacija r : rezervacije) {
			if (r.getId() == id) {
				return r;
			}
		}
		return null;
	}

	public List<Rezervacija> getRezervacije() {
		return rezervacije;
	}
}
