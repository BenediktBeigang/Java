package spieldaten;

public class Item extends Spielobjekt{
	private String art;

	public Item(int xPos, int yPos, int width, int height, int speed, String art) {
		super(xPos, yPos, width, height, speed);
		this.art = art;
		
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

}
