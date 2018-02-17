package hr.java.vjezbe.javaFX;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
		
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
	
	private static BorderPane root;
	private Stage primaryStage;
	
	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("PocetniEkran.fxml"));
			Scene scene = new Scene(root,400,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setCenterPane(BorderPane centerPane) {
	 	root.setCenter(centerPane);
}
	
	public static void main(String[] args) { 
		launch(args);
		
		/*dohvatiDrzave();
		dohvatiZupanije();
		dohvatiMjesta();
		dohvatiGeoTocke();
		dohvatiSenzore();
		dohvatiMjPostaje();*/
	}
	
	/**
	 * dohvatiDrzave
	 */
	public static List<Drzava> dohvatiDrzave() {

		List<Drzava> listaDrzava = new ArrayList<>();
		
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
					listaDrzava.add(new Drzava(naziv, povrsina, id));
					break;
				}
				
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		
		return listaDrzava;
	}

	/**
	 * dohvatiZupanije
	 */
	public static List<Zupanija> dohvatiZupanije() {
		
		List<Zupanija> listaZupanija = new ArrayList<>();
		List<Drzava> listaDrzava = dohvatiDrzave();

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
					mapaZupanija.put(id, new Zupanija(naziv, listaDrzava.get(dodatniID-1), id));
					listaZupanija.add(new Zupanija(naziv, listaDrzava.get(dodatniID-1), id));
					dodatniID++;
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		
		return listaZupanija;
	}

	/**
	 * dohvatiMjesta
	 */
	public static List<Mjesto> dohvatiMjesta() {

		List<Mjesto> listaMjesta = new ArrayList<>();
		
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
					
					listaMjesta.add(new Mjesto(naziv, mapaZupanija.get(dodatniID), vrstaMjesta, id));
					dodatniID++;
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		return listaMjesta;
	}

	/**
	 * dohvatiGeoTocke
	 */
	public static List<GeografskaTocka> dohvatiGeoTocke() {

		List<GeografskaTocka> listaGeoTocki = new ArrayList<>();
		
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
					
					listaGeoTocki.add( new GeografskaTocka(x, y));
					dodatniID++;
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return listaGeoTocki;
	}

	/**
	 * dohvatiSenzore
	 */
	public static List<Senzor> dohvatiSenzore() {
		
		List<Senzor> novaListaSenzora = new ArrayList<>();

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
						novaListaSenzora.add(new SenzorTemperature(mjernaJedinica, preciznost, nazivTemp, radSenzora, vrijednost, id));
						break;
						//vlaga senzor
					case 1:
						mapaSenzora.put(id, new SenzorVlage(mjernaJedinica, preciznost, radSenzora, vrijednost, id));
						listaSenzora.add( new SenzorVlage(mjernaJedinica, preciznost, radSenzora, vrijednost,id));
						novaListaSenzora.add(new SenzorVlage(mjernaJedinica, preciznost, radSenzora, vrijednost,id));						break;
						//tlak senzor	
					case 2:
						mapaSenzora.put(id, new KolicinaPadalina(mjernaJedinica, preciznost, tipSenzora, radSenzora, vrijednost, id));
						listaSenzora.add(new KolicinaPadalina(mjernaJedinica, preciznost, tipSenzora, radSenzora, vrijednost, id));
						novaListaSenzora.add(new KolicinaPadalina(mjernaJedinica, preciznost, tipSenzora, radSenzora, vrijednost, id));
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
		
		return novaListaSenzora;
	}

	/**
	 * dohvatiMjPostaje
	 */
	public static List<MjernaPostaja> dohvatiMjPostaje() {
		
		List<MjernaPostaja> listaMjernihPostaja = new ArrayList<>();
		List<GeografskaTocka> listaGeoTocki = dohvatiGeoTocke();
		
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
					listaMjernihPostaja.add(new MjernaPostaja(naziv, mapaMjesta.get(dodatniID), listaGeoTocki.get(dodatniID -1), mapaListeSenzora.get(dodatniID), id));
				}
				else {
					mapaMjPostaja.put(id, new RadioSondaznaMjernaPostaja(naziv, mapaMjesta.get(dodatniID), mapaGeoTocki.get(dodatniID), mapaListeSenzora.get(dodatniID), visinaPostaje, id));
					listaMjernihPostaja.add(new RadioSondaznaMjernaPostaja(naziv, mapaMjesta.get(dodatniID), listaGeoTocki.get(dodatniID -1), mapaListeSenzora.get(dodatniID), visinaPostaje, id));
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
		
		return listaMjernihPostaja;
	}

}
