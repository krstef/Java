package hr.java.vjezbe.entitet;

public class RadioSondaznaMjernaPostaja extends MjernaPostaja implements RadioSondazna {

	private int visinaPostaje;

	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, 
			GeografskaTocka geografskaTocka, Senzor[] senzori, int visinaPostaje ) {
		super(naziv, mjesto, geografskaTocka, senzori );

		this.visinaPostaje = visinaPostaje;
	}

	//implementiranje metoda naslijedenih od sucelja

	//metoda za postavljanje visine

	@Override
	public void podesiVisinuPostaje (int visinaPostaje) {
		this.visinaPostaje = visinaPostaje;
	}

	//metoda za dohvacanje visine

	@Override
	public int dohvatiVisinuPostaje() {
		return this.visinaPostaje;
	}
}
