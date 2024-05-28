package managerKlase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import entity.Soba;
import entity.TipSobe;
import enums.StatusSobe;

public class ManagerTipSobe {
	private List<TipSobe> lista;
	private String filePath = "data/tipovi_soba.csv";
	
	public ManagerTipSobe() {
		lista = new ArrayList<TipSobe>();
	}

	public void dodajTipSobe(TipSobe ts) {
		lista.add(ts);
	}
	public List<TipSobe> getTipoviSoba() {
		return lista;
	}

	public void prikaziTipoveSoba() {
		System.out.println("Tipovi soba u sistemu su: ");
		for (TipSobe ts : lista) {
			System.out.println(ts);
		}
	}
	public TipSobe getTipSobe(int brK, String raspored) {
		for (TipSobe ts : lista) {
			if (ts.getBrojKreveta() == brK && ts.getRaspored().equals(raspored)) {
				return ts;
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
				lista.add(new TipSobe(Integer.parseInt(tokeni[0]), tokeni[1]));
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
			for (TipSobe ts: lista) {
				pw.println(ts.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
