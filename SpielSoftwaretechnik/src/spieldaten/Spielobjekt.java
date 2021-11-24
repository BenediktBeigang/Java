package spieldaten;

public class Spielobjekt {
	private int speed;
	private double xPos, yPos;
	private double aktuelleRichtungGrad;
	private boolean aktiviert;
	public Spielobjekt(int xPos, int yPos, int width, int height, int speed) {
		this.speed = speed;
		
		setAktiviert(true);
	}
	
	public void setAktuelleRichtungGrad(double aktuelleRichtungGrad) {
		this.aktuelleRichtungGrad = aktuelleRichtungGrad;
	}
	
	public void setXPos(double xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(double yPos) {
		this.yPos = yPos;
	}

	public int diagonalSpeed() {
		return (int) Math.round(speed / Math.sqrt(2));
	}
	
	public void increaseSpeedBy(int deltaSpeed) {
		speed += deltaSpeed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	
	public double getAktuelleRichtungGrad() {
		return aktuelleRichtungGrad;
	}
	
	public double getXPos() {
		return this.xPos;
	}
	
	public int getXPosInt() {
		return (int) Math.round(this.xPos);
	}
	
	public double getYPos() {
		return this.yPos;
	}
	
	public int getYPosInt() {
		return (int) Math.round(this.yPos);
	}


	public boolean isAktiviert() {
		return aktiviert;
	}

	public void setAktiviert(boolean aktiviert) {
		this.aktiviert = aktiviert;
	}
}
