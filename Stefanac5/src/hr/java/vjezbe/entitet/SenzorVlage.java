package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class SenzorVlage extends Senzor {

	public SenzorVlage(String mjernaJedinica, byte preciznost) {
		super(mjernaJedinica, preciznost);
	}
	
	public SenzorVlage(String mjernaJedinica, byte preciznost, RadSenzora radSenzora) {
		super(mjernaJedinica, preciznost, radSenzora);
	}
	
	public SenzorVlage(String mjernaJedinica, byte preciznost, RadSenzora radSenzora, BigDecimal vrijednost) {
		super(mjernaJedinica, preciznost, radSenzora, vrijednost);
	}
	
	public SenzorVlage(String mjernaJedinica, byte preciznost, RadSenzora radSenzora, BigDecimal vrijednost, int id) {
		super(mjernaJedinica, preciznost, radSenzora, vrijednost);
		super.setId(id);
	}
	

	@Override 
	public String dohvatiPodatkeSenzora () {	
		return  "\nVrijednost vlage: " + this.getVrijednost() + this.getMjernaJedinica() +
				", Naèin rada: " + this.getRadSenzora();
	}
	
	@Override 
	public String dohvatiImeSenzora () {
		return "Senzor vlage";
	}
	
}
