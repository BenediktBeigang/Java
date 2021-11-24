package spieldaten;

public class Charakter extends Spielobjekt{
	private int leben;
	private final int MAX_LEBEN;
	
	public Charakter(int xPos, int yPos, int width, int height, int speed, int leben) {
		super(xPos, yPos, width, height, speed);
		this.leben = leben;
		this.MAX_LEBEN = leben;
	}
	
	public void decreaseLeben() {
		leben--;
	}
	
	public void increaseLeben() {
		if(MAX_LEBEN>=leben) {
			leben++;
		}	
	}

	public int getLeben() {
		return leben;
	}

	public void setLeben(int leben) {
		this.leben = leben;
	}

}
