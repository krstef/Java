package hr.java.vjezbe.entitet;

public interface RadioSondazna {
	
	//metoda za postavljajne visine
	public void podesiVisinuPostaje (int visinaPostaje);
	
	//metoda za dohvacanje visine
	public int dohvatiVisinuPostaje();
	
	//metoda za provjeru visine
	public static int provjeriVisinu (int visina) {
		if (visina < 1000 ) 
			return visina;
		else return 1000;
	}
	
	//metoda za povecanje visine
	public default int povecajVisinu (int visina) {
		
		int dodanaVisina = visina++;
		
		return provjeriVisinu(dodanaVisina);
	}
	

}
