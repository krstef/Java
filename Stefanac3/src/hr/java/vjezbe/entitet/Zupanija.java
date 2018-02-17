package hr.java.vjezbe.entitet;

public class Zupanija {
	
	private String naziv;
	private Drzava drzava;
	
	public Zupanija(String naziv, Drzava drzava) {
		super();
		this.naziv = naziv;
		this.drzava = drzava;
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

}
