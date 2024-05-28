package managerKlase;
import entity.DodatnaUsluga;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ManagerUsluga {
	private List<DodatnaUsluga> usluge;
	private String filePath = "data/usluge.csv";
	
	public ManagerUsluga() {
		usluge = new ArrayList<DodatnaUsluga>();
	}

	public void dodajUslugu(DodatnaUsluga du) {
		usluge.add(du);
	}
	public List<DodatnaUsluga> getUsluge() {
		return usluge;
	}

	public DodatnaUsluga getUslugaById(int id) {
		for (DodatnaUsluga du : usluge) {
			if (du.getId() == id) {
				return du;
			}
		}
		return null;
	}
	public void ukloniUslugu(int id) {
		for (DodatnaUsluga du : usluge) {
			if (du.getId() == id) {
				usluge.remove(du);
				break;
			}
		}
	}
	public void ukloniUslugu(String naziv) {
		System.out.println("Uklanjam uslugu po nazivu");
		for (DodatnaUsluga du : usluge) {
			System.out.println(du.getNaziv());
			if (naziv.equals(du.getNaziv())) {
				usluge.remove(du);
				System.out.println("Uklonjeno");
				break;
			}
		}
		System.out.println("Odradio sam posao");
	}
	public void ukloniUslugu(DodatnaUsluga du) {
		for (DodatnaUsluga du1 : usluge) {
			if (du1.equals(du)) {
				usluge.remove(du);
			}
		}
	}
	public boolean loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				usluge.add(new DodatnaUsluga(Integer.parseInt(tokeni[0]), tokeni[1], Double.parseDouble(tokeni[2])));
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
			for (DodatnaUsluga u: usluge){
				pw.println(u.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
