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
import entity.Gost;
import entity.Recepcioner;
import enums.Pol;
import enums.StrucnaSprema;

public class ManagerGost {
	List<Gost> lista;
	private String filePath = "data/gosti.csv";
	
	public ManagerGost() {
		lista = new ArrayList<Gost>();
	}
	// dodavanje gosta
	public void dodajGosta(int id, String ime, String prezime) {
		lista.add(new Gost(id, ime, prezime));
	}
	public void dodajGosta(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Gost(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}

	public Gost getGostById(int id) {
		for (Gost g : lista) {
			if (g.getId() == id) {
				return g;
			}
		}
		return null;
	}
	public void prikaziSveGoste() {
		System.out.println("Svi gosti u sistemu su: ");
		for (Gost g : lista) {
			System.out.println(g);
		}
	}
	public Gost postojiKorisnik(String korisnickoIme, String lozinka) {
		for (Gost g : lista) {
			if (g.postojiKorisnik(korisnickoIme, lozinka)) {
				return g;
			}
		}
		return null;
	}
	public boolean loadData() {
		try {	//FALI REZERVACIJE ZA GOSTA
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
				
				lista.add(new Gost(Integer.parseInt(tokeni[0]), tokeni[1], tokeni[2], pol1, datum1, tokeni[5], tokeni[6], tokeni[7], tokeni[8]));
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
			for (Gost g: lista) {
				pw.println(g.toFileStringK());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
