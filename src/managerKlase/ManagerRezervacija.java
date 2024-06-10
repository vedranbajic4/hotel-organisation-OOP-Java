package managerKlase;
import entity.DodatnaUsluga;
import entity.Gost;
import entity.Rezervacija;
import entity.Soba;
import entity.TipSobe;
import enums.StatusRezervacije;
import utils.Kalkulacija;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerRezervacija {
	private List<Rezervacija> rezervacije;
	private String filePath1 = "data/rezervacije.csv";
	private String filePath2 = "data/rez_usluga.csv";
	Kalkulacija kalk;
	
	public ManagerRezervacija(ManagerCenovnik mc) {
		rezervacije = new ArrayList<Rezervacija>();
		kalk = new Kalkulacija(mc);
	}
	
	public void dodajRezervaciju(Rezervacija r) {
		rezervacije.add(r);
		kalk.izracunajCenu(r);
	}
	public void dodajRezervaciju(TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska) {
		rezervacije.add(new Rezervacija(tipSobe, datumDolaska, datumOdlaska));
		kalk.izracunajCenu(rezervacije.get(rezervacije.size()-1));
	}
	public void dodajRezervaciju(int id, TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<DodatnaUsluga> usluge) {
		rezervacije.add(new Rezervacija(id, tipSobe, datumDolaska, datumOdlaska, usluge));
		kalk.izracunajCenu(rezervacije.get(rezervacije.size()-1));
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
	
	public boolean loadData(ManagerSoba ms, ManagerTipSobe mts, ManagerUsluga mu, ManagerGost mg) {
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
						soba1, LocalDate.parse(tokeni[5]), LocalDate.parse(tokeni[6]), Float.parseFloat(tokeni[7])));
				int idGosta = Integer.parseInt(tokeni[8]);
				mg.getGostById(idGosta).dodajRezervaciju(rezervacije.get(rezervacije.size()-1));
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
	
	public boolean saveData(ManagerGost mg) {
		PrintWriter pw1 = null, pw2 = null;
		int idGosta=0;
		List<Gost> gosti = mg.getGosti();
		List<Rezervacija> rezGosta = null;
		try {
			pw1 = new PrintWriter(new FileWriter(filePath1, false));
			pw2 = new PrintWriter(new FileWriter(filePath2, false));
			for (Rezervacija r: rezervacije){
				idGosta = -1;
				for(Gost g: gosti) {	//prolazim kroz sve goste
					rezGosta = g.getRezervacije();
					for(Rezervacija r2: rezGosta) {		//i kroz njegove rezrvacije, trazim r.id
						if(r2.getId() == r.getId()) {
							idGosta = g.getId();
							break;
						}
					}
					if(idGosta != -1) break;
				}
				pw1.println(r.toFileString()+","+idGosta);
				
				if(r.getUsluge() != null) {
					for (DodatnaUsluga du : r.getUsluge()) {
						pw2.println(r.getId() + "," + du.getId());
					}
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
