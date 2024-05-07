package main;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Cenovnik;
import managerKlase.ManagerFactory;
import utils.Pregled;

public class Hotel {
	public static void main(String[] args) {
		System.out.println("Welcome to Hotel Management System");
		ManagerFactory mf = new ManagerFactory();
		Pregled pregled = new Pregled(mf);
		
		mf.getMA().dodajAdmina(1, "Pera", "Peric");
		mf.getMA().getAdminById(1).dodajRecepcionera(1, "Mika", "Mikic", mf.getMR());
		mf.getMA().getAdminById(1).dodajRecepcionera(2, "Nikola", "Nikolic", mf.getMR());
		mf.getMA().getAdminById(1).dodajSobaricu(1, "Jana", "Janic", mf.getMS());
		
		pregled.prikaziSveZaposlene();
		
		mf.getMA().getAdminById(1).ukloniRecepcionera(2, mf.getMR());
		
		mf.getMR().getRecepcionerById(1).dodajGosta(1, "Milica", "Milic", mf.getMG());
		mf.getMR().getRecepcionerById(1).dodajGosta(2, "Ana", "Anic", mf.getMG());
		
		
		//dodavanje soba
		mf.getMSoba().dodajSobu(1, 1);			//id = 1, brojKreveta = 1
		mf.getMSoba().dodajSobu(2, 2);			//id = 2, brojKreveta = 2
		mf.getMSoba().dodajSobu(3, 2, "1+1");	//id = 3, brojKreveta = 2, raspored = 1+1
		mf.getMSoba().dodajSobu(4, 3, "2+1");	//id = 4, brojKreveta = 3, raspored = 2+1
		mf.getMSoba().dodajSobu(5, 3);			//id = 5, brojKreveta = 3
		
		mf.getMSoba().getSobaById(2).setRaspored(3, "2+1");
		
		System.out.println("\n\n\n");
		
		
		//dodavanje usluga i pravljenje cenovnika
		mf.getMC().dodajStavku(1, "Dorucak", 500.0);
		mf.getMC().dodajStavku(2, "Rucak", 1000.0);
		mf.getMC().dodajStavku(3, "Vecera", 800.0);
		mf.getMC().dodajStavku(4, "Spa Centar", 1500.0);
		mf.getMC().dodajStavku(5, "Bazen", 300.0);
		
		mf.getMC().ukloniStavku(4);
		Cenovnik c = new Cenovnik(1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 13));
		mf.getMC().kreirajCenovnik(1, c);				//kreira cenovnik za sve tipove soba
		mf.getMC().setCenaForStavka(1, 1, 600.0);		//stavkaID, cenovnikID, cena
		
		
		
		mf.getMSoba().prikaziDostupneSobe(LocalDate.of(2024, 8, 13), LocalDate.of(2024, 8, 23), mf.getMRez());
		ArrayList <String> usluge = new ArrayList<String>();
		usluge.add("dorucak");
		usluge.add("vecera");
		mf.getMG().getGostById(1).zahtevRezervacije(1, 3, "2+1", LocalDate.of(2024, 8, 13), LocalDate.of(2024, 8, 23), usluge, mf.getMRez());
		
		
		
		mf.getMSoba().prikaziDostupneSobe(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), mf.getMRez());
		mf.getMG().getGostById(2).zahtevRezervacije(2, 2, "1+1", LocalDate.of(2024, 6, 6), LocalDate.of(2024, 6, 12), mf.getMRez());
		
		System.out.println("\n\n\n");
		mf.getMG().getGostById(1).ispisiRezervacije();
	}
}
