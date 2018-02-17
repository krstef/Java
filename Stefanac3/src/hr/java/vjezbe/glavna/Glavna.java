package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;
import hr.java.vjezbe.entitet.KolicinaPadalina;

/**
 * Klasa Glavna u kojoj se izvršava aplikacija 
 */
public class Glavna {

	static final int SVE_MJERNE_POSTAJE = 3;
	static final int RADIOSONDAZNE_POSTAJE = 1;
	static final int KLASICNE_POSTAJE = 2;

	static final int BROJ_SENZORA = 3;
	/*
	 * 1 - Deklariranje reference input koja je tipa Scanner
	 * 2 - new Scanner stvara novi objekt tipa Scanner
	 * 3 - Novonastali objekt pridružeje se referenci input
	 */
	public static Scanner input = new Scanner(System.in);

							
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	/**
	 * Main metoda
	 * @param args
	 */
	public static void main(String[] args) {


		Drzava drzava;
		Zupanija zupanija;
		Mjesto mjesto;
		GeografskaTocka geoTocka;
		MjernaPostaja [] mjPostaja = new MjernaPostaja [SVE_MJERNE_POSTAJE];
		Senzor [] senzori = new Senzor [BROJ_SENZORA];

		logger.info("Aplikacija je pokrenuta!");

		for (int i = 0; i < SVE_MJERNE_POSTAJE; i++) {

			System.out.format("Unesite " + (i+1) + " mjernu postaju \n"); 

			drzava = unosDrzave (input, i+1) ;
			zupanija = unosZupanije (input, drzava, i+1);
			mjesto = unosMjesta (input, zupanija, i+1);
			geoTocka = unostTocke (input, i+1);

			senzori = unosSenzora(input);

			if (i < KLASICNE_POSTAJE)			
				mjPostaja[i] = unosMjPostaje (input, mjesto, geoTocka, senzori, i+1);
			else mjPostaja[i] = unosRadioSondazneMjernePostaje(input, mjesto, geoTocka, senzori);

		} 

		//ispis mjernih postaja

		logger.info("Ispis mjernih postaja.");

		for (int j = 0; j < SVE_MJERNE_POSTAJE; j++) {

			System.out.println("<------------------------>");

			if(mjPostaja[j] instanceof RadioSondaznaMjernaPostaja) {

				ispisRadioSondazneMjernePostaje((RadioSondaznaMjernaPostaja) mjPostaja[j]);
				System.out.println ("Vrijednost senzora postaje: ");

				for(int k = 0; k < mjPostaja[j].dohvatiSenzore().length; k++)
					System.out.println(mjPostaja[j].dohvatiSenzore()[k].dohvatiPodatkeSenzora());
			}

			else if (mjPostaja[j] instanceof MjernaPostaja) {
				ispisMjPostaje( mjPostaja[j] );
				System.out.println("Vrijednost senzora postaje: ");

				for (int k = 0; k < mjPostaja[j].dohvatiSenzore().length; k++)
					System.out.println(mjPostaja[j].dohvatiSenzore()[k].dohvatiPodatkeSenzora());
			}

		}

		logger.info("Prikaz vrijednosti temperaturnih senzora.");
		//beskonacna petlja za generiranje vrijednosti senzora
		for(;;) {

			System.out.println("______________________________________");

			for (int i = 0; i < mjPostaja.length; i++) {
				for (int j = 0; j < mjPostaja[i].dohvatiSenzore().length;j++) {

					try {
						if(mjPostaja[i].dohvatiSenzore()[j] instanceof SenzorTemperature) {
							((SenzorTemperature) mjPostaja[i].dohvatiSenzore()[j]).generirajVrijednost();
							System.out.println("Postaja: "+ mjPostaja[i].getNaziv() + ", Temperatura - OK! Temp iznosi: "+ mjPostaja[i].dohvatiSenzore()[j].getVrijednost());
							logger.info("Temp je u redu! Vrijednost temp: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost());
						}
					}catch (VisokaTemperaturaException e) {
						System.out.println("Postaja: "+ mjPostaja[i].getNaziv()
								+", VISOKA TEMPERATURA! Temperatura postaje: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost());

						logger.error("Temp previsoka! Vrijednost temp: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost(), e);

					}catch (NiskaTemperaturaException e) {
						System.out.println("Postaja: "+ mjPostaja[i].getNaziv()
								+", NISKA TEMPERATURA! Temperatura postaje je: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost());

						logger.error("Temp preniska! Vrijednost temp: " + mjPostaja[i].dohvatiSenzore()[j].getVrijednost(), e);
					}
			}
		}

		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

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
		Senzor[] senzori) {
	System.out.println("\nUpisite visinu radio sondazne mjerne postaje: ");
	int visinaPostaje = intInput();
	input.nextLine();
	logger.info("Unos visine mjerne postaje:" + visinaPostaje);

	System.out.println("Upisite naziv radio sondazne mjerne postaje: ");
	String naziv = input.nextLine();
	logger.info("Unos naziva radiosondazne mjerne postaje:" + naziv);

	return new RadioSondaznaMjernaPostaja(naziv, mjesto, geoTocka, senzori, visinaPostaje);
}

/**
 * Metoda za ispis RadioSondazneMjernePostaje
 * @param radioSondaznaMjernaPostaja
 */
private static void ispisRadioSondazneMjernePostaje(RadioSondaznaMjernaPostaja radioSondaznaMjernaPostaja) {

	System.out.println("Naziv radio sondazne mjerne postaje: " + radioSondaznaMjernaPostaja.getNaziv());

	System.out.println("Postaja je radio sondazna");

	System.out.println("Visina radio sondazne mjerne postaje: " + radioSondaznaMjernaPostaja.dohvatiVisinuPostaje());

	System.out.format("Postaja se nalazi na mjestu %s, zupaniji %s, drzavi %s \n",
			radioSondaznaMjernaPostaja.getMjesto().getNaziv(), 
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
private static Senzor[] unosSenzora(Scanner input) {
	Senzor[] senzori = new Senzor[BROJ_SENZORA];

	System.out.println("Unesite naziv senzora temperature:");
	String nazivTemp = input.nextLine();

	System.out.println("Unesite vrijednost senzora temperature: ");
	BigDecimal vrijednostTemp = bigDecimalInput();
	logger.info("Podaci za senzor temperature:" + nazivTemp + ", " + vrijednostTemp);

	System.out.println("Unesite vrijednost senzora vlage:");
	BigDecimal vrijednostVlaga = bigDecimalInput();
	logger.info("Podaci za senzor vlage:" + vrijednostVlaga);

	System.out.println("Unesite tip senzora padalina:");
	String tipSenzora = input.nextLine();

	System.out.println("Unesite vrijednost senzora padalina:");
	BigDecimal vrijednostPad = bigDecimalInput();
	logger.info("Podaci za senzor padalina:" + tipSenzora + ", " + vrijednostPad);

	byte preciznostTemp = 2;
	SenzorTemperature senzorTemperature= new SenzorTemperature("°C", preciznostTemp, nazivTemp );
	senzorTemperature.setVrijednost(vrijednostTemp);

	byte preciznostVlaga = 2;
	SenzorVlage senzorVlage = new SenzorVlage("%", preciznostVlaga);
	senzorVlage.setVrijednost(vrijednostVlaga);

	byte preciznostPad = 2;
	KolicinaPadalina senzorPadalina = new KolicinaPadalina("L/m2", preciznostPad, tipSenzora);
	senzorPadalina.setVrijednost(vrijednostPad);

	senzori[0] = senzorTemperature;
	senzori[1] = senzorVlage;
	senzori[2] = senzorPadalina;

	return senzori;
}

/**
 * Metoda za ispis MjernePostaje
 * @param mjernaPostaja
 */
private static void ispisMjPostaje(MjernaPostaja mjernaPostaja) {

	System.out.println("Naziv mjerne postaje: " + mjernaPostaja.getNaziv());

	System.out.format("Postaja se nalazi  mjestu %s, zupanija %s, drzava %s \n",
			mjernaPostaja.getMjesto().getNaziv(), 
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
private static MjernaPostaja unosMjPostaje(Scanner input, Mjesto mjesto, GeografskaTocka geoTocka, Senzor[] senzori, int i) {

	System.out.println("\nUpisite naziv " + i + " mjerne postaje: ");
	String naziv = input.nextLine(); 
	
	//kak si ti napiso
	
	//kak treba
	logger.info("Uneseni naziv mjerne postaje broj "+ i + ": "+ naziv);
	
	return new MjernaPostaja(naziv, mjesto, geoTocka, senzori);
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
	logger.info("Unesena tocka X: "+ x);
	System.out.println("\nUpisite Geo koordinatu Y:");
	BigDecimal y = bigDecimalInput();
	logger.info("Unesena tocka Y: "+ y);

	return new GeografskaTocka(x, y);
}

/**
 * Metoda za unos Mjesta
 * @param input
 * @param zupanija
 * @param i
 * @return novi objekt Mjesto
 */
private static Mjesto unosMjesta(Scanner input, Zupanija zupanija, int i) {

	System.out.println("\nUnesite naziv " + i + " mjesta:");
	String naziv = input.nextLine();
	logger.info("Uneseni naziv mjesta broj "+ i + ": "+ naziv);

	return new Mjesto(naziv, zupanija);
}

/**
 * Metoda za unos Zupanije
 * @param input
 * @param drzava
 * @param i
 * @return novi objekt Zupanija
 */
private static Zupanija unosZupanije(Scanner input, Drzava drzava, int i) {
	System.out.println("Upisite naziv " + i + " zupanije:");
	String naziv = input.nextLine();
	logger.info("Uneseni naziv zupanije: "+ naziv);

	return new Zupanija(naziv, drzava);
} 

/**
 * Metoda za unos drzave
 * @param input
 * @param i
 * @return novi objekt Drzava
 */
private static Drzava unosDrzave(Scanner input, int i) {

	System.out.println("\nUnesite naziv " + i +" drzave:");
	String naziv = input.nextLine();
	logger.info("Uneseni naziv drzave: "+ naziv);
	System.out.println("\nUnesite povrsinu " + i +" drzave:");
	BigDecimal povrsina = bigDecimalInput();
	logger.info("Unesena povrsina drzave: "+ naziv);

	return new Drzava(naziv, povrsina); 

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
			logger.error("Krivi tip podatka. Upisan je podatak: "+ num, e);
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
			logger.error("Krivi tip podatka. Upisan je podatak: "+ num, e);
			input.nextLine();
			flag = false;
		}
		catch (InputMismatchException e) {

			System.out.println("Krivi unos broja! Unesite cijeli broj!");
			logger.error("Krivi unos broja. Upisan je broj: "+ num, e);
			input.nextLine();
			flag = false;
		}
	}
	while(!flag);

	return num;
}

}

