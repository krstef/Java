package hr.java.vjezbe.iznimke;

public class VisokaTemperaturaException extends Exception {
	
	public VisokaTemperaturaException() {
		super("Pogreska temperature!");
	}
	
	public VisokaTemperaturaException(String message) {
		super(message);
	}
	
	public VisokaTemperaturaException(Throwable cause) {
		super(cause);
	}
	
	public VisokaTemperaturaException(String message, Throwable cause) {
		super(message, cause);
	}

}
