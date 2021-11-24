package spieldaten;

public class Spieler extends Charakter{
	private boolean speedBoost;

	public Spieler(int xPos, int yPos, int width, int height, int speed, int leben) {
		super(xPos, yPos, width, height, speed, leben);
		speedBoost = false;
	}

	public boolean isSpeedBoost() {
		return speedBoost;
	}

	public void setSpeedBoost(boolean speedBoost) {
		this.speedBoost = speedBoost;
	}
	
}
