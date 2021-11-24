package spieldaten;

import GlobaleKlassen.Vector2D;

public class Schuss extends Spielobjekt {
	private Vector2D schussRichtung;
	
	public Schuss(int xPos, int yPos, int width, int height, int speed) {
		super(xPos, yPos, width, height, speed);
		schussRichtung = new Vector2D();
		
	}

	public Vector2D getSchussRichtung() {
		return schussRichtung;
	}

	public void setSchussRichtung(Vector2D schussRichtung) {
		this.schussRichtung = schussRichtung;
	}

}
