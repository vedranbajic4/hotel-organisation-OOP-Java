package cliMenu;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntUnaryOperator;

import javax.swing.plaf.multi.MultiInternalFrameUI;

import entity.Administrator;
import entity.Cenovnik;
import entity.DodatnaUsluga;
import entity.Gost;
import entity.Recepcioner;
import entity.Rezervacija;
import entity.Soba;
import entity.Sobarica;
import entity.Stavka;
import entity.TipSobe;
import entity.Zaposleni;
import enums.Pol;
import enums.StatusSobe;
import enums.StrucnaSprema;
import filter.Filter;
import managerKlase.ManagerFactory;

public class AdminMenu {
	private ManagerFactory mf;
	private Administrator trenUser;
	private Filter filter;
	
	public AdminMenu(ManagerFactory mf, Administrator trenUser) {
		this.mf = mf;
		this.trenUser = trenUser;
		filter = new Filter(mf);
	}
	private void registrujZaposlenog() {	//radi
		System.out.println("  Registracija zaposlenog");
		Scanner scanner = new Scanner(System.in);
		System.out.print("  Izaberite tip zaposlenog: (1. Sobarica, 2. Recepcioner)");
		int tip = scanner.nextInt();
		scanner.nextLine();
		if (tip < 1 || tip > 2) {
			System.out.println("  Nepostojeci tip zaposlenog");
			return;
		}
		System.out.print("  Unesite ime: ");
		String ime = scanner.nextLine();
		System.out.print("  Unesite prezime: ");
		String prezime = scanner.nextLine();
		System.out.print("  Unesite korisnicko ime: ");
		String korisnickoIme = scanner.nextLine();
		System.out.print("  Unesite sifru: ");
		String sifra = scanner.nextLine();
		System.out.print("  Unesite pol: (MUSKI, ZENSKI) ");
		String polString = scanner.nextLine();
		Pol pol;
		try {
			pol = Pol.valueOf(polString);
		}
		catch (Exception e) {
			System.out.println("  Pogresan unos pola");
			return;
		}
		System.out.print("  Unesite datum rodjenja (yyyy-mm-dd): ");
		String datumString = scanner.nextLine();
		LocalDate datumRodjenja;
		try{
			datumRodjenja = LocalDate.parse(datumString);
		}
		catch (Exception e) {
			System.out.println("  Pogresan format datuma");
			return;
		}
		System.out.print("  Unesite telefon: ");
		String telefon = scanner.nextLine();
		System.out.print("  Unesite adresu: ");
		String adresa = scanner.nextLine();
		System.out.print("  Unesite strucnu spremu (broj od 1 do 7)");
		int ss = scanner.nextInt();
		scanner.nextLine();
		StrucnaSprema strucnaSprema;
		if(ss < 0 || ss > 7) {
			System.out.println("  Nepostojeca strucna sprema");
			return;
		}
		else {
			strucnaSprema = StrucnaSprema.values()[ss];
		}
		int staz = 0;
		int plata = 60000 + ss*5000;
		System.out.println("  Zelite li sigurno da registrujete zaposlenog sa ovim podacima: (1/2)(da/ne)");
		int opcija = scanner.nextInt();
		scanner.nextLine();
		if(opcija == 1) {
			if(tip == 1) {
				mf.getMS().dodajSobaricu(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, sifra, plata, staz, strucnaSprema);
			}
			else {
				mf.getMR().dodajRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, sifra, plata, staz, strucnaSprema);
			}
			System.out.println("  Uspesno ste registrovali zaposlenog");
		}
	}
	private void pregledPrihoda() {		//kao radi
		System.out.println("  Pregled prihoda");
		for (Rezervacija r : mf.getMRez().getRezervacije()) {
			if(r.getUkupnaCena() != 0) {
				System.out.println("  " + r.getDatumDolaska() + " += " + r.getUkupnaCena());
			}
		}
	}
	private void pregledZaposlenih() {	//radi
		System.out.println("  Pregled zaposlenih");
		System.out.println("  Sobarice:");
		for (int i = 0; i < mf.getMS().getSobarice().size(); i++) {
			System.out.println("  " + mf.getMS().getSobarice().get(i));
		}
		System.out.println("  Recepcioneri:");
		for (int i = 0; i < mf.getMR().getRecepcioneri().size(); i++) {
			System.out.println("  " + mf.getMR().getRecepcioneri().get(i));
		}
	}
	private void kreirajCenovnik() {	//radi
		System.out.println("  Kreiranje cenovnika");
		System.out.println("  Tipovi soba:");
		List<TipSobe> tipovi = mf.getMTS().getTipoviSoba();
		for (int i = 0; i < tipovi.size(); i++) {
			System.out.println(" " + i + " " + tipovi.get(i));
		}
		Scanner scanner = new Scanner(System.in);
		TipSobe tipSobe = null;
		while(true) {
			System.out.print("  Izaberite tip sobe: ");
			int tip = scanner.nextInt();
			scanner.nextLine();
			if(tip == -1) {
				System.out.println("  Prekid kreiranja cenovnika");
				return;
			}
			if (tip < 0 || tip >= tipovi.size()) {
				System.out.println("  Nepostojeci tip sobe");
				continue;
			}
			System.out.println("  Uspesno izabran tip: " + tipovi.get(tip) + "\n");
			tipSobe = tipovi.get(tip);
			break;
		}
		LocalDate d1=null, d2=null;
		while(true) {
			System.out.print("   Unesite datum pocetka vazenja(yyyy-mm-dd): ");
			String datumPocString = scanner.nextLine();
			if(datumPocString.equals("-1")) {
                System.out.println("   Prekid kreiranja cenovnika\n");
                return;
            }
			try {
				d1 = LocalDate.parse(datumPocString);
				System.out.println("   Uspesno ste izabrali datum pocetka");
                break;
			}
			catch (Exception e) {
				System.out.println("   Pogresan format datuma");
				continue;
			}
		}
		System.out.println("  Zelite li datum kraja vazenja cenovnika? (da/ne)(1/2)");
		int izbor = scanner.nextInt();
		scanner.nextLine();
		if(izbor == 1) {
			while(true) {
				System.out.print("   Unesite datum kraja vazenja(yyyy-mm-dd): ");
				String datumKrajString = scanner.nextLine();
				if(datumKrajString.equals("-1")) {
	                System.out.println("   Prekid kreiranja cenovnika\n");
	                return;
	            }
				try {
					d2 = LocalDate.parse(datumKrajString);
					System.out.println("   Uspesno ste izabrali datum kraja");
	                break;
				}
				catch (Exception e) {
					System.out.println("   Pogresan format datuma");
					continue;
				}
			}
		}
		else {
			System.out.println("  Cenovnik vazi do daljnjeg");
		}
		mf.getMC().kreirajCenovnik(tipSobe, new Cenovnik(d1, d2));
		System.out.println("  Uspesno kreiran cenovnik\n");
	}
	private void dodajStavku(Cenovnik c) {//radi
		System.out.println("  Dodavanje stavke");
		System.out.println("  Dodatne usluge: ");
		for (int i = 0; i < mf.getMU().getUsluge().size(); i++) {
			System.out.println(" " + i + " " + mf.getMU().getUsluge().get(i));
		}
		Scanner scanner = new Scanner(System.in);
		HashMap<Integer, Integer> mapa = new HashMap<Integer, Integer>();
		
		List<Stavka> stavke = mf.getMC().getStavkeByCenovnik(c.getId());
		for (Stavka s : stavke) {
			mapa.put(s.getDodatnaUsluga().getId(), 1);
		}
		
		while(true) {
			System.out.print("  Izaberite dodatnu uslugu: ");
			int tip = scanner.nextInt();
			scanner.nextLine();
			if(tip == -1) {
				System.out.println("  Izlazak");
				return;
			}
			if (tip < 0 || tip >= mf.getMU().getUsluge().size()) {
				System.out.println("  Nepostojeca usluga");
				continue;
			}
			if (mapa.containsKey(mf.getMU().getUsluge().get(tip).getId())) {
				System.out.println("  Vec ste dodali ovu uslugu");
				continue;
			}
			System.out.print("  Unesite cenu za ovu uslug:");
			float cena = scanner.nextFloat();
			scanner.nextLine();
			mapa.put(tip, 1);
			System.out.println("  Uspesno dodata usluga " + mf.getMU().getUsluge().get(tip) + " cena: " + cena);
			mf.getMC().dodajStavku(new Stavka(mf.getMU().getUsluge().get(tip), c.getId(), cena));
		}
	}
	private void ukloniStavku(Cenovnik c) {//radi
		List<Stavka> stavke = mf.getMC().getStavkeByCenovnik(c.getId());
		System.out.println("  Uklanjanje stavke");
		while(true) {
			for (int i = 0; i < stavke.size(); i++) {
				System.out.println("   " + i + " " + stavke.get(i));
			}
			System.out.print("  Izaberite id stavke za uklanjanje");
			Scanner scanner = new Scanner(System.in);
			int id = scanner.nextInt();
			scanner.nextLine();
			if (id == -1) {
				System.out.println("  Izlazak");
				return;
			}
			if (id < 0 || id >= stavke.size()) {
				System.out.println("  Nepostojeci id");
				continue;
			}
			System.out.println("  Uspesno uklonjena stavka: " + stavke.get(id));
			stavke.remove(id);
		}
	}
	private void izmeniStavku(Cenovnik c) {	//nije testirano
		List<Stavka> stavke = mf.getMC().getStavkeByCenovnik(c.getId());
		System.out.println("  Izmena cene stavke");
		while(true) {
			for (int i = 0; i < stavke.size(); i++) {
				System.out.println("   " + i + " " + stavke.get(i) + " " + stavke.get(i).getCena());
			}
			System.out.print("  Izaberite id stavke za menjanje");
			Scanner scanner = new Scanner(System.in);
			int id = scanner.nextInt();
			scanner.nextLine();
			if (id == -1) {
				System.out.println("  Izlazak");
				return;
			}
			if (id < 0 || id >= stavke.size()) {
				System.out.println("  Nepostojeci id");
				continue;
			}
			System.out.print("  Unesite novu cenu, stara je (" + stavke.get(id).getCena() + "): ");
			float cena = scanner.nextFloat();
			scanner.nextLine();
			stavke.get(id).setCena(cena);
			System.out.println("  Uspesno promenjena cena: ");
		}
	}
	private void promeniDatumPocetka(Cenovnik c) {	//nije tested
		LocalDate d1=null, d2=null;
		System.out.println("  Promena datuma pocetka vazenja");
		System.out.println("  Cenovnik vazi: " + c.getDatumPocetak() + " - " + c.getDatumKraj());
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("   Unesite datum pocetka vazenja(yyyy-mm-dd): ");
			String datumPocString = scanner.nextLine();
			if(datumPocString.equals("-1")) {
                System.out.println("   Prekid izmene datuma\n");
                return;
            }
			try {
				d1 = LocalDate.parse(datumPocString);
				if(c.getDatumKraj() == null) {
					c.setDatumPocetak(d1);
					System.out.println("   Uspesno ste promenili datum pocetka");
					break;
				}
				if (d1.isAfter(c.getDatumKraj())) {
					System.out.println("   Datum pocetka ne moze biti posle datuma kraja");
					continue;
				}
				else {
					c.setDatumPocetak(d1);
					System.out.println("   Uspesno ste promenili datum pocetka");
					break;
				}
			}
			catch (Exception e) {
				System.out.println("   Pogresan format datuma");
				continue;
			}
		}
	}
	private void promeniDatumKraja(Cenovnik c) {	//nije testirano
		LocalDate d1=null, d2=null;
		System.out.println("  Promena datuma kraja vazenja");
		if (c.getDatumKraj() == null) {
			System.out.println("  Cenovnik vazi: " + c.getDatumPocetak() + " - do daljnjeg");
		}
		else {
			System.out.println("  Cenovnik vazi: " + c.getDatumPocetak() + " - " + c.getDatumKraj());
		}
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("   Unesite datum kraja vazenja(yyyy-mm-dd): ");
			String datumPocString = scanner.nextLine();
			if(datumPocString.equals("-1")) {
                System.out.println("   Prekid izmene datuma\n");
                return;
            }
			try {
				d1 = LocalDate.parse(datumPocString);
				if (d1.isBefore(c.getDatumPocetak())) {
					System.out.println("   Datum kraja ne moze biti pre datuma pocetka");
					continue;
				}
				else {
					c.setDatumKraj(d1);
					System.out.println("   Uspesno ste promenili datum kraja");
					break;
				}
			}
			catch (Exception e) {
				System.out.println("   Pogresan format datuma");
				continue;
			}
		}
	}
	private void izmeniCenovnik() {
		System.out.println("  Izmena cenovnika");
		
		System.out.println("  Izaberite tip sobe za koji zelite da izmenite cenovnik:");
		List<TipSobe> tipovi = mf.getMTS().getTipoviSoba();
		for (int i = 0; i < tipovi.size(); i++) {
			System.out.println(" " + i + " " + tipovi.get(i));
		}
		Scanner scanner = new Scanner(System.in);
		TipSobe tipSobe = null;
		int tip =0;
		
		while(true) {
			System.out.print("  Izaberite tip sobe: ");
			tip = scanner.nextInt();
			scanner.nextLine();
			if(tip == -1) {
				System.out.println("  Prekid izmene cenovnika");
				return;
			}
			if (tip < 0 || tip >= tipovi.size()) {
				System.out.println("  Nepostojeci tip sobe");
				continue;
			}
			System.out.println("  Uspesno izabran tip: " + tipovi.get(tip) + "\n");
			tipSobe = tipovi.get(tip);
			break;
		}
		
		List<Cenovnik> cenovnici = mf.getMC().getCenovnikByTipSobe(tipSobe);
		if(cenovnici == null) {
			System.out.println("  Ne postoji cenovnik za izabrani tip sobe");
			return;
		}
		System.out.println("  Izaberite cenovnik za izmenu:");
		for (int i = 0; i < cenovnici.size(); i++) {
			System.out.println(" " + i + " " + cenovnici.get(i));
		}
		Cenovnik c = null;
		while(true) {
			System.out.print("  Izaberite cenovnik: ");
			tip = scanner.nextInt();
			scanner.nextLine();
			if(tip == -1) {
				System.out.println("  Prekid izmene cenovnika");
				return;
			}
			if (tip < 0 || tip >= cenovnici.size()) {
				System.out.println("  Nepostojeci cenovnik");
				continue;
			}
			System.out.println("  Uspesno izabran cenovnik:");
			c = cenovnici.get(tip);
			break;
		}
		
		
		String opcija = null;
		while(true) {
			System.out.println("\n  1. Dodaj stavku");
			System.out.println("  2. Ukloni stavku");
			System.out.println("  3. Izmeni stavku");
			System.out.println("  4. Promeni datum vazenja (datum pocetka)");
			System.out.println("  5. Promeni datum vazenja (datum kraja)");
			System.out.println("  6. kraj");
			System.out.print("  Izaberite opciju:");
			opcija = scanner.nextLine();
			if(opcija.equals("1"))  		dodajStavku(c);
			else if (opcija.equals("2"))	ukloniStavku(c);
			else if (opcija.equals("3"))	izmeniStavku(c);
			else if (opcija.equals("4"))	promeniDatumPocetka(c);
			else if (opcija.equals("5"))	promeniDatumKraja(c);
			else if (opcija.equals("6")) {
				System.out.println("  Kraj izmene cenovnika");
				return;
			} else {
				System.out.println("  Nepostojeca opcija");
			}
		}
	}
	private void izmenaPodataka() {		//radi
		System.out.println("  Izmena licnih podataka");
        MeniIzmenePodataka mip = new MeniIzmenePodataka();
        mip.showIzmenaPodataka(trenUser);
	}
	private void promeniTipSobe(Soba s) {	//nije testirano
		System.out.println("  Promena tipa sobe");
		Scanner scanner = new Scanner(System.in);
		System.out.println("  Tipovi soba:");
		List<TipSobe> tipovi = mf.getMTS().getTipoviSoba();
		for (int i = 0; i < tipovi.size(); i++) {
			System.out.println(" " + i + " " + tipovi.get(i));
		}
		while (true) {
			System.out.print("  Izaberite tip sobe: ");
			int tip = scanner.nextInt();
			scanner.nextLine();
			if (tip == -1) {
				System.out.println("  Prekid promene tipa sobe");
				return;
			}
			if (tip < 0 || tip >= tipovi.size()) {
				System.out.println("  Nepostojeci tip sobe");
				continue;
			}
			System.out.println("  Uspesno izabran tip: " + tipovi.get(tip) + "\n");
			s.setTipSobe(tipovi.get(tip));
			System.out.println("  Uspesno promenjen tip sobe");
			return;
		}
	}
	private void promeniBrojSobe(Soba s) {	//nije testirano
        System.out.println("  Promena broja sobe");
        Scanner scanner = new Scanner(System.in);
        int brojSobe = 0;
        while (true) {
            System.out.print("  Unesite broj sobe: ");
            brojSobe = scanner.nextInt();
            scanner.nextLine();
			if (brojSobe == -1) {
				System.out.println("  Prekid promene broja sobe");
				return;
			}
            if (proveriSobu(s.getTipSobe(), brojSobe)) {
                System.out.println("  Soba sa tim brojem vec postoji");
                continue;
            }
            break;
        }
        s.setBrojSobe(brojSobe);
        System.out.println("  Uspesno promenjen broj sobe");
	}
	private void izmenaSobe() {	//nije tesirano
		System.out.println("  Izmena sobe");
		Scanner scanner = new Scanner(System.in);
		System.out.println("  Dostupne sobe: ");
		List<Soba> sobe = mf.getMSoba().getSobe();
		for (int i = 0; i < sobe.size(); i++) {
			System.out.println("  " + i + " " + sobe.get(i));
		}
		while(true) {
			System.out.print("  Unesite broj sobe koju zelite da izmenite: ");
            int brojSobe = scanner.nextInt();
            scanner.nextLine();
            if (brojSobe == -1) {
                System.out.println("  Prekid izmene sobe");
                return;
            }
            if (brojSobe < 0 || brojSobe >= sobe.size()) {
                System.out.println("  Nepostojeci broj sobe");
                continue;
            }
            Soba s = sobe.get(brojSobe);
            
            while(true) {
            	
                System.out.println("\n  1. Promeni tip sobe");
                System.out.println("  2. Promeni broj sobe");
                System.out.println("  3. Kraj");
                System.out.print("  Izaberite opciju:");
	            String opcija = scanner.nextLine();
	            if(opcija.equals("1"))  		promeniTipSobe(s);
				else if (opcija.equals("2"))	promeniBrojSobe(s);
				else if (opcija.equals("3")) {
					System.out.println("  Kraj izmene sobe");
					return;
				} else {
					System.out.println("  Nepostojeca opcija");
				}
            }
		}
	}
	private boolean proveriSobu(TipSobe tipSobe, int brojSobe) {	//radi
		for (Soba s : mf.getMSoba().getSobe()) {
			if (s.getTipSobe().equals(tipSobe) && s.getBrojSobe() == brojSobe) {
				return true;
			}
		}
		return false;
	}
	private void dodavanjeSobe() {	//radi
		System.out.println("  Dodavanje sobe");
		Scanner scanner = new Scanner(System.in);
		TipSobe tipSobe = null;
		int tip =0;
		List<TipSobe> tipovi = mf.getMTS().getTipoviSoba();
		System.out.println("  Tipovi soba:");
		for (int i = 0; i < tipovi.size(); i++) {
			System.out.println(" " + i + " " + tipovi.get(i));
		}
		while(true) {
			System.out.print("  Izaberite tip sobe: ");
			tip = scanner.nextInt();
			scanner.nextLine();
			if(tip == -1) {
				System.out.println("  Prekid dodavanja sobe");
				return;
			}
			if (tip < 0 || tip >= tipovi.size()) {
				System.out.println("  Nepostojeci tip sobe");
				continue;
			}
			System.out.println("  Uspesno izabran tip: " + tipovi.get(tip) + "\n");
			tipSobe = tipovi.get(tip);
			break;
		}
		int brojSobe =0;
		while(true) {
			System.out.print("  Unesite broj sobe: ");
			brojSobe = scanner.nextInt();
			scanner.nextLine();
			if (proveriSobu(tipSobe, brojSobe)) {
				System.out.println("  Soba sa tim brojem vec postoji");
				continue;
			}
			break;
		}
		System.out.println("  Uspesno dodata soba " + tipSobe + " broj " + brojSobe);
		mf.getMSoba().dodajSobu(tipSobe, brojSobe);
	}
	private boolean rasporedOdgovara(int brojKreveta, String raspored) {	//radi
		boolean ret = true;
		int suma = 0;
		String tokeni[] = raspored.split("\\+");
		
		for(String token : tokeni) {
			//System.out.println(token);
			try {
				suma += Integer.parseInt(token);
			}
			catch(Exception e) {
				ret = false;
				break;
			}
        }
		if (suma != brojKreveta)
			ret = false;
		return ret;
	}
	boolean postojiTipSobe(int brojKreveta, String raspored) {	//radi
		for (TipSobe ts : mf.getMTS().getTipoviSoba()) {
			if (ts.getBrojKreveta() == brojKreveta && ts.getRaspored().equals(raspored)) {
				return true;
			}
		}
		return false;
	}
	private void dodavanjeTipaSobe() {	//radi
		System.out.println("  Dodavanje tipa sobe");
		Scanner scanner = new Scanner(System.in);
		System.out.print("  Unesite broj kreveta: ");
		int brojKreveta = scanner.nextInt();
		scanner.nextLine();
		System.out.print("  Unesite raspored: ");
		String raspored = scanner.nextLine();
		if (rasporedOdgovara(brojKreveta, raspored) && !postojiTipSobe(brojKreveta, raspored)) {
			mf.getMTS().dodajTipSobe(new TipSobe(brojKreveta, raspored));
			System.out.println("  Uspesno dodat tip sobe");
		} else {
			System.out.println("  Tip sobe sa tim brojem kreveta i rasporedom vec postoji ili raspored ne odgovara broju kreveta");
		}
	}
	private void dajOtkaz() {		//radi
		System.out.println("  Davanje otkaza");
		System.out.print("  Unesite korisnicko ime koga zelite da otpustite");
		Scanner scanner = new Scanner(System.in);
		String korisnickoIme = scanner.nextLine();
		Recepcioner r = mf.getMR().getRecepcionerByKorisnickoIme(korisnickoIme);
		
		if(r != null) {
			System.out.println("  Otpustili ste recepcionera :( " + r.getIme() + " " + r.getPrezime());
			mf.getMR().ukloniRecepcionera(r.getId());
			return;
		}
		else {
			Sobarica s = mf.getMS().getSobaricaByKorisnickoIme(korisnickoIme);
			if(s != null) {
				for (Soba soba : s.getSobeZaSpremanje()) {
					soba.setStatus(StatusSobe.SLOBODNA);
				}
                System.out.println("  Otpustili ste sobaricu :( " + s.getIme() + " " + s.getPrezime());
                System.out.println("  Njene sobe za ciscenje ce biti ociscene pre nego sto ode :)");
                mf.getMS().ukloniSobaricu(s.getId());
                return;
            }
            else  System.out.println("  Ne postoji zaposleni sa tim korisnickim imenom");
		}
	}
	
	public void showMenu() {
		Scanner scanner = new Scanner(System.in);
		String opcija = "";
		while(true) {
			System.out.println("\nDobrodošli " + trenUser.ime + " " + trenUser.prezime + "!");
			System.out.println("1. Registruj sobaricu/recepcionera");
			System.out.println("2. Pregled prihoda");
			System.out.println("3. Pregled zaposlenih");
			System.out.println("4. Kreiraj novi cenovnik");
			System.out.println("5. Izmeni cenovnik");
			System.out.println("6. Izmena licnih podataka");
			System.out.println("7. Izmena sobe");
			System.out.println("8. Dodavanje sobe");
			System.out.println("9. Dodavanje tipa sobe");
			System.out.println("10. Daj nekom otkaz");
			System.out.println("11. Izlogujte se");
			System.out.print("Izaberite opciju: ");
			
			opcija = scanner.nextLine();
			
			if (opcija.equals("1"))  		registrujZaposlenog();
			else if (opcija.equals("2")) 	pregledPrihoda();
			else if (opcija.equals("3"))	pregledZaposlenih();
			else if (opcija.equals("4"))	kreirajCenovnik();
			else if (opcija.equals("5"))	izmeniCenovnik();
			else if (opcija.equals("6")) 	izmenaPodataka();
			else if (opcija.equals("7"))    izmenaSobe();
			else if (opcija.equals("8"))	dodavanjeSobe();
			else if (opcija.equals("9"))	dodavanjeTipaSobe();
			else if (opcija.equals("10"))   dajOtkaz();
			else if (opcija.equals("11")) {
				System.out.println("Uspesno ste se izlogovali kao administrator\n\n");
				return;
			} else {
				System.out.println("Nepostojeca opcija");
			}
		}
	}
}
