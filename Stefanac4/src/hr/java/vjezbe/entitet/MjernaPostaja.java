package hr.java.vjezbe.entitet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MjernaPostaja {

	private String naziv;
	private Mjesto mjesto;
	private GeografskaTocka geografskaTocka;
	List<Senzor> listaSenzora;
	
	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, List<Senzor> listaSenzora) {
		super();
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
		this.listaSenzora = listaSenzora;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Mjesto getMjesto() {
		return mjesto;
	}

	public void setMjesto(Mjesto mjesto) {
		this.mjesto = mjesto;
	}

	public GeografskaTocka getGeografskaTocka() {
		return geografskaTocka;
	}

	public void setGeografskaTocka(GeografskaTocka geografskaTocka) {
		this.geografskaTocka = geografskaTocka;
	}
	
	/**
	 * Metoda za dohvacanje senzora
	 * @return polje senzora
	 */
	public List<Senzor> dohvatiSenzore() {
		
		//sortiranje senzora po mjernim jedinicama
		
		Collections.sort(
				listaSenzora, 
				(p1, p2) ->  p1.getMjernaJedinica().compareTo( p2.getMjernaJedinica() ) 
				);
		
		return listaSenzora;
	}
}
