package hr.java.vjezbe.glavna;

import java.util.TreeMap;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.KolicinaPadalina;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;

public class GlavnaDatoteke {

	public static final String FILE_DRZAVE = "dat//drzave.txt";
	public static final String FILE_GEOTOCKE = "dat//geoTocke.txt";
	public static final String FILE_MJPOSTAJE = "dat//mjernePostaje.txt";
	public static final String FILE_MJESTA = "dat//mjesta.txt";
	public static final String FILE_SENZORI = "dat//senzori.txt";
	public static final String FILE_ZUPANIJE = "dat//zupanije.txt";

	static Map<Integer, Drzava> mapaDrzava = new TreeMap<>();
	static Map<Integer, Zupanija> mapaZupanija = new TreeMap<>();
	static Map<Integer, Mjesto> mapaMjesta = new TreeMap<>();
	static Map<Integer, GeografskaTocka> mapaGeoTocki = new TreeMap<>();
	static Map<Integer, MjernaPostaja> mapaMjPostaja = new TreeMap<>();
	public static Map<Integer, Senzor> mapaSenzora = new TreeMap<>();
	static Map<Integer, List<Senzor>> mapaListeSenzora = new TreeMap<>();

	public static void main(String[] args) {

		dohvatiDrzave();
		dohvatiZupanije();
		dohvatiMjesta();
		dohvatiGeoTocke();
		dohvatiSenzore();
		dohvatiMjPostaje();

		//ispis podataka

		for(int i = 1; i <= mapaMjPostaja.size(); i++) {

			System.out.println(">----------------------------<");

			if(mapaMjPostaja.get(i) instanceof RadioSondaznaMjernaPostaja) {

				ispisRadioSondazneMjernePostaje((RadioSondaznaMjernaPostaja) mapaMjPostaja.get(i));
				System.out.println("Vrijednosti senzora postaje: ");
				mapaMjPostaja.get(i).dohvatiSenzore()
				.forEach(s -> System.out.println(s.dohvatiPodatkeSenzora()));	

			} else if(mapaMjPostaja.get(i) instanceof MjernaPostaja) {

				ispisMjPostaje(mapaMjPostaja.get(i));
				System.out.println("Vrijednosti senzora postaje: ");
				mapaMjPostaja.get(i).dohvatiSenzore()
				.forEach(s -> System.out.println(s.dohvatiPodatkeSenzora()));			

			}
			//Senzor.findSenzorID (i, mapaSenzora);
		}
		System.out.println("____________________________________________________________________________________");

		//Senzor.findSenzorID (10, mapaSenzora);

	}

	/**
	 * dohvatiDrzave
	 */
	public static void dohvatiDrzave() {

		//Otvara drzave.txt i cita
		try( BufferedReader in = new BufferedReader(new FileReader(FILE_DRZAVE))){

			String lineInFile;
			int brojRedova = 3;
			List<String> listaStringova = new ArrayList<>();

			int id = 0;
			String naziv = "";
			BigDecimal povrsina = null;

			//ako naide na null znaci da je kraj file-a
			while((lineInFile = in.readLine()) != null ) {
				listaStringova.add(lineInFile);			
			}

			for(int i = 0; i < listaStringova.size(); i++) {

				String lineInList = listaStringova.get(i); 
				switch(i % brojRedova) {
				case 0: 
					id = Integer.parseInt(lineInList);
					break;
				case 1: 
					naziv = lineInList;
					break;
				case 2:
					povrsina = new BigDecimal(lineInList);
					mapaDrzava.put(id, new Drzava(naziv, povrsina, id ));
					break;
				}
				
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * dohvatiZupanije
	 */
	public static void dohvatiZupanije() {

		try( BufferedReader in = new BufferedReader(new FileReader(FILE_ZUPANIJE))){

			String lineInFile;
			int brojRedova = 2;
			List<String> listaStringova = new ArrayList<>();

			int dodatniID = 1;
			int id = 0;
			String naziv = "";

			//ako naide na null znaci da je kraj file-a
			while((lineInFile = in.readLine()) != null ) {
				listaStringova.add(lineInFile);			
			}

			for(int i = 0; i < listaStringova.size(); i++) {
				String lineInList = listaStringova.get(i); 
				switch(i % brojRedova) {
				case 0: 
					id = Integer.parseInt(lineInList);
					break;
				case 1: 
					naziv = lineInList;
					mapaZupanija.put(id, new Zupanija(naziv, mapaDrzava.get(dodatniID), id));
					dodatniID++;
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * dohvatiMjesta
	 */
	public static void dohvatiMjesta() {

		try( BufferedReader in = new BufferedReader(new FileReader(FILE_MJESTA))){

			String lineInFile;
			int brojRedova = 3;
			List<String> listaStringova = new ArrayList<>();

			int dodatniID = 1;
			int id = 0;
			String naziv = "";
			VrstaMjesta vrstaMjesta = VrstaMjesta.OSTALO;

			while((lineInFile = in.readLine()) != null ) {
				listaStringova.add(lineInFile);			
			}

			for(int i = 0; i < listaStringova.size(); i++) {

				String lineInList = listaStringova.get(i); 

				switch(i % brojRedova) {
				case 0: 
					id = Integer.parseInt(lineInList);
					break;
				case 1: naziv = lineInList;
				break;
				case 2:	
					switch(lineInList) {
					case "GRAD": vrstaMjesta = VrstaMjesta.GRAD;
					break;
					case "SELO": vrstaMjesta = VrstaMjesta.SELO;
					break;
					case "OSTALO": vrstaMjesta = VrstaMjesta.OSTALO;
					break;
					}
					mapaMjesta.put(id, new Mjesto(naziv, mapaZupanija.get(dodatniID), vrstaMjesta, id));
					dodatniID++;
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	/**
	 * dohvatiGeoTocke
	 */
	public static void dohvatiGeoTocke() {

		try( BufferedReader in = new BufferedReader(new FileReader(FILE_GEOTOCKE))){

			String lineInFile;
			int brojRedova = 3;
			List<String> listaStringova = new ArrayList<>();

			int dodatniID = 1;
			int id = 0;
			BigDecimal x = new BigDecimal("0");
			BigDecimal y = new BigDecimal("0");

			while((lineInFile = in.readLine()) != null ) {
				listaStringova.add(lineInFile);			
			}

			for(int i = 0; i < listaStringova.size(); i++) {

				String lineInList = listaStringova.get(i); 

				switch(i % brojRedova) {
				case 0: 
					id = Integer.parseInt(lineInList);
					break;
				case 1:
					x = new BigDecimal(lineInList);
					break;

				case 2: 
					y = new BigDecimal(lineInList);
					mapaGeoTocki.put(id, new GeografskaTocka(x, y));
					dodatniID++;
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	/**
	 * dohvatiSenzore
	 */
	public static void dohvatiSenzore() {

		try( BufferedReader in = new BufferedReader(new FileReader(FILE_SENZORI))){

			String lineInFile;
			int brojRedova = 6;
			List<String> listaStringova = new ArrayList<>();

			int dodatniID = 1;
			int dodatniID2 = 1;
			int brojac = 0;
			int brojac2 = 0;
			int id = 0;
			String mjernaJedinica = "";
			String nazivTemp = "";
			String tipSenzora = "";
			byte preciznost = 0;
			RadSenzora radSenzora = RadSenzora.OSTALO;
			BigDecimal vrijednost = new BigDecimal("0");
			List<Senzor> listaSenzora = new ArrayList<>();

			while((lineInFile = in.readLine()) != null ) {
				listaStringova.add(lineInFile);			
			}

			for(int i = 0; i < listaStringova.size(); i++) {

				String lineInList = listaStringova.get(i); 

				switch(i % brojRedova) {
				case 0: 
					id = Integer.parseInt(lineInList);
					break;
				case 1: 
					mjernaJedinica = lineInList;
					break;		
				case 2:	
					preciznost = Byte.valueOf(lineInList);
					break;		
				case 3: 
					switch(brojac) {
					//temperaturni
					case 0:
						nazivTemp = lineInList;
						break;
						//vlaga	
					case 1:
						break;
						//tlak
					case 2:
						tipSenzora = lineInList;
						break;
					}
					brojac++;
					if (brojac == 3) brojac = 0;
					break;
					//RadSenzora
				case 4: 
					switch(lineInList) {
					case "PING": radSenzora = RadSenzora.PING;
					break;
					case "STREAMING": radSenzora = RadSenzora.STREAMING;
					break;
					case "OSTALO": radSenzora = RadSenzora.OSTALO;
					break;
					}
					break;
				case 5:	
					vrijednost = new BigDecimal(lineInList);

					switch(brojac2) {
					//temperaturni senzor
					case 0:
						mapaSenzora.put(id, new SenzorTemperature(mjernaJedinica, preciznost, nazivTemp, radSenzora, vrijednost, id));
						listaSenzora.add( new SenzorTemperature(mjernaJedinica, preciznost, nazivTemp, radSenzora, vrijednost, id));
						break;
						//vlaga senzor
					case 1:
						mapaSenzora.put(id, new SenzorVlage(mjernaJedinica, preciznost, radSenzora, vrijednost, id));
						listaSenzora.add( new SenzorVlage(mjernaJedinica, preciznost, radSenzora, vrijednost,id));
						break;
						//tlak senzor	
					case 2:
						mapaSenzora.put(id, new KolicinaPadalina(mjernaJedinica, preciznost, tipSenzora, radSenzora, vrijednost, id));
						listaSenzora.add(new KolicinaPadalina(mjernaJedinica, preciznost, tipSenzora, radSenzora, vrijednost, id));
						break;
					}
					dodatniID++;
					brojac2++;
					if(brojac2==3) {
						mapaListeSenzora.put(dodatniID2, listaSenzora);
						listaSenzora = null;
						listaSenzora = new ArrayList<>();
						dodatniID2++;
						brojac2=0;	
					}
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}		
	}

	/**
	 * dohvatiMjPostaje
	 */
	public static void dohvatiMjPostaje() {
		
		try( BufferedReader in = new BufferedReader(new FileReader(FILE_MJPOSTAJE))){

			String lineInFile;
			int brojRedova = 3;
			List<String> listaStringova = new ArrayList<>();

			int dodatniID = 1;
			int id = 0;
			String naziv = "";
			int visinaPostaje = 0;
			
			while((lineInFile = in.readLine()) != null ) {
				listaStringova.add(lineInFile);			
			}

			for(int i = 0; i < listaStringova.size(); i++) {
				
				String lineInList = listaStringova.get(i); 

				switch(i % brojRedova) {
				case 0: 
					id = Integer.parseInt(lineInList);
				break;
				case 1:	
					if(!lineInList.isEmpty()){
						visinaPostaje = Integer.parseInt(lineInList);
					}
					break;
				case 2: naziv = lineInList;

				if(dodatniID<3) {
					mapaMjPostaja.put(id, new MjernaPostaja(naziv, mapaMjesta.get(dodatniID), mapaGeoTocki.get(dodatniID), mapaListeSenzora.get(dodatniID), id));
				}
				else {
					mapaMjPostaja.put(id, new RadioSondaznaMjernaPostaja(naziv, mapaMjesta.get(dodatniID), mapaGeoTocki.get(dodatniID), mapaListeSenzora.get(dodatniID), visinaPostaje, id));
				}


				//TESTIRANJE sto se nalazi u mapiDrzava
				/*
							System.out.println("brojac (id): " +dodatniID +" -- " + mapaMjernihPostaja.get(dodatniID).getId() +
													" " + mapaMjernihPostaja.get(dodatniID).getNaziv() +" "+
													mapaMjernihPostaja.get(dodatniID).getMjesto().getNaziv()+
													" " + mapaMjernihPostaja.get(dodatniID).getMjesto().getZupanija().getNaziv()
													);

							mapaMjernihPostaja.get(dodatniID).dohvatiSenzore().forEach(s -> System.out.println(s.dohvatiPodatkeSenzora()));
				 */
				dodatniID++;
				break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Metoda za ispis mjerne postaje 
	 * @param mjernaPostaja
	 */

	public static void ispisMjPostaje(MjernaPostaja mjPostaja) {

		System.out.println("Naziv mjerne postaje: " + mjPostaja.getNaziv());

		System.out.format("Postaja se nalazi u mjestu %s (%s), zupanija %s, drzava %s \n",
				mjPostaja.getMjesto().getNaziv(), 
				mjPostaja.getMjesto().getVrstaMjesta(),
				mjPostaja.getMjesto().getZupanija().getNaziv(),
				mjPostaja.getMjesto().getZupanija().getDrzava().getNaziv());

		System.out.println("Tocne koordinate postaje su X: " + mjPostaja.getGeografskaTocka().getX() +
				" Y: " + mjPostaja.getGeografskaTocka().getY());

	}

	/**
	 * Metoda za ispis RadioSondazne mjerne postaje
	 * @param mjernaPostaja
	 */

	public static void ispisRadioSondazneMjernePostaje(RadioSondaznaMjernaPostaja mjPostaja) {


		System.out.println("Naziv radio sondazne mjerne postaje: " + mjPostaja.getNaziv());

		System.out.println("Postaja je radio sondazna.");

		System.out.println("Visina radio sondazne mjerne postaje: " + mjPostaja.dohvatiVisinuPostaje());

		System.out.format("Postaja se nalazi na mjestu %s (%s), zupanija %s, drzava %s \n",
				mjPostaja.getMjesto().getNaziv(),
				mjPostaja.getMjesto().getVrstaMjesta(),
				mjPostaja.getMjesto().getZupanija().getNaziv(),
				mjPostaja.getMjesto().getZupanija().getDrzava().getNaziv());

		System.out.println("Tocne koordinate postaje su X: " + mjPostaja.getGeografskaTocka().getX() +
				" Y: " + mjPostaja.getGeografskaTocka().getY());

		mjPostaja.povecajVisinu( mjPostaja.dohvatiVisinuPostaje() );
	}
}
