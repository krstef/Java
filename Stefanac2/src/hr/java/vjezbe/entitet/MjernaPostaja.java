package hr.java.vjezbe.entitet;

import java.util.Arrays;

public class MjernaPostaja {

	private String naziv;
	private Mjesto mjesto;
	private GeografskaTocka geografskaTocka;
	Senzor [] senzori;
	
	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzor[] senzori) {
		super();
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
		this.senzori = senzori;
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
	
	public Senzor [] dohvatiSenzore() {
		
		//sortiranje senzora po mjernim jedinicama
		
		Arrays.sort (senzori, (senzor1, senzor2) -> senzor1.getMjernaJedinica().
				compareTo(senzor2.getMjernaJedinica()));
		
		return senzori;
	}
	
	
}
