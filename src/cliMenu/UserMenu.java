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
			System.out.println("2. Registrujte se");
			System.out.println("3. Izlaz");
			System.out.print("Izaberite opciju: ");
			Scanner scanner = new Scanner(System.in);
			String opcija = scanner.nextLine();
			if(opcija.equals("1")) {
				System.out.println("___________\nUlogujte se, unesite korisnicko ime: ");
				String korisnickoIme = scanner.nextLine();
				System.out.println("Unesite lozinku: ");
				String lozinka = scanner.nextLine();
				
				if(mf.getMA().postojiKorisnik(korisnickoIme, lozinka) != null) {
                    System.out.println("Uspesno ste se ulogovali kao administrator");
				}
				else if (mf.getMG().postojiKorisnik(korisnickoIme, lozinka) != null) {
					System.out.println("Uspesno ste se ulogovali kao gost");
					GostMenu gm = new GostMenu(mf, mf.getMG().postojiKorisnik(korisnickoIme, lozinka));
					gm.showMenu();
					System.out.println("Uspesno ste se izlogovali kao gost\n\n");
					
				}  
				else if (mf.getMG().postojiKorisnik(korisnickoIme, lozinka) != null){
					System.out.println("Uspesno ste se ulogovali kao gost");
				}
				else if (mf.getMS().postojiKorisnik(korisnickoIme, lozinka) != null) {
                    System.out.println("Uspesno ste se ulogovali kao sobarica");
                }
                else {
                    System.out.println("Neuspesno logovanje, pokusajte ponovo");
                }
			}
			else if (opcija.equals("2")) {
				System.out.println("Registrujte se");
			} else if (opcija.equals("3")) {
				System.out.println("Izlaz");
				return;
			} else {
				System.out.println("Nepostojeca opcija");
			}
		}
	}
}
