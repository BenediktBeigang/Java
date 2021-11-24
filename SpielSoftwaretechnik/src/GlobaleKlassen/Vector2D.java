package GlobaleKlassen;

import gui.KollisionsObjekt;

public class Vector2D {
	private double x;
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;

	}

	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Gibt eine rotierte Version des Vektors zurück. 
	 * Der Ursprüngliche Vektor bleibt erhalten.
	 * @param n Gradzahl
	 * @return den temporären Vektor nach der Rotation
	 * @author Florian
	 */
	public Vector2D rotate(double n) {
	    double rx = (this.x * Math.cos(n)) - (this.y * Math.sin(n));
	    double ry = (this.x * Math.sin(n)) + (this.y * Math.cos(n));
	    return new Vector2D(rx,ry);
	}
	
	public static Vector2D addiereVektoren(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.getX()+v2.getX(), v1.getY() + v2.getY());
	}

	public static Vector2D getVerbindungsVektor2D(Vector2D start, Vector2D ziel) {
		double x = start.getX() - ziel.getX();
		double y = start.getY() - ziel.getY();
		return new Vector2D(x, y);
	}
	
	public static Vector2D getVerbindungsVektor2DWithObjects(KollisionsObjekt obj1, KollisionsObjekt obj2) {
		Vector2D spielerVektor = new Vector2D(obj1.getX() + (obj1.getWidth() / 2),
				obj1.getY() + (obj1.getHeight() / 2));
		Vector2D gegnerVektor = new Vector2D(obj2.getX() + (obj2.getWidth() / 2),
				obj2.getY() + (obj2.getHeight() / 2));

		return Vector2D.getVerbindungsVektor2D(spielerVektor, gegnerVektor);
	}
	
	public static Vector2D skaliereVektor(Vector2D vektor, int neueLaenge) {
		double deltaX = vektor.getX();
		double deltaY = vektor.getY();

		double laengeDesVektors = vektor.getLaenge();
		double skalierungsWert = neueLaenge / laengeDesVektors;

		int speedX = (int) Math.round(deltaX * skalierungsWert);
		int speedY = (int) Math.round(deltaY * skalierungsWert);
		
		return new Vector2D(speedX, speedY);
	}

	public Vector2D getIntVektor() {
		return new Vector2D(Math.round(x), Math.round(y));
	}

	public double getLaenge() {
		return Math.sqrt((x * x) + (y * y));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
