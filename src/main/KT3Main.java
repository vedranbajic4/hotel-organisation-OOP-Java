package main;

import cliMenu.UserMenu;
import managerKlase.ManagerFactory;

public class KT3Main {
	public static void main(String[] args) {
		System.out.println("Hotel!");
		ManagerFactory mf = new ManagerFactory();
		UserMenu um = new UserMenu(mf);
		mf.loadData();
		um.showMenu();
		mf.saveData();
	}
}
