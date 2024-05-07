package managerKlase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Rezervacija;
import entity.Soba;
import enums.StatusSobe;

public class ManagerSoba {
	List<Soba> lista;

	public ManagerSoba() {
		lista = new ArrayList<Soba>();
	}
	public void dodajSobu(int id, int brojKreveta) {
		lista.add(new Soba(id, brojKreveta));
	}
	public void dodajSobu(int id, int brojKreveta, String raspored) {
		lista.add(new Soba(id, brojKreveta, raspored));
	}
	public void dodajSobu(int id, int tipSobe, String raspored, StatusSobe status) {
		lista.add(new Soba(id, tipSobe, raspored, status));
	}

	public Soba getSobaById(int id) {
		for (Soba s : lista) {
			if (s.getId() == id) {
				return s;
			}
		}
		return null;
	}

	public void ispisiSobe() {
		System.out.println("Sve sobe u sistemu su: ");
		for (Soba s : lista) {
			System.out.println(s);
		}
	}
	
	public void prikaziDostupneSobe(LocalDate d1, LocalDate d2, ManagerRezervacija mr) {
		ArrayList<Soba> dostupneSobe = new ArrayList<>();
		for (Soba s : lista) {
			boolean moze = true;
			for (Rezervacija r : mr.getRezervacije()) {
				if(r.getDatumDolaska().isAfter(d1) && r.getDatumDolaska().isBefore(d2)) {
					moze = false;
					break;
				}
				if(r.getDatumOdlaska().isAfter(d1) && r.getDatumOdlaska().isBefore(d2)) {
					moze = false;
					break;
				}
			}
			if(moze) {
				dostupneSobe.add(s);
			}	
		}
		Collections.sort(dostupneSobe);
		System.out.println("Dostupne sobe su: ");
		for (Soba s : dostupneSobe) {
			System.out.println(s);
		}
	}
}
