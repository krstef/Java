package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public abstract class Senzor {
	
	private String mjernaJedinica;
	private byte preciznost;
	private BigDecimal vrijednost;
	private RadSenzora radSenzora;
	private VrstaSenzora vrstaSenzora;
	
	//konstruktor klase koji æe primiti mjernu jedinicu, preciznost i vrstu rada senzora
	
	public Senzor(String mjernaJedinica, byte preciznost) {
		super();
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
	}
	
	/**
	 * Konstruktor za Senzor koji unosi mjernu jedinicu, preciznost i rad senzora
	 * @param mjernaJedinica
	 * @param preciznost
	 * @param radSenzora
	 */
	public Senzor(String mjernaJedinica, byte preciznost, RadSenzora radSenzora) {
		super();
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
		this.radSenzora = radSenzora;
	}
	
	/**
	 * Konstruktor za Senzor koji unosi mjernu jedinicu, preciznost, rad senzora i vrijednost
	 * @param mjernaJedinica
	 * @param preciznost
	 * @param radSenzora
	 */
	public Senzor(String mjernaJedinica, byte preciznost, RadSenzora radSenzora, BigDecimal vrijednost) {
		super();
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
		this.radSenzora = radSenzora;
		this.vrijednost = vrijednost;
	}
	
	public Senzor(String mjernaJedinica, byte preciznost, RadSenzora radSenzora, BigDecimal vrijednost, VrstaSenzora vrstaSenzora) {
		super();
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
		this.radSenzora = radSenzora;
		this.vrijednost = vrijednost;
		this.vrstaSenzora = vrstaSenzora;
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
	
	public abstract String dohvatiImeSenzora();

	public RadSenzora getRadSenzora() {
		return radSenzora;
	}


	public void setRadSenzora(RadSenzora radSenzora) {
		this.radSenzora = radSenzora;
	}

	public VrstaSenzora getVrstaSenzora() {
		return vrstaSenzora;
	}
	
}
