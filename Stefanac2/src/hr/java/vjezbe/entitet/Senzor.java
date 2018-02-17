package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public abstract class Senzor {
	
	private String mjernaJedinica;
	private byte preciznost;
	private BigDecimal vrijednost;
	
	//konstruktor klase koji æe primiti mjernu jedinicu i preciznost
	
	public Senzor(String mjernaJedinica, byte preciznost) {
		super();
		//spremanje u privatne varijable
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
	}
	
	
	//getteri i setteri
	
	public String getMjernaJedinica() {
		return mjernaJedinica;
	}
	
	public void setMjernaJedinica(String mjernaJedinica) {
		this.mjernaJedinica = mjernaJedinica;
	}
	
	public Byte getPreciznost() {
		return preciznost;
	}
	
	public void setPreciznost(Byte preciznost) {
		this.preciznost = preciznost;
	}
	
	public BigDecimal getVrijednost() {
		return vrijednost;
	}
	
	public void setVrijednost(BigDecimal vrijednost) {
		this.vrijednost = vrijednost;
	}
	
	//kreiranje apstraktne metode
	
	public abstract String dohvatiPodatkeSenzora();
	
}
