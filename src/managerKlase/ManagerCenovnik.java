package managerKlase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ListModel;

import entity.Cenovnik;
import entity.DodatnaUsluga;
import entity.Stavka;
import entity.TipSobe;

public class ManagerCenovnik{
	private List<Stavka> stavke;
	private HashMap<TipSobe, ArrayList<Cenovnik>> cenovnici;
	private String filePath1 = "data/cenovnici.csv";
	private String filePath2 = "data/stavke.csv";
	
	public ManagerCenovnik() {
		stavke = new ArrayList<Stavka>();
		cenovnici = new HashMap<TipSobe, ArrayList<Cenovnik>>();
	}

	public void dodajStavku(Stavka s) {
		stavke.add(s);
	}
	public void dodajStavku(int id, DodatnaUsluga du) {
		stavke.add(new Stavka(id, du));
	}
	
	//kreiranje cenovnika
	public void kreirajCenovnik(TipSobe q, Cenovnik c){
		if(!cenovnici.containsKey(q)) {
			cenovnici.put(q, new ArrayList<Cenovnik>());
		}
		cenovnici.get(q).add(c);
	}
	public void kreirajCenovnik(int id, TipSobe q, LocalDate poc) {
		if(!cenovnici.containsKey(q)) {
			cenovnici.put(q, new ArrayList<Cenovnik>());
		}		
		cenovnici.get(q).add(new Cenovnik(id, poc));
	}
	public void kreirajCenovnik(int id, TipSobe q, LocalDate poc, LocalDate kraj) {
		if(!cenovnici.containsKey(q)) {
			cenovnici.put(q, new ArrayList<Cenovnik>());
		}
		cenovnici.get(q).add(new Cenovnik(id, poc, kraj));
	}
	public void kreirajCenovnik(List<TipSobe> tipovi, Cenovnik c) {
		for (TipSobe t : tipovi) {
			if(!cenovnici.containsKey(t)) {
				cenovnici.put(t, new ArrayList<Cenovnik>());
			}
			cenovnici.get(t).add(c);
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
	public boolean dodatnaUslugaNaCenovniku(int iddu, int idc) {
		for (Stavka s : stavke) {
			if (s.getDodatnaUsluga().getId() == iddu && s.getCenovnik() == idc) {
				return true;
			}
		}
		return false;
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
		for (ArrayList<Cenovnik> lista : cenovnici.values()) {
			for (Cenovnik c : lista) {
				if (c.getId() == idCenovnika) {
					return c;
				}
			}
		}
		return null;
	}

	public List<Cenovnik> getCenovnikByTipSobe(TipSobe q) {
		return cenovnici.get(q);
	}
	
	public List<Stavka> getStavkeByCenovnik(int id){
		List<Stavka> ret = new ArrayList<Stavka>();
		for (Stavka s : stavke) {
			if (s.getCenovnik() == id) {
				ret.add(s);
			}
		}
		return ret;
	}
	public float getCena(int idDodatneUsluge, int idCenovnika) {
		for (Stavka s : stavke) {
			if (s.getDodatnaUsluga().getId() == idDodatneUsluge && s.getCenovnik() == idCenovnika) {
				return s.getCena();
			}
		}
		return -1;
	}
	
	
	//seter za cenu stavke
	public void setCenaForStavka(int idStavke, int idCenovnika, float cena) {
		for (Stavka s : stavke) {
			if (s.getId() == idStavke && s.getCenovnik() == idCenovnika) {
				s.setCena(cena);
			}
		}	
	}
	public boolean loadData(ManagerTipSobe mts, ManagerUsluga mdu) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath1));
			String linija = null;
			TipSobe q = null;
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				q = mts.getTipSobe(Integer.parseInt(tokeni[0]), tokeni[1]);
				
				LocalDate kraj = null;
				if(!tokeni[4].equals("null")) kraj = LocalDate.parse(tokeni[4]);
				
				if(!cenovnici.containsKey(q)) {
					cenovnici.put(q, new ArrayList<Cenovnik>());
				}
				cenovnici.get(q).add(new Cenovnik(Integer.parseInt(tokeni[2]), LocalDate.parse(tokeni[3]), kraj));
			}
			br.close();
			br = new BufferedReader(new FileReader(filePath2));
			DodatnaUsluga du = null;
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				du = mdu.getUslugaById(Integer.parseInt(tokeni[1]));
				stavke.add(new Stavka(Integer.parseInt(tokeni[0]), du, Integer.parseInt(tokeni[2]), Float.parseFloat(tokeni[3])));
			}
			
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean saveData() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filePath1, false));
			String ispis = null;
			for (HashMap.Entry<TipSobe, ArrayList<Cenovnik>> entry : cenovnici.entrySet()) {
				ispis = entry.getKey().toFileString();
				for (Cenovnik c : entry.getValue()) {
					pw.println(ispis + "," + c.toFileString());
				}
			}
			pw.close();
			pw = new PrintWriter(new FileWriter(filePath2, false));
			for (Stavka s : stavke) {
				//System.out.println("stavkice");
				pw.println(s.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
