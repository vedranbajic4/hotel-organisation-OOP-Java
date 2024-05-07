package entity;

import enums.StatusSobe;

public class Soba implements Comparable<Soba>{
	private StatusSobe status;		//slobodna, spremanje, zauzeta...
	private String raspored;		//3, 3+1, 2+1, 1+1, 2+2...
	private int tipSobe;			//broj kreveta
	private int id;
	
	//konstruktori
	public Soba() {}
	public Soba(int id, int tipSobe, String raspored, StatusSobe status) {
		this.tipSobe = tipSobe;
		this.raspored = raspored;
		this.status = status;
		this.id = id;
	}
	public Soba(int id, int tipSobe, String raspored) {
		this.raspored = raspored;
		this.tipSobe = tipSobe;
		this.status = StatusSobe.SLOBODNA;
		this.id = id;
	}
	public Soba(int id, int tipSobe) {
		this.raspored = Integer.toString(tipSobe);
		this.status = StatusSobe.SLOBODNA;
		this.tipSobe = tipSobe;
		this.id = id;
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
	public void setRaspored(String raspored) {
		this.raspored = raspored;
	}
	public void setRaspored(int tipSobe, String raspored) {
		this.raspored = raspored;
		this.tipSobe = tipSobe;
	}
	
	@Override
	public String toString() {
		return "Soba id: " + id + ", broj kreveta: " + tipSobe + ", raspored: " + raspored + ", status: " + status;
	}
    @Override
	public int compareTo(Soba s) {
        return this.tipSobe - s.tipSobe;
    }
}
