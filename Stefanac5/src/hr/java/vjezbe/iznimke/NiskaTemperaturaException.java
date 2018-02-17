package hr.java.vjezbe.iznimke;

public class NiskaTemperaturaException extends RuntimeException {
	
	public NiskaTemperaturaException() {
		super("Pogreska temperature!");
	}
	
	public NiskaTemperaturaException(String message) {
		super(message);
	}
	
	public NiskaTemperaturaException(Throwable cause) {
		super(cause);
	}
	
	public NiskaTemperaturaException(String message, Throwable cause) {
		super(message, cause);
	}

}
