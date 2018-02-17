package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Mjesto {
	
	private String naziv;
	private Zupanija zupanija;
	private VrstaMjesta vrstaMjesta;
	private List<MjernaPostaja> listaMjernaPostaja;
	
	public Mjesto(String naziv, Zupanija zupanija) {
		super();
		this.naziv = naziv;
		this.zupanija = zupanija;
	}
	
	/**
	 * Konstruktor mjesta za kojeg je potrebno unjeti naziv, zupaniju i vrstu mjesta.
	 * Sam inicijalizira novu listu za mjerne postaje!
	 * @param naziv
	 * @param zupanija
	 * @param vrstaMjesta
	 */
	public Mjesto(String naziv, Zupanija zupanija, VrstaMjesta vrstaMjesta) {
		super();
		this.naziv = naziv;
		this.zupanija = zupanija;
		this.vrstaMjesta = vrstaMjesta;
		this.listaMjernaPostaja = new ArrayList<>();
	}
	
	/**
	 * Konstruktor mjesta za kojeg je potrebno unjeti naziv, zupaniju, vrstu mjesta i listu mjernih postaja.
	 * @param naziv
	 * @param zupanija
	 * @param vrstaMjesta
	 * @param listaMjernihPostaja
	 */
	public Mjesto(String naziv, Zupanija zupanija, VrstaMjesta vrstaMjesta, List<MjernaPostaja> listaMjernaPostaja) {
		super();
		this.naziv = naziv;
		this.zupanija = zupanija;
		this.vrstaMjesta = vrstaMjesta;
		this.listaMjernaPostaja=listaMjernaPostaja;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public Zupanija getZupanija() {
		return zupanija;
	}


	public void setZupanija(Zupanija zupanija) {
		this.zupanija = zupanija;
	}

	public VrstaMjesta getVrstaMjesta() {
		return vrstaMjesta;
	}

	public void setVrstaMjesta(VrstaMjesta vrstaMjesta) {
		this.vrstaMjesta = vrstaMjesta;
	}

	public List<MjernaPostaja> getListaMjernaPostaja() {
		return listaMjernaPostaja;
	}

}
