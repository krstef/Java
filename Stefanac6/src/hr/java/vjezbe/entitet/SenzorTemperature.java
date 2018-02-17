package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

//import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
//import hr.java.vjezbe.iznimke.VisokaTemperaturaException;

public class SenzorTemperature extends Senzor {

	private String nazivKomponente;

	public SenzorTemperature(String mjernaJedinica, byte preciznost, String nazivKomponente) {
		super(mjernaJedinica, preciznost);
		this.nazivKomponente = nazivKomponente;
	}

	public SenzorTemperature(String mjernaJedinica, byte preciznost, String nazivKomponente, RadSenzora radSenzora) {
		super(mjernaJedinica, preciznost, radSenzora);
		this.nazivKomponente = nazivKomponente;
	}

	public SenzorTemperature(String mjernaJedinica, byte preciznost, String nazivKomponente, RadSenzora radSenzora, BigDecimal vrijednost) {
		super(mjernaJedinica, preciznost, radSenzora,vrijednost);
		this.nazivKomponente = nazivKomponente;
	}
	
	public SenzorTemperature(String mjernaJedinica, byte preciznost, String nazivKomponente, RadSenzora radSenzora, BigDecimal vrijednost, int id) {
		super(mjernaJedinica, preciznost, radSenzora,vrijednost);
		super.setId(id);
		this.nazivKomponente = nazivKomponente;
	}

	@Override 
	public String dohvatiPodatkeSenzora () {	
		return "\nVrijednost temperature: " + this.getVrijednost() + this.getMjernaJedinica() +
				" Naziv komponente: " + this.nazivKomponente + ", Naèin rada: " + this.getRadSenzora();
	}

	@Override 
	public String dohvatiImeSenzora () {
		return "Senzor temperature";
	}
	

	/**
	 * Metoda za generiranje temperature u rasponu -50 do +50 stupnjeva C
	 * @throws VisokaTemperaturaException
	 */
/*	public void generirajVrijednost() throws VisokaTemperaturaException {

		BigDecimal num = new BigDecimal((int) (Math.random()*(51+50) - 50));
		BigDecimal numUpper = new BigDecimal("40");
		BigDecimal numLow = new BigDecimal("-10");
		super.setVrijednost(num);

		if(num.compareTo(numUpper) > 0) {
			throw new VisokaTemperaturaException("Temperatura je presla gornju granicu! "
					+ "Temperatura iznosi " + num + " stupnjeva");
		} else if (num.compareTo(numLow) < 0) {
			throw new NiskaTemperaturaException("Temperatura je presla donju granicu! "
					+ "Temperatura iznosi " + num + " stupnjeva");
		}
	} */
}
