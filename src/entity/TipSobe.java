package entity;

public class TipSobe {
	private int brojKreveta;
	private String raspored;

	// konstruktori
	public TipSobe() {
	}
	public TipSobe(int brojKreveta, String raspored) {
		this.brojKreveta = brojKreveta;
		this.raspored = raspored;
	}
	
	public int getBrojKreveta() {
		return this.brojKreveta;
	}
	public String getRaspored() {
		return this.raspored;
	}

	public String toFileString() {
		return brojKreveta + "," + raspored;
	}
	@Override
	public String toString() {
		return "TipSobe [brojKreveta=" + brojKreveta + ", raspored=" + raspored + "]";
	}
}
