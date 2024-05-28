package managerKlase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Recepcioner;
import enums.Pol;

public class ManagerRecepcioner {
	ArrayList<Recepcioner> lista;
	private String filePath = "data/recepcioneri.csv";
	
	public ManagerRecepcioner() {
		lista = new ArrayList<Recepcioner>();
	}
	// dodavanje recepcionera
	public void dodajRecepcionera(int id, String ime, String prezime) {
		lista.add(new Recepcioner(id, ime, prezime));
	}
	public void dodajRecepcionera(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Recepcioner(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}

	public Recepcioner getRecepcionerById(int id) {
		for (Recepcioner r : lista) {
			if (r.getId() == id) {
				return r;
			}
		}
		return null;
	}
	//uklanjanje recepcionera
	public void ukloniRecepcionera(int id) {
		Recepcioner r = getRecepcionerById(id);
		if (r != null) {
			lista.remove(r);
		}
	}
	public void prikaziSveRecepcionere() {
		System.out.println("Svi recepcioneri u sistemu su: ");
		for (Recepcioner r : lista) {
			System.out.println(r);
		}
	}
	public Recepcioner postojiKorisnik(String korisnickoIme, String lozinka) {
		for (Recepcioner r : lista) {
			if (r.postojiKorisnik(korisnickoIme, lozinka)) {
				return r;
			}
		}
		return null;
	}
	public boolean loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				Pol pol1 = null;
				LocalDate datum1 = null;
				if(!tokeni[3].equals("null")) pol1 = Pol.valueOf(tokeni[3]);
				if(!tokeni[4].equals("null")) {
					String[] brojS = tokeni[4].split("-");
					datum1 = LocalDate.of(Integer.parseInt(brojS[0]), Integer.parseInt(brojS[1]), Integer.parseInt(brojS[2]));
				}
				
				lista.add(new Recepcioner(Integer.parseInt(tokeni[0]), tokeni[1], tokeni[2], pol1, datum1, tokeni[5], tokeni[6], tokeni[7], tokeni[8]));
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
			for (Recepcioner r: lista) {
				pw.println(r.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
