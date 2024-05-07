package enums;

public enum Pol {
	MUSKI(1), ZENSKI(2);
	int pol;
	private Pol() {}
	private Pol(int i) {
		this.pol = i;
	}
}
