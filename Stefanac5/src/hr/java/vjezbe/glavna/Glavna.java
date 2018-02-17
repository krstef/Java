package hr.java.vjezbe.glavna;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.SynchronousQueue;

//import org.slf4j.Logger; 
//import org.slf4j.LoggerFactory; 

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;
import hr.java.vjezbe.sortiranje.ZupanijaSorter;
import hr.java.vjezbe.entitet.KolicinaPadalina;

/**
 * Klasa Glavna u kojoj se izvršava aplikacija 
 */
public class Glavna {

	static final int SVE_MJERNE_POSTAJE = 2;
	static final int RADIOSONDAZNE_POSTAJE = 1;
	static final int KLASICNE_POSTAJE = 1;

	static final int BROJ_SENZORA = 3;
	/*
	 * 1 - Deklariranje reference input koja je tipa Scanner
	 * 2 - new Scanner stvara novi objekt tipa Scanner
	 * 3 - Novonastali objekt pridružeje se referenci input
	 */
	public static Scanner input = new Scanner(System.in);


	//	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	static int inputBrojac = 0;
	
//dodan glupi komentar samo da vidim updatea li se
	/**
	 * Main metoda
	 * @param args
	 */
	public static void main(String[] args) {

		Zupanija zupanija;
		Mjesto mjesto;
		GeografskaTocka geoTocka;

		List<Senzor> listaSenzora = new ArrayList<> ();
		List<MjernaPostaja> listaMjernaPostaja = new ArrayList<> ();

		//		logger.info("Aplikacija je pokrenuta!");

		for (int i = 0; i < SVE_MJERNE_POSTAJE; i++) {

			System.out.format("Unesite " + (i+1) + " mjernu postaju \n"); 

			/* drzava se unosi u zupaniji 
			drzava = unosDrzave (input, i+1) ;
			 */
			zupanija = unosZupanije (input, listaMjernaPostaja);
			mjesto = unosMjesta (input, zupanija, listaMjernaPostaja);
			geoTocka = unostTocke (input, i+1);

			listaSenzora = unosSenzora(input);

			if (i < KLASICNE_POSTAJE)			
				listaMjernaPostaja.add(unosMjPostaje(input, mjesto, geoTocka, listaSenzora, listaMjernaPostaja));
			else listaMjernaPostaja.add(unosRadioSondazneMjernePostaje(input, mjesto, geoTocka, listaSenzora));

			inputBrojac++;
		} 

		//ispis mjernih postaja

		//		logger.info("Ispis mjernih postaja.");

		for (int j = 0; j < SVE_MJERNE_POSTAJE; j++) {

			System.out.println("<------------------------>");

			if(listaMjernaPostaja.get(j) instanceof RadioSondaznaMjernaPostaja) {

				ispisRadioSondazneMjernePostaje((RadioSondaznaMjernaPostaja) listaMjernaPostaja.get(j));
				System.out.println ("Vrijednost senzora postaje: ");

				for(int k = 0; k < listaMjernaPostaja.get(j).dohvatiSenzore().size(); k++)
					System.out.println(listaMjernaPostaja.get(j).dohvatiSenzore().get(k).dohvatiPodatkeSenzora());
			}

			else if (listaMjernaPostaja.get(j) instanceof MjernaPostaja) {
				ispisMjPostaje( listaMjernaPostaja.get(j));
				System.out.println("Vrijednost senzora postaje: ");

				for (int m = 0; m < listaMjernaPostaja.get(j).dohvatiSenzore().size(); m++)
					System.out.println(listaMjernaPostaja.get(j).dohvatiSenzore().get(m).dohvatiPodatkeSenzora());
			}

		}

		//		logger.info("Prikaz vrijednosti temperaturnih senzora.");
		//beskonacna petlja za generiranje vrijednosti senzora
		/*	for(;;) {

			System.out.println("______________________________________");

			for (int i = 0; i < mjPostaja.length; i++) {
				for (int j = 0; j < mjPostaja[i].dohvatiSenzore().length;j++) {

					try {
						if(mjPostaja[i].dohvatiSenzore()[j] instanceof SenzorTemperature) {
							((SenzorTemperature) mjPostaja[i].dohvatiSenzore()[j]).generirajVrijednost();
							System.out.println("Postaja: "+ mjPostaja[i].getNaziv() + ", Temperatura - OK! Temp iznosi: "+ mjPostaja[i].dohvatiSenzore()[j].getVrijednost());
//							logger.info("Temp je u redu! Vrijednost temp: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost());
						}
					}catch (VisokaTemperaturaException e) {
						System.out.println("Postaja: "+ mjPostaja[i].getNaziv()
								+", VISOKA TEMPERATURA! Temperatura postaje: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost());

//						logger.error("Temp previsoka! Vrijednost temp: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost(), e);

					}catch (NiskaTemperaturaException e) {
						System.out.println("Postaja: "+ mjPostaja[i].getNaziv()
								+", NISKA TEMPERATURA! Temperatura postaje je: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost());

//						logger.error("Temp preniska! Vrijednost temp: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost(), e);
					}
			}
		}

		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();

		}
	} */

		//stvaranje liste zupanija setiranih abecedno i ispis

		System.out.println("\n<------------------------------------------->"
				+ "\n---> ISPIS ZUPANIJA PO ABECEDNOM REDU <---");
		SortedSet<Zupanija> setZupanijaAbecedno = new TreeSet<>(new ZupanijaSorter());

		//for petlja u kojoj popunimo TreeSet(bez duplikata)
		for(int k = 0; k < listaMjernaPostaja.size(); k++) {
	
			//setZupanijaAbecedno.add(listaMjernaPostaja.get(k).getMjesto().getZupanija());
			setZupanijaAbecedno.addAll(listaMjernaPostaja.get(k).getMjesto().getZupanija().getDrzava().getListaZupanija());
		}

		setZupanijaAbecedno.stream().forEach(nazivZupanije -> System.out.println(
				nazivZupanije.getNaziv()));

		//stvaranje mape koja sadrzi popis senzora u svakom mjestu
		System.out.println("\n<------------------------------------------->"
				+ "\n---> ISPIS MJESTA I NJIHOVIH SENZORA <---");

		Map<String, List<String>> mapaMjesta = new TreeMap<>();

		for (int k = 0; k < listaMjernaPostaja.size(); k++) {

			List<String> listaImenaSenzora = new ArrayList<>();

			if(!mapaMjesta.containsKey(listaMjernaPostaja.get(k).getMjesto().getNaziv())) {

				for(int j = 0; j < listaMjernaPostaja.get(k).dohvatiSenzore().size(); j++) {
					listaImenaSenzora.add(listaMjernaPostaja.get(k).dohvatiSenzore().get(j).dohvatiImeSenzora());
				}

				mapaMjesta.put(listaMjernaPostaja.get(k).getMjesto().getNaziv(), listaImenaSenzora);
			} else continue;	
		}
		
		for (Entry <String, List<String>> entry: mapaMjesta.entrySet()) {

			String key = entry.getKey();
			List<String> vrijednost = entry.getValue();

			System.out.println("U mjestu " + key + " nalaze se senzori: \n" +
					vrijednost);
		} 

		/*	for(String key : mapaMjesta.keySet()) {
			System.out.println(key + "\n");
		}
		//System.out.println(mapaMjesta); */

	}

	/**
	 * Metoda za unos RadioSondazneMjernePostaje
	 * @param input
	 * @param mjesto
	 * @param geoTocka
	 * @param senzori
	 * @return novi objekt RadioSondaznaMjernaPostaja
	 */
	private static MjernaPostaja unosRadioSondazneMjernePostaje(Scanner input, Mjesto mjesto, GeografskaTocka geoTocka,
			List<Senzor> listaSenzora) {
		System.out.println("\nUpisite visinu radio sondazne mjerne postaje: ");
		int visinaPostaje = intInput();
		input.nextLine();
		//logger.info("Unos naziva radiosondazne mjerne postaje:" + naziv);

		System.out.println("Upisite naziv radio sondazne mjerne postaje: ");
		String naziv = input.nextLine();
		//	logger.info("Unos naziva radiosondazne mjerne postaje:" + naziv);

		return new RadioSondaznaMjernaPostaja(naziv, mjesto, geoTocka, listaSenzora, visinaPostaje);
	}

	/**
	 * Metoda za ispis RadioSondazneMjernePostaje
	 * @param radioSondaznaMjernaPostaja
	 */
	private static void ispisRadioSondazneMjernePostaje(RadioSondaznaMjernaPostaja radioSondaznaMjernaPostaja) {

		System.out.println("Naziv radio sondazne mjerne postaje: " + radioSondaznaMjernaPostaja.getNaziv());

		System.out.println("Postaja je radio sondazna");

		System.out.println("Visina radio sondazne mjerne postaje: " + radioSondaznaMjernaPostaja.dohvatiVisinuPostaje());

		System.out.format("Postaja se nalazi na mjestu %s (%s), zupaniji %s, drzavi %s \n",
				radioSondaznaMjernaPostaja.getMjesto().getNaziv(),
				radioSondaznaMjernaPostaja.getMjesto().getVrstaMjesta(),
				radioSondaznaMjernaPostaja.getMjesto().getZupanija().getNaziv(),
				radioSondaznaMjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());

		System.out.println("Tocne koordinate postaje su x: " + radioSondaznaMjernaPostaja.getGeografskaTocka().getX() +
				" y: " + radioSondaznaMjernaPostaja.getGeografskaTocka().getY());

		radioSondaznaMjernaPostaja.povecajVisinu( radioSondaznaMjernaPostaja.dohvatiVisinuPostaje() );

	}

	/**
	 * Metoda za unos senzora 
	 * @param input
	 * @return polje senzori
	 */
	private static List<Senzor> unosSenzora(Scanner input) {

		List<Senzor> listaSenzora = new ArrayList<>();

		/* Senzor temperature */
		System.out.println("Unesite naziv senzora temperature:");
		String nazivTemp = input.nextLine();

		System.out.println("Unesite vrijednost senzora temperature: ");
		BigDecimal vrijednostTemp = bigDecimalInput();
		//	logger.info("Podaci za senzor temperature:" + nazivTemp + ", " + vrijednostTemp);

		System.out.println("Odaberite nacin rada senzora temperature:");
		RadSenzora radSenTemp = odabirRadaSenzora();

		/* Senzor vlage */
		System.out.println("Unesite vrijednost senzora vlage:");
		BigDecimal vrijednostVlaga = bigDecimalInput();
		//	logger.info("Podaci za senzor vlage:" + vrijednostVlaga);

		System.out.println("Odaberite nacin rada senzora vlage:");
		RadSenzora radSenVlage = odabirRadaSenzora();

		/* Senzor padalina */
		System.out.println("Unesite tip senzora padalina:");
		String tipSenzora = input.nextLine();

		System.out.println("Unesite vrijednost senzora padalina:");
		BigDecimal vrijednostPad = bigDecimalInput();
		//	logger.info("Podaci za senzor padalina:" + tipSenzora + ", " + vrijednostPad);

		System.out.println("Odaberite nacin rada senzora padalina:");
		RadSenzora radSenPad = odabirRadaSenzora();

		byte preciznostTemp = 2;
		SenzorTemperature senzorTemperature= new SenzorTemperature("°C", preciznostTemp, nazivTemp );
		senzorTemperature.setVrijednost(vrijednostTemp);
		senzorTemperature.setRadSenzora(radSenTemp);

		byte preciznostVlaga = 2;
		SenzorVlage senzorVlage = new SenzorVlage("%", preciznostVlaga);
		senzorVlage.setVrijednost(vrijednostVlaga);
		senzorVlage.setRadSenzora(radSenVlage);

		byte preciznostPad = 2;
		KolicinaPadalina senzorPadalina = new KolicinaPadalina("L/m2", preciznostPad, tipSenzora);
		senzorPadalina.setVrijednost(vrijednostPad);
		senzorPadalina.setRadSenzora(radSenPad);

		listaSenzora.add(senzorTemperature);
		listaSenzora.add(senzorVlage);
		listaSenzora.add(senzorPadalina);

		return listaSenzora;
	}

	/**
	 * Metoda za ispis MjernePostaje
	 * @param mjernaPostaja
	 */
	private static void ispisMjPostaje(MjernaPostaja mjernaPostaja) {

		System.out.println("Naziv mjerne postaje: " + mjernaPostaja.getNaziv());

		System.out.format("Postaja se nalazi u mjestu %s (%s), zupanija %s, drzava %s \n",
				mjernaPostaja.getMjesto().getNaziv(),
				mjernaPostaja.getMjesto().getVrstaMjesta(),
				mjernaPostaja.getMjesto().getZupanija().getNaziv(),
				mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());

		System.out.println("Tocne koordinate postaje su x: " + mjernaPostaja.getGeografskaTocka().getX() +
				" y: " + mjernaPostaja.getGeografskaTocka().getY());
	}

	/**
	 * Metoda za unos mjerne postaje
	 * @param input
	 * @param mjesto
	 * @param geoTocka
	 * @param senzori
	 * @param i
	 * @return novi objekt MjernaPostaja
	 */
	private static MjernaPostaja unosMjPostaje(Scanner input, Mjesto mjesto, GeografskaTocka geoTocka, List<Senzor> listaSenzora, List<MjernaPostaja> listaMjernaPostaja) {

		System.out.println("\nUpisite naziv mjerne postaje: ");
		String naziv = input.nextLine(); 

		//logger.info("Unos naziva mjerne postaje:" + naziv);

		//Provjerava postoji li unesena zupanija, ako postoji linka ih, iste su reference
		for(int i = 0; i < inputBrojac; i++) {

			if(mjesto.getNaziv().equals(listaMjernaPostaja.get(i).getMjesto().getNaziv())){

				mjesto = listaMjernaPostaja.get(i).getMjesto();
			}

		}

		final Mjesto finalMjesto = mjesto;


		//DODANO
		//Ispituje postoji li mjerna postaja s unesenim mjestom
		Optional<MjernaPostaja> postaja = listaMjernaPostaja.stream().filter(
				p -> p.getMjesto().getNaziv().equals(finalMjesto.getNaziv())).findFirst();   //pronalazi da li vec postoji naziv drzave, ako ga ima onda je Present

		//ako mjesto postoji
		if(postaja.isPresent()) {

			//Provjeri da li je naziv mjerne postaje isti
			Optional<MjernaPostaja> postaja2 = listaMjernaPostaja.stream().filter(
					p -> p.getNaziv().equals(naziv)).findFirst();
			System.out.println("Uneseno mjesto vec postoji!");

			//ako je isti naziv mjerne postaje returnaj istu mjernu postaju
			if(postaja2.isPresent()) {
				System.out.println("Vraca postojece mjesto: " + postaja.get().getMjesto().getNaziv());
				postaja.get().getMjesto().getListaMjernaPostaja().forEach(mjernaPostaja -> System.out.println( "IMA POSTAJU: " + mjernaPostaja.getNaziv()));
				return postaja.get();   //ako postaja vec postoji onda tu istu returna, da nemamo duplikate
			}
		}


		//ako zupanija postoji, no novo je mjesto, onda dodaj mjeesto u linkanu zupaniju
		MjernaPostaja mjernaPostaja = new MjernaPostaja(naziv, finalMjesto, geoTocka, listaSenzora);

		finalMjesto.getListaMjernaPostaja().add(mjernaPostaja);

		System.out.println("U mjestu: "+ mjesto.getNaziv());
		mjesto.getListaMjernaPostaja().forEach(mpostaja -> System.out.println( "nalazi se mjerna postaja: " + mpostaja.getNaziv())); 


		return mjernaPostaja;
	}

	/**
	 * Metoda za unos Geografske tocke
	 * @param input
	 * @param i
	 * @return novi objekt GeografskaTocka
	 */
	private static GeografskaTocka unostTocke(Scanner input, int i) {

		System.out.println("\nUpisite koordinatu X:");
		BigDecimal x = bigDecimalInput();
		//	logger.info("Unesena tocka X: "+ x);
		System.out.println("\nUpisite Geo koordinatu Y:");
		BigDecimal y = bigDecimalInput();
		//	logger.info("Unesena tocka Y: "+ y);

		return new GeografskaTocka(x, y);
	}

	/**
	 * Metoda za unos Mjesta
	 * @param input
	 * @param zupanija
	 * @param i
	 * @return novi objekt Mjesto
	 */
	private static Mjesto unosMjesta(Scanner input, Zupanija zupanija, List<MjernaPostaja> listaMjernaPostaja) {

		//Provjerava postoji li unesena zupanija, ako postoji linka ih, iste su reference
		for(int i = 0; i < inputBrojac; i++) {

			if(zupanija.getNaziv().equals(listaMjernaPostaja.get(i).getMjesto().getZupanija().getNaziv())){

				zupanija = listaMjernaPostaja.get(i).getMjesto().getZupanija();
			}

		}

		final Zupanija finalZupanija = zupanija;


		//logger.info("Unos Mjesta.");
		System.out.println("Unesite naziv mjesta:");
		String naziv = input.nextLine();
		//logger.info("Unos naziva mjesta:" + naziv);

		//dio koda za odabir enumeracije VrsteMjesta
		VrstaMjesta vrstaMjesta = odabirVrsteMjesta();

		//DODANO
		//Ispituje postoji li mjerna postaja s unesenom zupanijom
		Optional<MjernaPostaja> postaja = listaMjernaPostaja.stream().filter(
				p -> p.getMjesto().getZupanija().getNaziv().equals(finalZupanija.getNaziv())).findFirst();   //pronalazi da li vec postoji naziv drzave, ako ga ima onda je Present

		//ako zupanija postoji
		if(postaja.isPresent()) {

			//Provjeri da li je naziv mjesta isti
			Optional<MjernaPostaja> postaja2 = listaMjernaPostaja.stream().filter(
					p -> p.getMjesto().getNaziv().equals(naziv)).findFirst();
			System.out.println("POSTOJI VEC UNESENA ZUPANIJA!");

			//ako je isti naziv mjesta returnaj isto mjesto
			if(postaja2.isPresent()) {
				System.out.println("Vraca postojecu zupaniju: " + postaja.get().getMjesto().getZupanija().getNaziv());
				postaja.get().getMjesto().getZupanija().getListaMjesta().forEach(mjesto -> System.out.println( "IMA MJESTO: " + mjesto.getNaziv()));
				return postaja.get().getMjesto();   //ako mjesto vec postoji onda tu istu returna, da nemamo duplikate
			}
		}

		//ako zupanija postoji, no novo je mjesto, onda dodaj mjeesto u linkanu zupaniju
		Mjesto mjesto = new Mjesto(naziv, finalZupanija, vrstaMjesta);

		finalZupanija.getListaMjesta().add(mjesto);

		System.out.println("U Zupaniji: "+ zupanija.getNaziv());
		zupanija.getListaMjesta().forEach(mjestoUListi -> System.out.println( " nalazi se: " + mjestoUListi.getNaziv())); 


		return mjesto; 
	}

	/**
	 * Metoda za unos Zupanije
	 * @param input
	 * @param drzava
	 * @param i
	 * @return novi objekt Zupanija
	 */
	private static Zupanija unosZupanije(Scanner input, List<MjernaPostaja> listaMjernihPostaja) {

		Drzava drzava = unosDrzave(input);

		//Provjerava postoji li unesena drzava, ako postoji linka ih, iste su reference
		for(int i = 0; i < inputBrojac; i++) {

			if(drzava.getNaziv().equals(listaMjernihPostaja.get(i).getMjesto().getZupanija().getDrzava().getNaziv())){

				drzava = listaMjernihPostaja.get(i).getMjesto().getZupanija().getDrzava();
			}

		}

		final Drzava finalDrzava = drzava;

		System.out.println("Upisite naziv zupanije:");
		String naziv = input.nextLine();
		//logger.info("Unos naziva zupanije:" + naziv);

		//Ispituje postoji li mjerna postaja s unesenom drzavom
		Optional<MjernaPostaja> postaja = listaMjernihPostaja.stream().filter(
				p -> p.getMjesto().getZupanija().getDrzava().getNaziv().equals(finalDrzava.getNaziv())).findFirst();   //pronalazi da li vec postoji naziv drzave, ako ga ima onda je Present

		//ako drzava postoji
		if(postaja.isPresent()) {

			//Provjeri da li je naziv zupanije isti
			Optional<MjernaPostaja> postaja2 = listaMjernihPostaja.stream().filter(
					p -> p.getMjesto().getZupanija().getNaziv().equals(naziv)).findFirst();
			//System.out.println("POSTOJI VEC UNESENA DRZAVA!");

			//ako je isti naziv zupanije returnaj istu zupaniju 
			if(postaja2.isPresent()) {
				//System.out.println("Vraca postojecu drzavu: " + postaja.get().getMjesto().getZupanija().getDrzava().getNaziv());
				//postaja.get().getMjesto().getZupanija().getDrzava().getListaZupanija().forEach(zup -> System.out.println( "IMA ZUPANIJU: " + zup.getNaziv()));
				return postaja.get().getMjesto().getZupanija();   //ako zupanija vec postoji onda tu istu returna, da nemamo duplikate
			}
		}

		//ako drzava postoji, no nova je zupanija, onda dodaj zupaniju u linkanu drzavu
		Zupanija zupanija = new Zupanija(naziv, drzava);
		drzava.getListaZupanija().add(zupanija);


		//System.out.println("UNUTAR DRZAVE: "+ drzava.getNaziv());
		//drzava.getListaZupanija().forEach(zup -> System.out.println( "IMA ZUPANIJU: " + zup.getNaziv())); 

		return zupanija; 		//Zupanija(String naziv, Drzava drzava)
	} 

	/**
	 * Metoda za unos drzave
	 * @param input
	 * @param i
	 * @return novi objekt Drzava
	 */
	private static Drzava unosDrzave(Scanner input) {

		System.out.println("\nUnesite naziv drzave:");
		String naziv = input.nextLine();
		//	logger.info("Uneseni naziv drzave: "+ naziv);
		System.out.println("\nUnesite povrsinu drzave:");
		BigDecimal povrsina = bigDecimalInput();
		//	logger.info("Unesena povrsina drzave: "+ naziv);

		return new Drzava(naziv, povrsina, new ArrayList<Zupanija>()); 

	} 

	/**
	 * Metoda za provjeru unosa podatka u formatu BigDecimal
	 * @return ispravan unos
	 */
	static BigDecimal bigDecimalInput() {

		BigDecimal num = null;
		boolean flag = false;

		do {
			try {
				num = new BigDecimal (input.nextLine());
				flag = true;
			} 
			catch(NumberFormatException e) {
				System.out.println("Krivi tip podatka! Unesite broj!");
				//			logger.error("Krivi tip podatka. Upisan je podatak: "+ num, e);
				flag = false;
			}
		}
		while(!flag);

		return num;
	}

	/**
	 * Metoda za provjeru unosa podatka u formatu integer
	 * @return ispravan unos
	 */
	static int intInput() {

		int num = 0;
		boolean flag = false;

		do {
			try {
				num = input.nextInt();
				// check this one !!!!!!!! input.nextLine();
				flag = true;
			}
			catch (NumberFormatException e) {

				System.out.println("Krivi tip podatka! Unesite broj!");
				//			logger.error("Krivi tip podatka. Upisan je podatak: "+ num, e);
				input.nextLine();
				flag = false;
			}
			catch (InputMismatchException e) {

				System.out.println("Krivi unos broja! Unesite cijeli broj!");
				//			logger.error("Krivi unos broja. Upisan je broj: "+ num, e);
				input.nextLine();
				flag = false;
			} 
		}
		while(!flag);

		return num;
	}

	/**
	 * Metoda za odabir vrste mjesta
	 * @return odabranu vrstu mjesta
	 */
	static VrstaMjesta odabirVrsteMjesta() {

		for (int i = 0; i < VrstaMjesta.values().length - 1; i++) {  
			System.out.println((i + 1) + ". " + VrstaMjesta.values()[i]); } 

		Integer redniBroj = null;
		VrstaMjesta odabranaVrstaMjesta;

		while(true) {
			System.out.println("Odabir vrste mjesta>>");
			try {
				redniBroj = intInput();
				input.nextLine();
				break;
			}catch (InputMismatchException ex) {
				input.nextLine();
				System.out.println("Neispravan unos!");
				//logger.error("Neispravan unos rada senzora!", ex);
			}
		}

		if (redniBroj >= 1 && redniBroj < VrstaMjesta.values().length) {  
			odabranaVrstaMjesta = VrstaMjesta.values()[redniBroj - 1]; 
		} else odabranaVrstaMjesta = VrstaMjesta.OSTALO; 

		return odabranaVrstaMjesta;
	}

	/**
	 * Metoda za odabir rada senzora
	 * @return odabrani rad senzora
	 */
	static RadSenzora odabirRadaSenzora() {

		for (int i = 0; i < RadSenzora.values().length - 1; i++) {  
			System.out.println((i + 1) + "." + RadSenzora.values()[i]); } 

		Integer redniBroj = null;
		RadSenzora odabraniRadSenzora;

		while(true) {
			System.out.println("Odabir rada senzora>>");
			try {
				redniBroj = intInput();
				input.nextLine();
				break;
			}catch (InputMismatchException ex) {
				input.nextLine();
				System.out.println("Neispravan unos!");
				//logger.error("Neispravan unos rada senzora!", ex);
			}
		}

		if (redniBroj >= 1 && redniBroj < RadSenzora.values().length) {  
			odabraniRadSenzora = RadSenzora.values()[redniBroj - 1]; 
		} else odabraniRadSenzora = RadSenzora.OSTALO; 

		return odabraniRadSenzora;
	}

	//private static List<Senzor> popunjavanjeImenaSenzora() {}

}

