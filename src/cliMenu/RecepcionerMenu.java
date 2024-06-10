package cliMenu;

import java.awt.Menu;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import entity.DodatnaUsluga;
import entity.Gost;
import entity.Recepcioner;
import entity.Rezervacija;
import entity.Soba;
import enums.StatusRezervacije;
import enums.StatusSobe;
import filter.Filter;
import managerKlase.ManagerFactory;
import utils.Kalkulacija;

public class RecepcionerMenu {
	private ManagerFactory mf;
	private Recepcioner trenUser;
	private Filter filter;
	Kalkulacija kalk;
	
	public RecepcionerMenu(ManagerFactory mf, Recepcioner trenUser) {
		this.kalk = new Kalkulacija(mf.getMC());
		this.mf = mf;
		this.trenUser = trenUser;
		this.filter = new Filter(mf);
	}

	private void prikaziSveRezervacije() {
		List<Rezervacija> rezervacije = mf.getMRez().getRezervacije();
		System.out.println(" Sve rezervacije: ");
		for (Gost g : mf.getMG().getGosti()) {
			for (Rezervacija r : g.getRezervacije()) {
				System.out.println(g.getKorisnickoIme() + " " + r);
			}
		}
	}
	private void checkInGosta() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(" Check in gosta");
		String korisnickoIme = null;
		System.out.print(" Unesite korisnicko ime gosta: ");
		korisnickoIme = scanner.nextLine();
		
		Gost gost = mf.getMG().postojiKorisnik(korisnickoIme);
		if(gost == null){
            System.out.println("  Niste registrovani korisnik");
            return;
        }
        else {
            System.out.println("  Dobro dosli " + gost.getIme() + " " + gost.getPrezime() + "!\n");
            List<Rezervacija> rezervacijeGosta = filter.getRezervacijeGosta(gost);
			if (rezervacijeGosta.size() == 0) {
				System.out.println("  Nemate potvrdjenih rezervacija");
				return;
			}
			else {
				System.out.println("  Vase potvrdjene rezervacije su: ");
				for (int i = 0; i < rezervacijeGosta.size(); i++) {
					System.out.println("  " + i + "  " + rezervacijeGosta.get(i));
				}
				System.out.println("  Izaberite redni broj rezervacije koju zelite da potvrdite: ");
				int redniBroj = scanner.nextInt();
				scanner.nextLine();
				if (redniBroj < 0 || redniBroj >= rezervacijeGosta.size()) {
					System.out.println("  Pogresan redni broj");
					return;
				}
				else {
					Rezervacija r = rezervacijeGosta.get(redniBroj);
					
					List<Soba> dostupneSobe = filter.getDostupneSobe(r.getTipSobe(), r.getDatumDolaska(),
							r.getDatumOdlaska());
					if (dostupneSobe.size() == 0) {
						System.out.println("  Nema dostupnih soba za ovu rezervaciju.\n  Izvinjavamo se...");
						return;
					}
					else {
						Soba s = dostupneSobe.get(0);
						r.setSoba(s);
						s.setStatus(StatusSobe.ZAUZETA);
						
						System.out.print("  Dobili ste sobu: " + s.getBrojSobe() + "\n  Zelite li dodatne usluge? (1/2)(da/ne)");
						String odgovor = scanner.nextLine();
						if(odgovor.equals("1")) {
							System.out.println("\n   Dostupne dodatne usluge:");
							List<DodatnaUsluga> usluge = mf.getMU().getUslugeBezPostojecih(r.getUsluge());
							if(usluge.size() != 0){
								System.out.println("   index, naziv");
								for (int i = 0; i < usluge.size(); i++) {
									System.out.println("   " + i + " " + usluge.get(i).getNaziv());
								}
								System.out.println("   Birajte indexom sta zelite (-1 za kraj)");
								HashMap<Integer, Integer> mapa = new HashMap<Integer, Integer>();
								int index = -1;
								while(true) {
									System.out.print("\n    index: ");
									index = scanner.nextInt();
									scanner.nextLine();
									if(index == -1) {
										System.out.println("    Zavrseno");
										break;
									}
									if(index < 0 || index >= usluge.size()) {
										System.out.println("    Pogresan index");
									}
									else {
										if(mapa.containsKey(index)) {
											System.out.println("    Ovo ste vec izabrali");
										}
										else {
											mapa.put(index, 1);
											r.dodajUslugu(usluge.get(index));
											System.out.println("    Usluga: " + usluge.get(index).getNaziv() + " uspesno dodata");
										}
									}
								}
								kalk.izracunajCenu(r);
							}
							else {
								System.out.println("   Nema dostupnih dodatnih usluga");
							}
							System.out.println(" Gotovo, uspesno ste dodali i usluge! \n\n");
						}
						System.out.println(" Napomena: Cena rezervacije je: " + r.getCena());
						System.out.println("  Gotovo, mozete u svoju sobu! \n\n");
					}
				}
			}
        }
	}
	private void potvrdiRez() {
		Scanner scanner = new Scanner(System.in);
		List<String> rezStringNaCekanju = filter.getStringRezervacijeNaCekanju();
		List<Rezervacija> rezNaCekanju = filter.getRezervacijePoStatusu(StatusRezervacije.NACEKANJU);
		System.out.println(" Sve rezervacije na cekanju: ");
		for (String s : rezStringNaCekanju) {
			System.out.println(s);
		}
		
		int idRez=-1;
		List<Soba> listaDostupnihSoba = null;
		Rezervacija mojaR = null;
		while(true) {
			System.out.println(" Izaberite id koju zelite da potvrdite: ");
			idRez = scanner.nextInt();
			scanner.nextLine();	//smece
			if(idRez == -1) {
				System.out.println("  Izlazak");
				break;
			}
			mojaR = null;
			for (Rezervacija r : rezNaCekanju) {
				if (idRez == r.getId()) {
					mojaR = r;
					break;
				}
			}
			if(mojaR == null) {
				System.out.println("  Pogresan id rezevacije");
				continue;
			}
			//meni samo treba da li ima dostupnih soba sa tim tipom sobe za taj datum, NE TACNE SOBE
			listaDostupnihSoba = filter.getDostupneSobe(
					mojaR.getTipSobe(), mojaR.getDatumDolaska(), mojaR.getDatumOdlaska());
			if(listaDostupnihSoba.size() == 0) {
				System.out.println("  Nema dostupnih soba, zelite li da odbijete ovu rezervaciju? (1/2)(da/ne)");
				String opcija2 = scanner.nextLine();
				if(opcija2.equals("1")) {
					System.out.println("  Status date rezervacije je: ODBIJEN\n");
					mojaR.setStatus(StatusRezervacije.ODBIJENA);
				}
				else {
					System.out.println("  Nista se nije desilo :)");
				}
			}
			else {
				System.out.println("  Dostupnih soba za ovu rezervaciju ima : " + listaDostupnihSoba.size());
				System.out.println("  Zelite li da prihavtite ovu rezervaciju? (1/2)(da/ne)");
				String opcija2 = scanner.nextLine();
				if(opcija2.equals("1")) {
					System.out.println("  Status date rezervacije je: POTVRDJEN\n");
					mojaR.setStatus(StatusRezervacije.POTVRDJENA);
				}
				else {
					System.out.println("  Nista se nije desilo :)");
				}
			}
			break;
		}
	}
	private void checkOutGosta() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("  Check out gosta");
		String korisnickoIme = null;
		System.out.print("  Unesite korisnicko ime gosta: ");
		korisnickoIme = scanner.nextLine();
		Gost gost = mf.getMG().postojiKorisnik(korisnickoIme);
		if(gost == null) {
			System.out.println("  Korisnik ne postoji");
            return;
        }
        else {
        	Soba njegovaSoba = gost.postojiSobaZaCheckOut();
        	if(njegovaSoba != null) {	//samo proveri njegove rez, nadje potvrdjenu sa postojeceom sobom
        		System.out.println("  Uspesno ste check out-ovali gosta iz sobe: " + njegovaSoba.getBrojSobe());
        		System.out.println("  Ukupna cena vaseg boravka je: " + gost.postojiRezervacijaZaCheckOut().getCena());
        		njegovaSoba.setStatus(StatusSobe.SPREMANJE);
        		System.out.println("  Soba je data sobarici na spremanje");
        		mf.getMS().dodeliSobu(njegovaSoba);
        		gost.postojiRezervacijaZaCheckOut().setStatus(StatusRezervacije.ZAVRSENA);	
        	}
        	else {
        		System.out.println("  Nema sobe za check out");
        	}
        }
	}
	private void registracijaNovogGosta() {
		System.out.println(" Registracija novog gosta");
	}
	
	public void showMenu() {
		Scanner scanner = new Scanner(System.in);
		List<Rezervacija> rezervacije = null;
		
		while(true) {
			System.out.println("\nDobrodošli " + trenUser.ime + " " + trenUser.prezime + "!");
			System.out.println("1. Prikazi sve rezervacije");
			System.out.println("2. Check in (gosta)");
			System.out.println("3. Potvrdi rezervacije");
			System.out.println("4. Check out (gosta)");
			System.out.println("5. Registracija novog gosta");
			System.out.println("6. Izmena licnih podataka");
			System.out.println("7. Izlogujte se");
			
			System.out.print("Izaberite opciju: ");
			String opcija = scanner.nextLine();
			if(opcija.equals("1"))  		prikaziSveRezervacije();
			else if(opcija.equals("2"))  	checkInGosta();
			else if(opcija.equals("3")) 	potvrdiRez();
			else if(opcija.equals("4")) 	checkOutGosta();
			else if(opcija.equals("5")) 	registracijaNovogGosta();
			else if (opcija.equals("6")) {
				MeniIzmenePodataka meni = new MeniIzmenePodataka();
				meni.showIzmenaPodataka(trenUser);
			}
			else if(opcija.equals("7")) {
				System.out.println(" Uspesno ste se izlogovali kao recepcioner\n\n");
				return;
            }
            else {
                System.out.println(" Nepostojeca opcija");
            }
		}
	}
}
