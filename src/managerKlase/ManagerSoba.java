package managerKlase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Rezervacija;
import entity.Soba;
import entity.TipSobe;
import enums.StatusSobe;

public class ManagerSoba {
	List<Soba> lista;
	private String filePath = "data/sobe.csv";
	
	public ManagerSoba() {
		lista = new ArrayList<Soba>();
	}
	public void dodajSobu(int id, TipSobe tipSobe) {
		lista.add(new Soba(id, tipSobe));
	}
	public void dodajSobu(int id, TipSobe tipSobe, int brojSobe) {
		lista.add(new Soba(id, tipSobe, brojSobe));
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
		//Collections.sort(dostupneSobe);
		System.out.println("Dostupne sobe su: ");
		for (Soba s : dostupneSobe) {
			System.out.println(s);
		}
	}
	public boolean loadData(ManagerTipSobe mts) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String linija = null;
			int brk = 0;
			String rasString = "";
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				StatusSobe status1 = null;
				if(!tokeni[4].equals("null")) status1 = StatusSobe.valueOf(tokeni[4]);
				
				brk = Integer.parseInt(tokeni[1]);
				rasString = tokeni[2];
				
				lista.add(new Soba(Integer.parseInt(tokeni[0]), mts.getTipSobe(brk, rasString), Integer.parseInt(tokeni[3]), status1));
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean saveData() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filePath, false));
			for (Soba s: lista) {
				pw.println(s.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
