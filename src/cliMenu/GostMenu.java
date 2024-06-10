package cliMenu;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import entity.DodatnaUsluga;
import entity.Gost;
import entity.Rezervacija;
import entity.TipSobe;
import enums.StatusRezervacije;
import filter.Filter;
import managerKlase.ManagerFactory;
import utils.Kalkulacija;

public class GostMenu {
	private ManagerFactory mf;
	private Gost trenUser;
	private Filter filter;
	
	public GostMenu(ManagerFactory mf, Gost trenUser) {
		this.mf = mf;
		this.trenUser = trenUser;
		filter = new Filter(mf);
	}
	
	private void prikaziSveRezervacije() {
		System.out.println("  Prikaz svih rezervacija:");
		List<Rezervacija> rezervacije = trenUser.getRezervacije();
		for (Rezervacija r : rezervacije) {
			System.out.println(r.toString());
		}
	}
	private void rezervacijaSobe() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(" Rezeracija sobe");
		System.out.println(" Ukoliko zelite da prekinete proces, upisite -1");
		boolean prekid = false;
		LocalDate d1 = null, d2 = null;
		
		while(true) {
			System.out.print(" Unesite datum (yyyy-mm-dd) dolaska: ");
			String datumDolaska = scanner.nextLine();
			if(datumDolaska.equals("-1")) {
				System.out.println("  Prekid rezervacije\n");
				prekid = true;
				break;
			}
			try {
				d1 = LocalDate.parse(datumDolaska);
				break;
			}
			catch(Exception e){
				System.out.println("  Pogresan format datuma :(\n");
                continue;
			}
		}
		if(prekid) return;
		
		while(true) {
			System.out.print(" Unesite datum (yyyy-mm-dd) odlaska: ");
			String datumOdlaska = scanner.nextLine();
			if(datumOdlaska.equals("-1")) {
				System.out.println("  Prekid rezervacije\n");
				prekid = true;
				break;
			}
			try {
				d2 = LocalDate.parse(datumOdlaska);
				break;
			}
			catch(Exception e){
				System.out.println("  Pogresan format datuma :(\n");
                continue;
			}
		}
		if(prekid) return;
		
		System.out.println(" Dostupni tipovi sobe su: ");
		System.out.println(" broj kreveta, raspored");
		List<TipSobe> tipovi = mf.getMTS().getTipoviSoba();
		for (int i = 0; i < tipovi.size(); i++) {
			System.out.println("  " + i + " " + tipovi.get(i).getBrojKreveta() + ". " + tipovi.get(i).getRaspored());
		}
		
		int index = tipovi.size();
		while(index < -1 || index >= tipovi.size()) {
			System.out.print(" Izaberite index: ");
			index = scanner.nextInt();
			if(index == -1) {
				System.out.println("  Prekid rezervacije\n");
				return;
			}
			scanner.nextLine();//djubre
		}
		
		Rezervacija r = new Rezervacija(tipovi.get(index), d1, d2);
		mf.getMRez().dodajRezervaciju(r);
		System.out.print(" Uspesno ste napravili rezeraciju\n Zelite li dodatne usluge? (1/2)(da/ne)");
		HashMap<Integer, Integer> mapa = new HashMap<Integer, Integer>();
		mapa.put(0, 1);
		r.dodajUslugu(mf.getMU().getUsluge().get(0));	//nocenje
		String odgovor = scanner.nextLine();
		if(odgovor.equals("1")) {
			System.out.println("\n Dodatne usluge:");
			List<DodatnaUsluga> usluge = mf.getMU().getUsluge();
			System.out.println(" index, naziv");
			for (int i = 0; i < usluge.size(); i++) {
				if(i == 0) continue;
				System.out.println("  " + i + " " + usluge.get(i).getNaziv());
			}
			System.out.println(" Birajte indexom sta zelite (-1 za kraj)");
			
			while(true) {
				System.out.print("\n  index: ");
				index = scanner.nextInt();
				scanner.nextLine();
				if(index == -1) {
					System.out.println("  Zavrseno");
					break;
				}
				if(index < 0 || index >= usluge.size()) {
					System.out.println("  Pogresan index");
				}
				else {
					if(mapa.containsKey(index)) {
						System.out.println("  Ovo ste vec izabrali");
					}
					else {
						mapa.put(index, 1);
						r.dodajUslugu(usluge.get(index));
						System.out.println("  Usluga: " + usluge.get(index).getNaziv() + " uspesno dodata");
					}
				}
			}
			Kalkulacija kalk = new Kalkulacija(mf.getMC());
			kalk.izracunajCenu(r);
			System.out.println(" Rezervacija ce vas kostati: " + r.getUkupnaCena() + " dinara");
			System.out.println(" Gotovo, uspesno ste dodali i usluge! \n\n");	
		}
		else {
			Kalkulacija kalk = new Kalkulacija(mf.getMC());
			kalk.izracunajCenu(r);
			System.out.println(" Rezervacija ce vas kostati: " + r.getUkupnaCena() + " dinara");
			System.out.println(" Gotovo...\n\n");
		}
		
		trenUser.dodajRezervaciju(r);
	}
	private void izmeniDatumDolaska(Rezervacija r) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("   Unesite novi datum dolaska (yyyy-mm-dd): ");
			String datumDolaskaString = scanner.nextLine();
			if(datumDolaskaString.equals("-1")) {
                System.out.println("   Prekid izmene\n");
                return;
            }
			try {
				LocalDate datumDolaska = LocalDate.parse(datumDolaskaString);
				if (datumDolaska.isAfter(r.getDatumOdlaska())) {
					System.out.println("   Datum dolaska ne moze biti posle datuma odlaska");
					continue;
				}
				r.setDatumDolaska(datumDolaska);
				System.out.println("   Uspesno ste izmenili datum dolaska");
				return;
			}
			catch (Exception e) {
				System.out.println("   Pogresan format datuma");
				continue;
			}
		}
	}
	private void izmeniDatumOdlaska(Rezervacija r) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("   Unesite novi datum dolaska (yyyy-mm-dd): ");
			String datumOdlaskaString = scanner.nextLine();
			if(datumOdlaskaString.equals("-1")) {
                System.out.println("   Prekid izmene\n");
                return;
            }
			try {
				LocalDate datumOdlaska = LocalDate.parse(datumOdlaskaString);
				if (datumOdlaska.isBefore(r.getDatumDolaska())) {
					System.out.println("   Datum odlaska ne moze biti pre datuma dolaska");
					continue;
				}
				r.setDatumOdlaska(datumOdlaska);
				System.out.println("   Uspesno ste izmenili datum odlaska");
				return;
			}
			catch (Exception e) {
				System.out.println("   Pogresan format datuma");
				continue;
			}
		}

	}
	private void izmeniTipSobe(Rezervacija r) {
		System.out.println("  Prikaz svih tipova soba:");
		List<TipSobe> tipovi = mf.getMTS().getTipoviSoba();
		for (int i = 0; i < tipovi.size(); i++) {
			System.out.println(i + " " + tipovi.get(i).toString());
		}
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("  Unesite index novog tipa sobe: ");
			int index = scanner.nextInt();
			scanner.nextLine();
			if (index == -1) {
				System.out.println("  Prekid izmene\n");
				return;
			}
			if (index < 0 || index >= tipovi.size()) {
				System.out.println("  Pogresan index\n");
				return;
			}
			r.setTipSobe(tipovi.get(index));
			System.out.println("  Uspesno ste izmenili tip sobe");
			
			return;
		}
	}
	private void izmeniUsluge(Rezervacija r) {
		List<DodatnaUsluga> usluge = r.getUsluge();
		if (usluge.size() == 0) {
			System.out.println("  Nema dodatnih usluga za izmenu");
			return;
		}
		System.out.println("  Ukoliko zelite da dodate usluge, to uradite na recepciji kada dodjete");
		Kalkulacija kalk = new Kalkulacija(mf.getMC());
		
		while(true) {
			System.out.println("\n  Izmena usluga (brisanje)");
			for (int i = 0; i < usluge.size(); i++) {
				if(i == 0) continue;
				System.out.println("  " + i + " " + usluge.get(i).getNaziv());
			}
			Scanner scanner = new Scanner(System.in);
			System.out.print("  Unesite index usluge koju zelite da obrisete: ");
			int index = scanner.nextInt();
			scanner.nextLine();
			if (index == -1) {
				System.out.println("  Prekid izmene usluga\n");
				//r.setUsluge(usluge);
				kalk.izracunajCenu(r);
				return;
			}
			if (index < 0 || index >= usluge.size()) {
				System.out.println("  Pogresan index\n");
				continue;
			}
			usluge.remove(index);
			System.out.println("  Usluga uspesno obrisana");
		}
	}
	private void izmenaRezervacije() {
		System.out.println("  Izmena rezervacije");
		List<Rezervacija> rezervacije = filter.getRezervacijePoStatusu(trenUser.getRezervacije(), StatusRezervacije.NACEKANJU);
		for (int i = 0; i < rezervacije.size(); i++) {
			System.out.println(" " + i + " " + rezervacije.get(i).toString());
		}
		Scanner scanner = new Scanner(System.in);
		Rezervacija r = null;
		boolean prekid = false;
		
		while(true) {
			System.out.print("\n Unesite index rezervacije koju zelite da izmenite: ");
			int index = scanner.nextInt();
			scanner.nextLine();
			if (index == -1) {
				System.out.println("  Prekid izmene rezervacije\n");
				prekid = true;
				break;
			}
			if(index < 0 || index >= rezervacije.size()) {
				System.out.println(" Pogresan index");
				continue;
			}
			r = rezervacije.get(index);
			break;
		}
		if(prekid) return;
		String opcija = "";
		while(true) {
			System.out.println("\n  Izaberite sta zelite da izmenite: ");
			System.out.println("  1. Datum dolaska");
			System.out.println("  2. Datum odlaska");
			System.out.println("  3. Tip sobe");
			System.out.println("  4. Dodatne usluge");
			System.out.println("  5. Zavrsi izmenu");
			
			opcija = scanner.nextLine();
			if(opcija.equals("1")) 			izmeniDatumDolaska(r);
			else if (opcija.equals("2"))	izmeniDatumOdlaska(r);
			else if (opcija.equals("3")) 	izmeniTipSobe(r);
			else if (opcija.equals("4")) 	izmeniUsluge(r);
			else if (opcija.equals("5")) {
				System.out.println("  Izmena zavrsena\n");
				break;
			} else {
				System.out.println("  Nepostojeca opcija\n");
			}
		}		
	}
	private void otkaziRezervaciju() {
		Scanner scanner = new Scanner(System.in);
		List<Rezervacija> rezervacije = filter.getRezervacijeZaOtkaz(trenUser);
		if (rezervacije.size() == 0) {
			System.out.println("  Nema dostupnih rezervacija za otkazivanje\n");
			return;
		}
		System.out.println("  Dostupne rezervacije za otkazivanje su: ");
		for (int i = 0; i < rezervacije.size(); i++) {
			System.out.println("  " + i + " " + rezervacije.get(i).toString());
		}
		while(true) {
			System.out.print("  Unesite index rezervacije koju zelite da otkazete: ");
			int index = scanner.nextInt();
			scanner.nextLine();
			if (index == -1) {
				System.out.println("  Prekid otkazivanja\n");
				break;
			}
			if (index < 0 || index >= rezervacije.size()) {
				System.out.println("  Pogresan index\n");
			}
			else {
				Rezervacija r = rezervacije.get(index);
				r.setStatus(StatusRezervacije.OTKAZANA);
				System.out.println("  Uspesno ste otkazali rezervaciju\n");
				break;
			}
		}
	}
	private void izmenaPodataka() {
		System.out.println("  Izmena licnih podataka");
        MeniIzmenePodataka mip = new MeniIzmenePodataka();
        mip.showIzmenaPodataka(trenUser);
	}
	private void prikaziSveTipoveSoba() {
		System.out.println("  Prikaz svih tipova soba:");
		for (TipSobe ts : mf.getMTS().getTipoviSoba()) {
			System.out.println(ts.toString());
		}
	}
	
	public void showMenu() {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("\nDobrodošli " + trenUser.ime + " " + trenUser.prezime + "!");
			System.out.println("1. Prikazi sve tipove soba");
			System.out.println("2. Rezervisi sobu");
			System.out.println("3. Prikazi sve rezervacije");
			System.out.println("4. Izmeni rezervaciju");
			System.out.println("5. Otkazi rezervaciju");
			System.out.println("6. Izmena licnih podataka");
			System.out.println("7. Izlogujte se");
			
			System.out.print("Izaberite opciju: ");
			String opcija = scanner.nextLine();
			
			if (opcija.equals("1")) 	    prikaziSveTipoveSoba();
			else if (opcija.equals("2")) 	rezervacijaSobe();
			else if (opcija.equals("3")) 	prikaziSveRezervacije();
			else if (opcija.equals("4")) 	izmenaRezervacije();
			else if (opcija.equals("5")) 	otkaziRezervaciju();
			else if (opcija.equals("6")) 	izmenaPodataka();
			else if (opcija.equals("7")) {
				System.out.println("Uspesno ste se izlogovali kao gost\n\n");
				return;
			} else {
				System.out.println("Nepostojeca opcija");
			}
		}
		
	}
}
