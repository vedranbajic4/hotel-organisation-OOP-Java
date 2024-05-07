package enums;

public enum StatusSobe {
	SLOBODNA(0), ZAUZETA(1), SPREMANJE(2);
	int s;
	private StatusSobe() {
		this.s = 0;
	}
	private StatusSobe(int i) {
		this.s = i;
	}
}
