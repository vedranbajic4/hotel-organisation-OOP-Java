package managerKlase;

public class ManagerFactory {
	private ManagerAdmin ma;
	private ManagerGost mg;
	private ManagerRecepcioner mr;
	private ManagerSobarica ms;
	private ManagerCenovnik mc;
	private ManagerSoba msoba;
	private ManagerRezervacija mrez;
	private ManagerTipSobe mts;
	private ManagerUsluga mu;
	
	public ManagerFactory() {
		this.ma = new ManagerAdmin();
		this.mg = new ManagerGost();
		this.mr = new ManagerRecepcioner();
		this.ms = new ManagerSobarica();
		this.mc = new ManagerCenovnik();
		this.msoba = new ManagerSoba();
		this.mrez = new ManagerRezervacija(this.mc);
		this.mts = new ManagerTipSobe();
		this.mu = new ManagerUsluga();
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
	public ManagerTipSobe getMTS() { 		// get ManagerTipSobe
		return mts;
	}
	public ManagerUsluga getMU() { 			// get ManagerUsluga
		return mu;
	}
	
	public void loadData() {
		ma.loadData();						//admin
		ms.loadData();						//sobarica
		mg.loadData();						//gost
		mr.loadData();						//recepcioner
		mts.loadData();						//tipSobe
		msoba.loadData(mts, ms);				//soba
		mu.loadData();						//usluge
		mrez.loadData(msoba, mts, mu, mg);	//rezervacije
		mc.loadData(mts, mu);				//cenovnik
	}
	public void saveData() {
		ma.saveData();			//admin
		ms.saveData();			//sobarica
		mg.saveData();			//gost
		mr.saveData();			//recepcioner
		mts.saveData();			//tipSobe
		msoba.saveData(ms);		//soba
		mu.saveData();			//usluge
		mrez.saveData(mg);		//rezervacije
		mc.saveData();			//cenovnik
	}
}
