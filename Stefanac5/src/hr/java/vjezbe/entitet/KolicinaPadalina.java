package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class KolicinaPadalina extends Senzor {

	private String tipSenzora;

	public KolicinaPadalina(String mjernaJedinica, byte preciznost, String tipSenzora) {
		super(mjernaJedinica, preciznost);
		this.tipSenzora = tipSenzora;
	}
	
	public KolicinaPadalina(String mjernaJedinica, byte preciznost, String tipSenzora, RadSenzora radSenzora, BigDecimal vrijednost, int id) {
		super(mjernaJedinica, preciznost, radSenzora, vrijednost);
		super.setId(id);
		this.tipSenzora = tipSenzora;
	}
	
	public String getTipSenzora() {
		return tipSenzora;
	}

	public void setTipSenzora(String tipSenzora) {
		this.tipSenzora = tipSenzora;
	}
	
	@Override 
	public String dohvatiPodatkeSenzora () {	
		return "\nVrijednost padalina: " + this.getVrijednost() + this.getMjernaJedinica() +
				" Broj mjerenja: " + this.tipSenzora + ", Naèin rada: " + this.getRadSenzora();
	}
	
	@Override 
	public String dohvatiImeSenzora () {
		return "Senzor kolicine padalina";
	}
	
}
