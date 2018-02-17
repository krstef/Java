package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Zupanija {
	
	private String naziv;
	private Drzava drzava;
	private List<Mjesto> listaMjesta;
	
	public Zupanija(String naziv, Drzava drzava) {
		super();
		this.naziv = naziv;
		this.drzava = drzava;
		this.listaMjesta = new ArrayList<>();
	}
	
	public Zupanija(String naziv, Drzava drzava, List<Mjesto> listaMjesta) {
		this.naziv = naziv;
		this.drzava = drzava;
		this.listaMjesta = listaMjesta;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}

	public Drzava getDrzava() {
		return drzava;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	/**
	 * Vraca listu svih mjesta unutar zupanije.
	 * @return
	 */
	public List<Mjesto> getListaMjesta(){
		return listaMjesta;
	}

}
