package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.entitet.kolicinaPadalina;

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

	public static void main(String[] args) {


		Drzava drzava;
		Zupanija zupanija;
		Mjesto mjesto;
		GeografskaTocka geoTocka;
		MjernaPostaja [] mjPostaja = new MjernaPostaja [SVE_MJERNE_POSTAJE];
		Senzor [] senzori = new Senzor [BROJ_SENZORA];

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

	}

	private static MjernaPostaja unosRadioSondazneMjernePostaje(Scanner input, Mjesto mjesto, GeografskaTocka geoTocka,
			Senzor[] senzori) {
		System.out.println("\nUpisite visinu radio sondazne mjerne postaje: ");
		int visinaPostaje = input.nextInt();
		input.nextLine();
		
		System.out.println("Upisite naziv radio sondazne mjerne postaje: ");
		String naziv = input.nextLine();
		
		return new RadioSondaznaMjernaPostaja(naziv, mjesto, geoTocka, senzori, visinaPostaje);
	}

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

	private static Senzor[] unosSenzora(Scanner input) {
		Senzor[] senzori = new Senzor[BROJ_SENZORA];
		
		System.out.println("Unesite naziv senzora temperature:");
		String nazivTemp = input.nextLine();
		
		System.out.println("Unesite vrijednost senzora temperature: ");
		BigDecimal vrijednostTemp = new BigDecimal (input.nextLine());
		
		System.out.println("Unesite vrijednost senzora vlage:");
		BigDecimal vrijednostVlaga = new BigDecimal (input.nextLine());
		
		System.out.println("Unesite tip senzora padalina:");
		String tipSenzora = input.nextLine();
		
		System.out.println("Unesite vrijednost senzora padalina:");
		BigDecimal vrijednostPad = new BigDecimal (input.nextLine());
		
		byte preciznostTemp = 2;
		SenzorTemperature senzorTemperature= new SenzorTemperature("°C", preciznostTemp, nazivTemp );
		senzorTemperature.setVrijednost(vrijednostTemp);
		
		byte preciznostVlaga = 2;
		SenzorVlage senzorVlage = new SenzorVlage("%", preciznostVlaga);
		senzorVlage.setVrijednost(vrijednostVlaga);
		
		byte preciznostPad = 2;
		kolicinaPadalina senzorPadalina = new kolicinaPadalina("L/m2", preciznostPad, tipSenzora);
		senzorPadalina.setVrijednost(vrijednostPad);
				
		senzori[0] = senzorTemperature;
		senzori[1] = senzorVlage;
		senzori[2] = senzorPadalina;
		
		return senzori;
	}

	private static void ispisMjPostaje(MjernaPostaja mjernaPostaja) {

		System.out.println("Naziv mjerne postaje: " + mjernaPostaja.getNaziv());

		System.out.format("Postaja se nalazi  mjestu %s, zupanija %s, drzava %s \n",
				mjernaPostaja.getMjesto().getNaziv(), 
				mjernaPostaja.getMjesto().getZupanija().getNaziv(),
				mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());

		System.out.println("Tocne koordinate postaje su x: " + mjernaPostaja.getGeografskaTocka().getX() +
				" y: " + mjernaPostaja.getGeografskaTocka().getY());
	}

	private static MjernaPostaja unosMjPostaje(Scanner input, Mjesto mjesto, GeografskaTocka geoTocka, Senzor[] senzori, int i) {

		System.out.println("\nUpisite naziv " + i + " mjerne postaje: ");
		String naziv = input.nextLine(); 

		return new MjernaPostaja(naziv, mjesto, geoTocka, senzori);
	}

	private static GeografskaTocka unostTocke(Scanner input, int i) {

		System.out.println("\nUpisite koordinatu X:");
		BigDecimal x = new BigDecimal( input.nextLine() );
		System.out.println("\nUpisite Geo koordinatu Y:");
		BigDecimal y = new BigDecimal( input.nextLine() );

		return new GeografskaTocka(x, y);
	}

	private static Mjesto unosMjesta(Scanner input, Zupanija zupanija, int i) {

		System.out.println("\nUnesite naziv " + i + " mjesta:");
		String naziv = input.nextLine();

		return new Mjesto(naziv, zupanija);
	}

	private static Zupanija unosZupanije(Scanner input, Drzava drzava, int i) {
		System.out.println("Upisite naziv " + i + " zupanije:");
		String naziv = input.nextLine();

		return new Zupanija(naziv, drzava);
	} 

	private static Drzava unosDrzave(Scanner input, int i) {

		System.out.println("\nUnesite naziv " + i +" drzave:");
		String naziv = input.nextLine();
		System.out.println("\nUnesite povrsinu " + i +" drzave:");
		BigDecimal povrsina = new BigDecimal (input.nextLine());

		return new Drzava(naziv, povrsina); 

	} 

}

