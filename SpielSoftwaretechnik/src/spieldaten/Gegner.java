package spieldaten;

public class Gegner extends Charakter{
	private String typ;
	private String todesgrund;
	
	public Gegner(String typ, int xPos, int yPos, int width, int height, int speed, int leben) {
		super(xPos, yPos, width, height, speed, leben);
		this.typ = typ;
		todesgrund = "";
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getTodesgrund() {
		return todesgrund;
	}

	public void setTodesgrund(String todesgrund) {
		this.todesgrund = todesgrund;
	}
	
	

}
