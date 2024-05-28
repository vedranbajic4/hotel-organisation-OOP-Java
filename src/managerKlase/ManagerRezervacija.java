package managerKlase;
import entity.DodatnaUsluga;
import entity.Rezervacija;
import entity.Soba;
import entity.TipSobe;
import enums.StatusRezervacije;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManagerRezervacija {
	private List<Rezervacija> rezervacije;
	private String filePath1 = "data/rezervacije.csv";
	private String filePath2 = "data/rez_usluga.csv";
	
	public ManagerRezervacija() {
		rezervacije = new ArrayList<Rezervacija>();
	}

	public void dodajRezervaciju(Rezervacija r) {
		rezervacije.add(r);
	}
	public void dodajRezervaciju(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska) {
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska));
	}
	public void dodajRezervaciju(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<DodatnaUsluga> usluge) {
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska, usluge));
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
	
	public boolean loadData(ManagerSoba ms, ManagerTipSobe mts, ManagerUsluga mu) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath1));
			String linija = null;
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				Soba soba1 = null;
				if(Integer.parseInt(tokeni[4]) != -1) {
					soba1 = ms.getSobaById(Integer.parseInt(tokeni[4]));
				}
				rezervacije.add(new Rezervacija(Integer.parseInt(tokeni[0]), StatusRezervacije.valueOf(tokeni[1]),
						mts.getTipSobe(Integer.parseInt(tokeni[2]), tokeni[3]),
						soba1, LocalDate.parse(tokeni[5]), LocalDate.parse(tokeni[6])));
			}
			br.close();
			
			br = new BufferedReader(new FileReader(filePath2));
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				this.getRezervacijaById(Integer.parseInt(tokeni[0])).dodajUslugu(mu.getUslugaById(Integer.parseInt(tokeni[1])));
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean saveData() {
		PrintWriter pw1 = null, pw2 = null;
		try {
			pw1 = new PrintWriter(new FileWriter(filePath1, false));
			pw2 = new PrintWriter(new FileWriter(filePath2, false));
			for (Rezervacija r: rezervacije){
				pw1.println(r.toFileString());
				for (DodatnaUsluga du : r.getUsluge()) {
					pw2.println(r.getId() + "," + du.getId());
				}
			}
			
			pw1.close();
			pw2.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
