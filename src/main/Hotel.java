package main;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Cenovnik;
import entity.TipSobe;
import entity.DodatnaUsluga;

import managerKlase.ManagerFactory;
import utils.Pregled;

public class Hotel {
	public static void main(String[] args) {
		System.out.println("Welcome to Hotel Management System");
		 
		ManagerFactory mf = new ManagerFactory();
		mf.loadData();
		
		
		
		mf.saveData();
	}
}
