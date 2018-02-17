package hr.java.vjezbe.entitet;

import java.util.List;

public class RadioSondaznaMjernaPostaja extends MjernaPostaja implements RadioSondazna {

	private int visinaPostaje;

	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, 
			GeografskaTocka geografskaTocka, List<Senzor> listaSenzora, int visinaPostaje ) {
		super(naziv, mjesto, geografskaTocka, listaSenzora );

		this.visinaPostaje = visinaPostaje;
	}
	
	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, 
			GeografskaTocka geografskaTocka, List<Senzor> listaSenzora, int visinaPostaje, int id ) {
		super(naziv, mjesto, geografskaTocka, listaSenzora );
		super.setId(id);
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
