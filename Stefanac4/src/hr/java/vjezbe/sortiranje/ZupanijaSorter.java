package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Zupanija;

public class ZupanijaSorter implements Comparator <Zupanija>{

	@Override
	public int compare(Zupanija z1, Zupanija z2) {
		
		return z1.getNaziv().compareTo(z2.getNaziv());
	}

}
