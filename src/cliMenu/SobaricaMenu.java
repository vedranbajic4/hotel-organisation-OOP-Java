package cliMenu;

import java.util.List;
import java.util.Scanner;

import entity.Rezervacija;
import entity.Soba;
import entity.Sobarica;
import enums.StatusSobe;
import filter.Filter;
import managerKlase.ManagerFactory;

public class SobaricaMenu {
	private Sobarica trenUser;
	private Filter filter;
	
	public SobaricaMenu(ManagerFactory mf, Sobarica trenUser) {
		this.trenUser = trenUser;
		this.filter = new Filter(mf);
	}
	public void showMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n  Ulogovani ste kao sobarica");
		while(true) {
			System.out.println("\nDobrodošli " + trenUser.ime + " " + trenUser.prezime + "!");
			System.out.println("1. Ocisti sobu");
			System.out.println("6. Izlogujte se");
			System.out.print("Izaberite opciju: ");
			String opcija = scanner.nextLine();
			if (opcija.equals("1")) {
				if (trenUser.brojSobaZaSpremanje() == 0) {
					System.out.println("  Nema soba za ciscenje");
					continue;
				}
				
				System.out.println("  Sobe za ciscenje:");
				List<Soba> sobe = trenUser.getSobeZaSpremanje();
				for (int i = 0; i < sobe.size(); i++) {
					System.out.println("  " + i + " " + sobe.get(i));
				}
				System.out.print("  Izaberite sobu: ");
				int index = scanner.nextInt();
				scanner.nextLine();
				if (index < 0 || index >= sobe.size()) {
					System.out.println("  Nepostojeca soba");
					continue;
				}
				else {
					trenUser.spremiSobu(sobe.get(index));
					System.out.println("  Sobu " + sobe.get(index) + " ste uspesno ocistili");
					//sobe.get(index).setStatus(StatusSobe.SLOBODNA); // visak
				}
				
			} else if (opcija.equals("6")) {
				System.out.println("  Uspesno ste se izlogovali kao sobarica\\n\\n");
				break;
			} else {
				System.out.println(" Nepostojeca opcija");
			}
		}
	}
}
