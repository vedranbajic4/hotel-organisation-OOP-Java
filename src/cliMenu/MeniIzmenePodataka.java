package cliMenu;

import entity.Gost;
import entity.Korisnik;
import entity.Sobarica;
import entity.Recepcioner;

import java.util.Scanner;

import entity.Administrator;

public class MeniIzmenePodataka {
	
	public void showIzmenaPodataka(Korisnik s) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("\n  Izmena podataka");
			System.out.println("  1. Prezime");
			System.out.println("  2. Telefon");
			System.out.println("  3. Adresa");
			System.out.println("  4. Korisnicko ime");
			System.out.println("  5. Lozinka");
			System.out.println("  6. Nazad");
			System.out.print("  Izaberite opciju: ");
			
			String opcija = scanner.nextLine();
			
			if(opcija.equals("1")) {
				System.out.print("  Unesite novo prezime: ");
				String prezime = scanner.nextLine();
				s.setPrezime(prezime);
				System.out.println("  Uspesno ste promenili prezime!");
			}
			else if (opcija.equals("2")) {
				System.out.print("  Unesite novi telefon: ");
				String telefon = scanner.nextLine();
                s.setTelefon(telefon);
                System.out.println("  Uspesno ste promenili telefon!");
            }
            else if (opcija.equals("3")) {
            	System.out.print("  Unesite novu adresu: ");
                String adresa = scanner.nextLine();
                s.setAdresa(adresa);
                System.out.println("  Uspesno ste promenili adresu!");
            }
            else if (opcija.equals("4")) {
            	System.out.print("  Unesite novo korisnicko ime: ");
                String korisnickoIme = scanner.nextLine();
                s.setKorisnickoIme(korisnickoIme);
                System.out.println("  Uspesno ste promenili korisnicko ime!");
            }
            else if (opcija.equals("5")) {
            	System.out.print("  Unesite novu lozinku: ");
                String lozinka = scanner.nextLine();
                s.setLozinka(lozinka);
                System.out.println("  Uspesno ste promenili lozinku!");
            }
            else if (opcija.equals("6")) {
                break;
			}
            else {
				System.out.println("  Nepostojeca opcija!");
			}
		}
	}

	
}
