package entity;

import enums.StatusSobe;

public class Soba implements Comparable<Soba>{
	private StatusSobe status = StatusSobe.SLOBODNA;		//slobodna, spremanje, zauzeta...
	private int brojSobe;
	private TipSobe tipSobe;
	private int id;
	
	public Soba() {
	}
	public Soba(int id, TipSobe tipSobe) {
		this.id = id;
		this.tipSobe = tipSobe;
	}
	public Soba(int id, TipSobe tipSobe, int brojSobe) {
		this.id = id;
		this.tipSobe = tipSobe;
		this.brojSobe = brojSobe;
	}
	public Soba(int id, TipSobe tipSobe, int brojSobe, StatusSobe status) {
		this.id = id;
		this.tipSobe = tipSobe;
		this.brojSobe = brojSobe;
		this.status = status;
	}
	//metode
	public void setStatus(StatusSobe status) {
        this.status = status;
    }
	public StatusSobe getStatus() {
		return this.status;
	}

	public int getId() {
		return this.id;
	}
	
	public TipSobe getTipSobe() {
		return this.tipSobe;
	}
	public void setTipSobe(TipSobe t) {
		this.tipSobe = t;
	}
	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}
	public int getBrojSobe() {
		return this.brojSobe;
	}
	
    @Override
	public int compareTo(Soba s) {
        return this.tipSobe.getBrojKreveta() - s.tipSobe.getBrojKreveta();
    }
    @Override
	public String toString() {
		return "Soba [id=" + id  + ", brojSobe=" + brojSobe + ", status=" + status + "]";
	}
    public String toFileString() {
    	return id + "," + tipSobe.getBrojKreveta() + "," + tipSobe.getRaspored() + "," + brojSobe + "," + status;
    }
}
