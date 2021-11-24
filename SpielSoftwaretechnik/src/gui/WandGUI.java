package gui;

import javax.swing.JLabel;

import spieldaten.Spielobjekt;
import spieldaten.Wand;

public class WandGUI extends JLabel implements KollisionsObjekt{
	private static final long serialVersionUID = 1L;
	private int deltaXSpieler;
	private int deltaYSpieler;
	private Wand wandDaten;
	
	public WandGUI(Wand wanddaten) {
		deltaXSpieler = 0;
		deltaYSpieler = 0;
		this.wandDaten = wanddaten;
	}

	public int getDeltaXSpieler() {
		return deltaXSpieler;
	}

	public void setDeltaXSpieler(int deltaXSpieler) {
		this.deltaXSpieler = deltaXSpieler;
	}

	public int getDeltaYSpieler() {
		return deltaYSpieler;
	}

	public void setDeltaYSpieler(int deltaYSpieler) {
		this.deltaYSpieler = deltaYSpieler;
	}

	@Override
	public Spielobjekt getDaten() {
		return wandDaten;
	}
	
	
}
