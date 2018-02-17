package hr.java.vjezbe.entitet;

public class KolicinaPadalina extends Senzor {

	private String tipSenzora;;

	public KolicinaPadalina(String mjernaJedinica, byte preciznost, String tipSenzora) {
		super(mjernaJedinica, preciznost);
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
		return " Vrijednost padalina: " + this.getVrijednost() + this.getMjernaJedinica() +
				" Broj mjerenja: " + this.tipSenzora;
	}

}
