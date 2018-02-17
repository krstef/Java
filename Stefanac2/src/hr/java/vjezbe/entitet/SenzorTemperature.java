package hr.java.vjezbe.entitet;

public class SenzorTemperature extends Senzor {

	private String nazivKomponente;

	public SenzorTemperature(String mjernaJedinica, byte preciznost, String nazivKomponente) {
		super(mjernaJedinica, preciznost);
		this.nazivKomponente = nazivKomponente;
	}

	@Override 
	public String dohvatiPodatkeSenzora () {	
		return " Vrijednost temperature: " + this.getVrijednost() + this.getMjernaJedinica() +
				" Naziv komponente: " + this.nazivKomponente;
	}
}
