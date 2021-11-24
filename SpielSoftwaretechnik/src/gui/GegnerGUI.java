package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import spieldaten.Gegner;

public class GegnerGUI extends JLabel implements KollisionsObjekt{
	private static final long serialVersionUID = 1L;
	
	private static String DIR_SEPERATOR = java.io.File.separator;
	
	private Gegner gegnerDaten;
	private BufferedImage image;
	private BufferedImage newImage;
	private Graphics graph;
	
	public GegnerGUI(Gegner gegner, int posX, int posY, int width, int height) {
		gegnerDaten = gegner;
		this.setLocation(posX, posY);
		this.setSize(width, height);
		
		
		String dateiname = "GegnerDozent.png";
		if(gegnerDaten.getTyp().equals("freund")) {
			dateiname = "GegnerFreund.png";
		}
		
		try {
			image = ImageIO.read(new File("./Assets" + DIR_SEPERATOR + "Player" + DIR_SEPERATOR + dateiname));
			newImage = image;
		} catch (IOException ex) {
			System.out.println("Bild wurde nicht gefunden");
		}
		graph = null;
	}

	public Gegner getDaten() {
		return gegnerDaten;
	}
	
	protected void paintComponent(Graphics g) {
		graph = g;
		super.paintComponent(g);
		g.drawImage(newImage, 0, 0, this);
	}

	public void updateBilder() {
		if(graph!=null) {
			newImage = GUIObjektHilfsfunktionen.getImageMitRichtung(this, image);
			super.paintComponent(graph);
			graph.drawImage(newImage, 0, 0, this);
		}
		this.revalidate();
		this.repaint();
	}
	
}
