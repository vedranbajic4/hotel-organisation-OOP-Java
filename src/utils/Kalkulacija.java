package utils;

import entity.Rezervacija;
import entity.Stavka;

import java.time.LocalDate;
import java.util.List;

import entity.Cenovnik;
import entity.DodatnaUsluga;
import managerKlase.ManagerCenovnik;

public class Kalkulacija {
	ManagerCenovnik mc;
	
	public Kalkulacija(ManagerCenovnik mc) {
		this.mc = mc;
	}
	public void izracunajCenu(Rezervacija r) {
		//System.out.println("Pre samog pocetka");
		float cena = 0;
		List<Cenovnik> cenovnici = mc.getCenovnikByTipSobe(r.getTipSobe());
		LocalDate d1 = r.getDatumDolaska();
		LocalDate d2 = r.getDatumOdlaska();
		int idCenovika=0;
		Cenovnik mojc = null;
		//System.out.println(d2);
		for (LocalDate danDate = d1; !danDate.isAfter(d2); danDate = danDate.plusDays(1)) {
			//imam dan: danDate
			//prolazim kroz cenovnike i pronadjem idCenovnika koji odgovara danu
			//!date.isAfter(endDate) je <= , posto je isBefore strogo before
			//System.out.println("Dan: " + danDate);
			for (DodatnaUsluga du : r.getUsluge()) {
				mojc = null;
				for (Cenovnik c : cenovnici) {
					if (!mc.dodatnaUslugaNaCenovniku(du.getId(), c.getId())) continue;
					if (!c.getDatumPocetak().isAfter(danDate)) {
						if(c.getDatumKraj() == null){
							if(mojc == null) {
								mojc = c;
							}
							else if (c.getDatumPocetak().isAfter(mojc.getDatumPocetak())) {	//trazim najblizi, poslendji koji je krenuo da vazi
								mojc = c;
							}
						}
						else if(!c.getDatumKraj().isBefore(danDate)) {
							if(mojc == null) {
								mojc = c;
							}
							else if (c.getDatumPocetak().isAfter(mojc.getDatumPocetak())) {	//trazim najblizi, poslendji koji je krenuo da vazi
								mojc = c;
							}
						}
					}
				}
				cena += mc.getCena(du.getId(), mojc.getId());
				//System.out.println(" += " +  mc.getCena(du.getId(), mojc.getId()));
			}
			//onda za taj idCenovnika uzmem sve stavke i sabiram na cenu	
		}
		//valjda sam dobro nasao mojc		
		r.setCena(cena);
	}
}
