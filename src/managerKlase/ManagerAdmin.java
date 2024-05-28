package managerKlase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Administrator;
import enums.Pol;

public class ManagerAdmin {
	List<Administrator> lista;
	private String filePath = "data/administratori.csv";
	
	public ManagerAdmin() {
		lista = new ArrayList<Administrator>();
	}
	// dodavanje Administratora
	public void dodajAdmina(int id, String ime, String prezime) {
		lista.add(new Administrator(id, ime, prezime));
	}
	public void dodajAdmina(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Administrator(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}
	public void ukloniAdmina(int id) {
		for(Administrator a : lista) {
			if(a.getId() == id) {
				lista.remove(a);
				return;
			}
		}
	}

	public Administrator postojiKorisnik(String korisnickoIme, String lozinka) {
		for (Administrator a : lista) {
			if (a.postojiKorisnik(korisnickoIme, lozinka)) {
				return a;
			}
		}
		return null;
	}
	public Administrator getAdminById(int id) {
		for (Administrator a : lista) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}
	public void prikaziSveAdmine() {
		System.out.println("Svi admini u sistemu su: ");
		for (Administrator a : lista) {
			System.out.println(a);
		}
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
				
				lista.add(new Administrator(Integer.parseInt(tokeni[0]), tokeni[1], tokeni[2], pol1, datum1, tokeni[5], tokeni[6], tokeni[7], tokeni[8]));
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
			for (Administrator admin : lista) {
				pw.println(admin.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
