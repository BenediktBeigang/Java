package gui;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import spieldaten.Schuss;

public class SchussGUI extends JLabel implements KollisionsObjekt{
	private static final long serialVersionUID = 1L;
	
	private static String DIR_SEPERATOR = java.io.File.separator;
	
	private Schuss schussDaten;

	public SchussGUI(Schuss schuss, int posX, int posY, int width, int height) {
		schussDaten=schuss;
		this.setLocation(posX, posY);
		this.setSize(width,height);
		File file = new File("./ItemBilder" + DIR_SEPERATOR + "schuss.png");
		if (file.exists()) {
			ImageIcon img = new ImageIcon(file.getAbsolutePath());
			this.setIcon(img);
		} else {
			System.out.println("ERROR - Schuss img not found: " + file.getPath());
		}
		
	}

	public Schuss getDaten() {
		return schussDaten;
	}

}
