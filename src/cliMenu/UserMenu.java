package cliMenu;

import java.util.Scanner;

import managerKlase.ManagerFactory;

public class UserMenu {
	private ManagerFactory mf;
	
	public UserMenu(ManagerFactory mf) {
		this.mf = mf;
	}
	public void showMenu() {
		while(true) {
			System.out.println("\nDobrodošli u hotel!");
			System.out.println("1. Ulogujte se");
			//System.out.println("2. Registrujte se");
			System.out.println("2. Izlaz");
			System.out.print("Izaberite opciju: ");
			Scanner scanner = new Scanner(System.in);
			String opcija = scanner.nextLine();
			if(opcija.equals("1")) {
				System.out.print("___________\nUlogujte se, unesite korisnicko ime: ");
				String korisnickoIme = scanner.nextLine();
				System.out.print("Unesite lozinku: ");
				String lozinka = scanner.nextLine();
				
				//admin
				if(mf.getMA().postojiKorisnik(korisnickoIme, lozinka) != null) {
                    System.out.println("Uspesno ste se ulogovali kao administrator");
                    AdminMenu am = new AdminMenu(mf, mf.getMA().postojiKorisnik(korisnickoIme, lozinka));
                    am.showMenu();
				}
				//gost
				else if (mf.getMG().postojiKorisnik(korisnickoIme, lozinka) != null) {
					System.out.println("Uspesno ste se ulogovali kao gost");
					GostMenu gm = new GostMenu(mf, mf.getMG().postojiKorisnik(korisnickoIme, lozinka));
					gm.showMenu();
				}
				//recepcioner
				else if (mf.getMR().postojiKorisnik(korisnickoIme, lozinka) != null){
					System.out.println("Uspesno ste se ulogovali kao recepcioner");
					RecepcionerMenu rm = new RecepcionerMenu(mf, mf.getMR().postojiKorisnik(korisnickoIme, lozinka));
					rm.showMenu();
				}
				//sobarica
				else if (mf.getMS().postojiKorisnik(korisnickoIme, lozinka) != null) {
                    System.out.println("Uspesno ste se ulogovali kao sobarica");
                    SobaricaMenu sm = new SobaricaMenu(mf, mf.getMS().postojiKorisnik(korisnickoIme, lozinka));
                    sm.showMenu();
                }
                else {
                    System.out.println("Neuspesno logovanje, pokusajte ponovo");
                }
			}
			else if (opcija.equals("2")) {
				System.out.println("Izlaz");
				return;
			} else {
				System.out.println("Nepostojeca opcija");
			}
		}
	}
}
