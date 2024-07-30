package main;

import managerKlase.ManagerFactory;
import Gui.MainFrame;

public class Hotel {
	public static void main(String[] args) {
		System.out.println("Welcome to Hotel Management System");
		 
		ManagerFactory mf = new ManagerFactory();
		mf.loadData();
		
		new MainFrame(mf);
		
		//mf.saveData();
	}
}
