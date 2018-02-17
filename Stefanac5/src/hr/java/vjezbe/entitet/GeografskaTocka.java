package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class GeografskaTocka extends BazniEntitet{

	private BigDecimal x,y;
		
	public GeografskaTocka(BigDecimal x, BigDecimal y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public GeografskaTocka(BigDecimal x, BigDecimal y, int id) {
		super.setId(id);
		this.x = x;
		this.y = y;
	}
	
	public BigDecimal getX() {
		return x;
	}
	
	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}
}
