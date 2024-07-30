package filter;

import java.awt.print.Printable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthScrollPaneUI;

import entity.Gost;
import entity.Recepcioner;
import entity.Rezervacija;
import entity.Soba;
import entity.Sobarica;
import entity.TipSobe;
import entity.Zaposleni;
import enums.StatusRezervacije;
import enums.StatusSobe;
import managerKlase.ManagerFactory;

public class Filter {
	private ManagerFactory mf;
	public Filter(ManagerFactory mf) {
		this.mf = mf;
	}
	public List<Rezervacija> getRezervacijePoStatusu(StatusRezervacije status){
		List<Rezervacija> ret = new ArrayList<Rezervacija>();
		for(Gost g: mf.getMG().getGosti()) {
			for (Rezervacija r : g.getRezervacije()) {
				if(r.getStatus() == status)
					ret.add(r);
			}
		}
		return ret;
	}
	public List<Rezervacija> getRezervacijeGosta(Gost g){	//sve rezervacije gosta koje su potvrđene i bez sobe
		List<Rezervacija> ret = new ArrayList<Rezervacija>();
		for(Rezervacija r : g.getRezervacije()) {
			if(r.getStatus() == StatusRezervacije.POTVRDJENA && r.getSoba() == null) 
				ret.add(r);
		}
		return ret;
	}
	
	public List<String> getStringRezervacijeNaCekanju(){
		List<String> ret = new ArrayList<String>();
		for(Gost g: mf.getMG().getGosti()) {
			for (Rezervacija r : g.getRezervacije()) {
				if(r.getStatus() == StatusRezervacije.NACEKANJU)
					ret.add(r.smallString()+" "+g.getKorisnickoIme());
			}
		}
		return ret;
	}
	public List<Rezervacija> getRezervacijePoStatusu(List<Rezervacija> rezervacije, StatusRezervacije status){
		List<Rezervacija> ret = new ArrayList<Rezervacija>();
		for (Rezervacija r : rezervacije) {
			if(r.getStatus() == status)
				ret.add(r);
		}
		
		return ret;
	}
	
	public List<Soba> getDostupneSobe(TipSobe tipSobe, LocalDate datumDolaska, LocalDate datumOdlaska){
		List<Soba> ret = new ArrayList<Soba>();
		for(Soba s: mf.getMSoba().getSobe()) {
			if (!s.getTipSobe().equals(tipSobe)) continue;
			//if (s.getStatus() == StatusSobe.ZAUZETA) continue;
			//odgovarajuci tip sobe
			boolean moze = true;
			for(Rezervacija r: mf.getMRez().getRezervacije()) {
				//Soba zauzetaSoba = r.getSoba();
				if(r.getStatus() != StatusRezervacije.POTVRDJENA) continue;	//ne vredi mi gledat rez koje nisu potvrdene
				
				if (r.getDatumDolaska().isBefore(datumOdlaska) && r.getDatumDolaska().isAfter(datumDolaska)) {
					moze = false;
					break;
				}
				if (r.getDatumOdlaska().isBefore(datumOdlaska) && r.getDatumOdlaska().isAfter(datumDolaska)) {
					//Datum odlaska date rezervacije SECE moj interval datumDolaska, datumOdlaska
					moze = false;
					break;
				}
				//}
				if(!moze) break;	//nema sta vise da trazim, jedna rezervacija sece, dovoljno za neuspeh
			}
			if(moze) {
				//System.out.println("Dodajem sobu: "+s);
				ret.add(s);
			}
			System.out.println("\n\n");
		}
		return ret;
	}
	public List<Soba> getSlobodneSobe() {
		List<Soba> ret = new ArrayList<Soba>();
        for(Soba s: mf.getMSoba().getSobe()) {
            if(s.getStatus() != StatusSobe.ZAUZETA)
                ret.add(s);
        }
        return ret;
	}
	public List<Rezervacija> getRezervacijeZaOtkaz(Gost g){
		ArrayList<Rezervacija> ret = new ArrayList<Rezervacija>();
		for (Rezervacija r : g.getRezervacije()) {
			if ((r.getStatus() == StatusRezervacije.POTVRDJENA && r.getSoba() == null) || r.getStatus() == StatusRezervacije.NACEKANJU) {
				ret.add(r);
			}
		}
		return ret;
	}
	public List<Zaposleni> getZaposleni(){
		List<Zaposleni> ret = new ArrayList<Zaposleni>();
		for (Sobarica s : mf.getMS().getSobarice()) 
			ret.add(s);
		for (Recepcioner r : mf.getMR().getRecepcioneri()) 
			ret.add(r);
		return ret;
	}

	public boolean postojiKorisnik(String korisnickoIme) {
		for (Zaposleni z : getZaposleni()) {
			if (z.getKorisnickoIme().equals(korisnickoIme))
				return true;
		}
		for (Gost g : mf.getMG().getGosti()) {
			if (g.getKorisnickoIme().equals(korisnickoIme))
				return true;
		}
		return false;
	}
	public float getPrihodi(YearMonth month) {
		float ret = 0;
		for (Rezervacija r : mf.getMRez().getRezervacije()) {
			if (r.getStatus() == StatusRezervacije.ZAVRSENA) {
				ret += r.getCena();
			}
		}
		return ret;
	}
}
