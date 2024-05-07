package utils;

import java.awt.print.Printable;
import java.time.LocalDate;
import java.util.ArrayList;

import managerKlase.ManagerAdmin;
import managerKlase.ManagerFactory;
import managerKlase.ManagerRecepcioner;
import managerKlase.ManagerSobarica;

public class Pregled {
	private ManagerAdmin ma;
	private ManagerSobarica ms;
	private ManagerRecepcioner mr;
	
	public Pregled(ManagerFactory mf) {
		this.ma = mf.getMA();
        this.ms = mf.getMS();
        this.mr = mf.getMR();
	}
	
	public void prikaziSveAdmine() {
		ma.prikaziSveAdmine();
	}
	public void prikaziSveSobarice() {
		ms.prikaziSveSobarice();
	}
	public void prikaziSveRecepcionere() {
		mr.prikaziSveRecepcionere();
	}

	public void prikaziSveZaposlene() {
		prikaziSveAdmine();
		prikaziSveSobarice();
		prikaziSveRecepcionere();
	}
}
