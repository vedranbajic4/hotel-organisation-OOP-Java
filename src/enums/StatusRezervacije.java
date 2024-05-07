package enums;

public enum StatusRezervacije {
	NACEKANJU(0), POTVRDJENA(1), ODBIJENA(2), OTKAZANA(3);
	int tip;
	private StatusRezervacije() {
		this.tip = 0; 
	}
	private StatusRezervacije(int i) {
		this.tip = i;
	}
}
