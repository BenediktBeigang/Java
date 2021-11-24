package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import spieldaten.Item;

public class ItemGUI extends JLabel implements KollisionsObjekt {
	private static final long serialVersionUID = 1L;
	private static String DIR_SEPERATOR = java.io.File.separator;
	private Item itemDaten;
	private BufferedImage image;

	public ItemGUI(Item item, int posX, int posY, int width, int height) {
		itemDaten = item;
		this.setLocation(posX, posY);
		this.setSize(width, height);
		this.setBackground(new Color(0, 0, 0, 0));

		selectArt();
		this.repaint();
	}

	public void selectArt() {
		String art = itemDaten.getArt();
		String pfad = null;

		if (art.equals("pizza")) {
			pfad = "Assets" + DIR_SEPERATOR + "Items" + DIR_SEPERATOR + "Pizza.png";
		} else if (art.equals("energy")) {
			pfad = "Assets" + DIR_SEPERATOR + "Items" + DIR_SEPERATOR + "Energydrink.png";
		} else {
			pfad = "Assets" + DIR_SEPERATOR + "Items" + DIR_SEPERATOR + "Spezialattacke.png";
		}

		if (pfad != null) {
			try {
				image = ImageIO.read(new File(pfad));
			} catch (IOException ex) {
				System.out.println("Item-Bild wurde nicht gefunden");
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

	public Item getDaten() {
		return itemDaten;
	}
}
