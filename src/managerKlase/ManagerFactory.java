package managerKlase;

public class ManagerFactory {
	private ManagerAdmin ma;
	private ManagerGost mg;
	private ManagerRecepcioner mr;
	private ManagerSobarica ms;
	private ManagerCenovnik mc;
	private ManagerSoba msoba;
	private ManagerRezervacija mrez;
	
	public ManagerFactory() {
		this.ma = new ManagerAdmin();
		this.mg = new ManagerGost();
		this.mr = new ManagerRecepcioner();
		this.ms = new ManagerSobarica();
		this.mc = new ManagerCenovnik(5);
		this.msoba = new ManagerSoba();
		this.mrez = new ManagerRezervacija();
	}

	public ManagerAdmin getMA() {			//get ManagaerAdmin
		return ma;
	}
	public ManagerGost getMG() {			//get ManagerGost
		return mg;
	}
	public ManagerRecepcioner getMR() {		//get ManagerRecepcioner
		return mr;
	}
	public ManagerSobarica getMS() {        //get ManagerSobarica
		return ms;
	}
	public ManagerCenovnik getMC() { 		// get ManagerCenovnik
		return mc;
	}
	public ManagerSoba getMSoba() { 		// get ManagerSoba
		return msoba;
	}
	public ManagerRezervacija getMRez() { 	// get ManagerRezervacija
		return mrez;
	}
}
