package managerKlase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Soba;
import entity.Sobarica;
import enums.Pol;
import enums.StrucnaSprema;

public class ManagerSobarica {
	List<Sobarica> lista;
	private String filePath = "data/sobarice.csv";
	
	public ManagerSobarica() {
		lista = new ArrayList<Sobarica>();
	}
	// dodavanje sobarice
	public void dodajSobaricu(int id, String ime, String prezime) {
		lista.add(new Sobarica(id, ime, prezime));
	}
	public void dodajSobaricu(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		lista.add(new Sobarica(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka));
	}
	public void dodajSobaricu(int id, String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		lista.add(new Sobarica(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema));
	}
	public void dodajSobaricu(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, int plata, int staz, StrucnaSprema strucnaSprema) {
		lista.add(new Sobarica(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, plata, staz, strucnaSprema));
	}
	
	public Sobarica getSobaricaById(int id) {
		for (Sobarica s : lista) {
			if (s.getId() == id) {
				return s;
			}
		}
		return null;
	}

	public List<Sobarica> getSobarice() {
		return lista;
	}
	//uklanjanje sobarice
	public void ukloniSobaricu(int id) {
		Sobarica s = getSobaricaById(id);
		if (s != null) {
			lista.remove(s);
		}
	}
	//prikaz svih sobarica
	public void prikaziSveSobarice() {
		System.out.println("Sve sobarice u sistemu su: ");
        for (Sobarica s : lista) {
            System.out.println(s);
        }
	}
	public Sobarica postojiKorisnik(String korisnickoIme, String lozinka) {
		for (Sobarica s : lista) {
			if (s.postojiKorisnik(korisnickoIme, lozinka)) {
				return s;
			}
		}
		return null;
	}
	public Sobarica getSobaricaByKorisnickoIme(String korisnickoIme) {
		for (Sobarica s : lista) {
			if (s.getKorisnickoIme().equals(korisnickoIme)) {
				return s;
			}
		}
		return null;
	}
	public void dodeliSobu(Soba s) {
		int minSoba = 100000;
		Sobarica sobarica = null;
		for (Sobarica sb : lista) {
			if (sb.brojSobaZaSpremanje() < minSoba) {
				minSoba = sb.brojSobaZaSpremanje();
				sobarica = sb;
				//System.out.println("minsoba == " + minSoba);
			}
		}
		//System.out.println("dodeljeno sobarici == " + sobarica);
		sobarica.dodajSobu(s);
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
				lista.add(new Sobarica(Integer.parseInt(tokeni[0]), tokeni[1], tokeni[2], pol1, datum1, tokeni[5], tokeni[6], tokeni[7], tokeni[8]));
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
			for (Sobarica s: lista) {
				pw.println(s.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
