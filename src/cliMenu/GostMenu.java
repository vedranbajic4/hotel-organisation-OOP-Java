package cliMenu;

import java.util.Scanner;

import entity.Gost;
import managerKlase.ManagerFactory;

public class GostMenu {
	private ManagerFactory mf;
	private Gost trenUser;
	
	public GostMenu(ManagerFactory mf, Gost trenUser) {
		this.mf = mf;
		this.trenUser = trenUser;
	}
	public void showMenu() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("\nDobrodošli " + trenUser.ime + " " + trenUser.prezime + "!");
			System.out.println("1. Prikazi sve sobe");
			System.out.println("2. Rezervisi sobu");
			System.out.println("3. Prikazi sve rezervacije");
			System.out.println("4. Izmeni rezervaciju");
			System.out.println("5. Otkazi rezervaciju");
			System.out.println("6. Izlogujte se");
			System.out.print("Izaberite opciju: ");
			
			String opcija = scanner.nextLine();
			//System.out.println("Trenutna opcija je: " + opcija);
			
			if (opcija.equals("1")) {
				System.out.println("Prikazi sve sobe");
			} else if (opcija.equals("2")) {
				System.out.println("Rezervisi sobu");
			} else if (opcija.equals("3")) {
				System.out.println("Prikazi sve rezervacije");
			} else if (opcija.equals("4")) {
				System.out.println("Izmeni rezervaciju");
			} else if (opcija.equals("5")) {
				System.out.println("Otkazi rezervaciju");
			} else if (opcija.equals("6")) {
				System.out.println("Izlogujte se");
				return;
			} else {
				System.out.println("Nepostojeca opcija");
			}
		}
		
	}
}
