package enums;

public enum StrucnaSprema {
	PRVI_STEPEN(0), DRUGI_STEPEN(1), TRECI_STEPEN(2),
	CETVRTI_STEPEN(3), PETI_STEPEN(4), SESTI_STEPEN(5), SEDMI_STEPEN(6);
	int stepen;
	private StrucnaSprema() {}
	private StrucnaSprema(int stepen) {
		this.stepen = stepen;
	}
	
}
