package hr.java.vjezbe.entitet;

public class SenzorVlage extends Senzor {

	public SenzorVlage(String mjernaJedinica, byte preciznost) {
		super(mjernaJedinica, preciznost);
	}

	@Override 
	public String dohvatiPodatkeSenzora () {	
		return " Vrijednost vlage: " + this.getVrijednost() + this.getMjernaJedinica();
	}
}
