package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import GlobaleKlassen.Controller;
import spieldaten.Spieler;
import spieldaten.Spielobjekt;

public class SpielerGUI extends JLabel implements KollisionsObjekt {
	private static final long serialVersionUID = 1L;
	
	private static String DIR_SEPERATOR;
	
	private BufferedImage image;
	private BufferedImage newImage;
	private Graphics graph;
	private Spieler spielerDaten;

	public SpielerGUI(Spieler spielerdaten, int x, int y, int groesse, Color c) {
		DIR_SEPERATOR = java.io.File.separator;
		
		this.setLocation(x, y);
		this.setSize(groesse, groesse);
		this.setBackground(c);

		try {
			image = ImageIO.read(new File("./Assets" + DIR_SEPERATOR + "Player" + DIR_SEPERATOR + "Spieler.png"));
		} catch (IOException ex) {
			System.out.println("Bild wurde nicht gefunden");
		}
		this.spielerDaten = spielerdaten;
		graph = null;
	}

	protected void paintComponent(Graphics g) {
		graph = g;
		super.paintComponent(g);
		g.drawImage(newImage, 0, 0, this);
	}

	public void updateBilder() {
		newImage = GUIObjektHilfsfunktionen.getImageMitRichtung(this, image);
		super.paintComponent(graph);
		graph.drawImage(image, 0, 0, this);

		this.revalidate();
		this.repaint();
	}

	public Spielobjekt getDaten() {
		return spielerDaten;
	}

	public void updateAnzeigenPosition() {
		int deltaXLeben = 0;
		int deltaXCooldown = 0;

		if (Controller.getSpieldaten().getSpieler().getAktuelleRichtungGrad()>0) {
			deltaXLeben = 50;
			deltaXCooldown = 70;
		} else {
			deltaXLeben = -20;
			deltaXCooldown = -40;
		}
		Controller.getSpielframe().getLebensAnzeige().setLocation(deltaXLeben + getX(), getY());
		Controller.getSpielframe().getCooldownAnzeige().setLocation(deltaXCooldown + getX(), getY());

	}
}
