package spieldaten;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import GlobaleKlassen.Controller;
import GlobaleKlassen.State;
import GlobaleKlassen.TimerManager;
import GlobaleKlassen.State.STATE;
import GlobaleKlassen.Vector2D;
import gui.ItemGUI;
import gui.WandGUI;

/**
 * Diese Klasse dient der organisation der Spielogik. Alle Daten die nichts mit
 * der Visualisierung zu tun haben werden hier gespeichert. Ausserdem werden
 * hier spiellogische Entscheidungen getroffen.
 * 
 * @author Benedikt
 */
public class Spiel {
	private int getoeteteGegner;
	private Item aktuellesItem;

	private Spieler spieler;
	private ArrayList<Gegner> gegnerDatenListe;
	private ArrayList<Schuss> schussDatenListe;
	private ArrayList<Wand> wandDatenListe;

	private int sekunden;
	private int cooldown;

	private int score;
	private int speedBoostOverTime = 0;

	private final int MAX_COOLDOWN;
	private final int SUBTRAHEND_NORMAL_COOLDOWN;
	private final int SUBTRAHEND_OVERHEAT_COOLDOWN;

	// private boolean spielAn;
	private boolean aktivesItem;
	private Random random;
	private boolean salveAn;

	/**
	 * Spiel() ist der Konstruktor der Klasse und initialisiert alle Variablenund
	 * Listen und bringt das Spiel in seinen Ausgangszustand.
	 * 
	 * @author Benedikt
	 */
	public Spiel() {
		score = 0;
		getoeteteGegner = 0;
		aktuellesItem = null;
		gegnerDatenListe = new ArrayList<Gegner>();
		schussDatenListe = new ArrayList<Schuss>();
		spieler = new Spieler(0, 0, 40, 40, 7, 10);
		sekunden = 0;
		cooldown = 0;
		MAX_COOLDOWN = 600;
		SUBTRAHEND_NORMAL_COOLDOWN = 6;
		SUBTRAHEND_OVERHEAT_COOLDOWN = 4;
		random = new Random();
		// spielAn = true;
		salveAn = false;
		aktivesItem = false;

		runSpiellogik();
		runFastSpiellogik();
	}

	/**
	 * runSpiellogik() startet einen Timer der jede Sekunde das Spiel vorantreibt,
	 * also Gegner spawnt, und final l�scht und die Sekunden hochz�hlt.
	 * 
	 * @author Benedikt
	 */
	public void runSpiellogik() {
		Timer time = new Timer(1000, (ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
				// Fps zaehler
				Controller.getSpielframe().setFramesPerSecond(Controller.getSpielframe().getFrame());
				Controller.getSpielframe().setFrame(0);
				if (Controller.getSpielframe().getFramesPerSecond() < 55) {
					System.out.println("WARNING - performance low");
				}

				// Pruefe wie viele threads offen sind
				// Thread.currentThread();
				if (Thread.activeCount() > 250) {
					System.out.println("WARNING - Many open threads: " + Thread.activeCount());
				}

				if (Controller.getStatus().getState() == STATE.GAME) {
					if (Controller.getSpielframe() != null) {
						spawnverhaltenGegner();
					}

					Controller.getSpielframe().deleteGegner();
					Controller.getSpielframe().deleteSchuesse();
					sekunden++;

					if (!aktivesItem && sekunden % 10 == 0) {
						createItem();
					}
				}
			}

		});
		time.start();
		TimerManager.addTimer(time);
	}

	public void spawnverhaltenGegner() {
		if (sekunden >= 29) {
			if (sekunden % 15 == 0) {
				speedBoostOverTime++;
				Controller.getSoundboard().playNewWave();
				int extraGegner = (sekunden / 15) * Controller.getGeladenesLevel().getThougness();
				for (int i = 0; i < extraGegner; i++) {
					spawnGegnerBeiKanten();
				}
			}
		}

		int gegnerAnzahl = 1;
		for (int i = 0; i < gegnerAnzahl; i++) {
			spawnGegnerBeiKanten();
		}
	}

	public void runFastSpiellogik() {
		Timer time = new Timer(10, (ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Controller.getStatus().getState() == STATE.GAME && spielerTot()) {
					stoppeSpiel();
				}
			}
		});
		time.start();
		TimerManager.addTimer(time);
	}

	public boolean spielerTot() {
		if (spieler.getLeben() <= 0) {
			return true;
		}
		return false;
	}

	public void stoppeSpiel() {
		Controller.getSoundboard().playGameOver();
		State.setState(STATE.TOT);
		Controller.totMenu();
	}

	public void itemAktion(Item item) {
		String art = item.getArt();
		if (art.equals("pizza")) {
			spieler.increaseLeben();
			Controller.getSoundboard().playEats();
			Controller.getSpielframe().getLebensAnzeige().changeValueBy(1);
			Controller.getSpielframe().addAnimation(Controller.getSpielframe().getSpielerGUI(), "health", 0, false,
					false);
			System.out.println("INFO - Leben: " + Controller.getSpieldaten().getSpieler().getLeben() + "+++");
		} else if (art.equals("energy")) {
			Controller.getSoundboard().playDrinkCan();
			Controller.getSpielframe().getSteuerung().increaseSpeed();
			Controller.getSpielframe().addAnimation(Controller.getSpielframe().getSpielerGUI(), "flash", 0, false,
					false);
		} else if (art.equals("spezialSchuss")) {
			Controller.getSoundboard().playPop();
			salveAn = true;
			Controller.getSpielframe().getSteuerung().startSalveTimer();
		}
	}

	public void createItem() {
		aktivesItem = true;

		Item newItem = new Item(0, 0, 0, 0, 0, "");
		int itemArt = -1;
		itemArt = random.nextInt(3);

		switch (itemArt) {
		case 0:
			// Pizza | Leben
			newItem.setArt("pizza");
			break;
		case 1:
			// Energy Dose | Speed
			newItem.setArt("energy");
			break;
		case 2:
			// | Spezialschuss
			newItem.setArt("spezialSchuss");
			break;
		}

		ItemGUI newItemGUI = new ItemGUI(newItem, 0, 0, 40, 40);
		boolean positionGefunden = false;
		while (!positionGefunden) {
			positionGefunden = true;
			newItemGUI.setLocation(random.nextInt(920), random.nextInt(920));
			innerloop: for (WandGUI wand : Controller.getSpielframe().getWandGUIListe()) {
				if (Controller.getSpielframe().getKollision().isClose(newItemGUI, wand)) {
					positionGefunden = false;
					break innerloop;
				}
			}
		}
		aktuellesItem = newItem;
		Controller.getSpielframe().addItem(newItemGUI);
		Controller.getSpielframe().addAnimation(newItemGUI.getX() + 15, newItemGUI.getY() + 15, "pickup");
		Controller.getSoundboard().playPop();
		if (newItem.getArt() == "pizza") {
			Controller.getSpielframe().addAnimation(newItemGUI, "vapor", 0, true, false);
		}
		if (newItem.getArt() == "energy") {
			Controller.getSpielframe().addAnimation(newItemGUI, "energy", 0, true, false);
		}
		if (newItem.getArt() == "spezialSchuss") {
			Controller.getSpielframe().addAnimation(newItemGUI, "vibration", 0, true, false);
		}
		// Controller.getSpielframe().addAnimation(newItemGUI, "vapor", 0, true, false);
	}

	/**
	 * spawnGegnerBeiKanten() spawnt zuf�llig Gegner an den Kanten und wird von
	 * runSpiellogik() aufgerufen.
	 * 
	 * @author Benedikt
	 */
	public void spawnGegnerBeiKanten() {
		int kante = random.nextInt(4) + 1;
		int offset = random.nextInt(960) + 1;
		int speed = Controller.getGeladenesLevel().getSpeed() + speedBoostOverTime;
		int groesse = 40;

		String typ = "";
		int art = random.nextInt(2);
		switch (art) {
		case 0:
			typ = "dozent";
			break;
		case 1:
			typ = "freund";
			break;
		}

		switch (kante) {
		case 1:
			createGegner(typ, offset, -40, groesse, speed, 3);
			break;
		case 2:
			createGegner(typ, 960, offset, groesse, speed, 3);
			break;
		case 3:
			createGegner(typ, offset, 960, groesse, speed, 3);
			break;
		case 4:
			createGegner(typ, -40, offset, groesse, speed, 3);
			break;
		}
	}

	/**
	 * createGegner() erstellt einen neuen Gegner und speichert diesen in
	 * gegnerDatenListe. Au�erdem wird in Frame zus�tzlich ein zugeh�riger
	 * GegnerGUI erstellt.
	 * 
	 * @param art     Die Art des Gegners
	 * @param posX    X-Wert der Startposition
	 * @param posY    Y-Wert der Startposition
	 * @param groesse Gr��e des GegnerGUIs
	 * @param speed   Geschwindigkeit des Gegners
	 * @param leben   leben des Gegners
	 * @return Gibt false zur�ck wenn die �bergebene Art nicht existiert
	 * @author Benedikt
	 */
	public boolean createGegner(String typ, int posX, int posY, int groesse, int speed, int leben) {
		Gegner neuerGegner = new Gegner(typ, posX, posY, groesse, groesse, speed, leben);
		gegnerDatenListe.add(neuerGegner);
		Controller.getSpielframe().addGegner(posX, posY, groesse, groesse, neuerGegner);
		return true;
	}

	/**
	 * Erstellt sowohl das Schussobjekt als auch das zugeh�rige JLabel Objekt. Das
	 * Schuss-Objekt wird in die entsprechende Datenliste aufgenommen.
	 * 
	 * @param xPos     Startkoordinate auf der x-Achse
	 * @param yPos     Startkoordinate auf der y-Achse
	 * @param width    Breite des Swing-Labels
	 * @param height   H�he des Swing-Labels
	 * @param speed    Geschwindigkeit des Schusses
	 * @param richtung Richtung des Schusses
	 * @author Florian
	 */
	public void createSchuss(int xPos, int yPos, int width, int height, int speed) {
		Schuss neuerSchuss = new Schuss(xPos, yPos, width, height, speed);
		Vector2D mausVektor = Controller.getSpielframe().getSteuerung().getMouseAsVector();
		Vector2D verbindungsVektor = Vector2D.getVerbindungsVektor2D(mausVektor, new Vector2D(xPos, yPos));
		neuerSchuss.setSchussRichtung(Vector2D.skaliereVektor(verbindungsVektor, speed));

		schussDatenListe.add(neuerSchuss);
		Controller.getSpielframe().addSchuss(xPos, yPos, width, height, neuerSchuss);

		cooldown = cooldown + 100;
	}

	/**
	 * Erstellt sowohl die Schussobjekte als auch das zugeh�rige JLabel Objekte
	 * f�r einen Salven-Spezialschuss. Die Schuss-Objekte werden in die
	 * entsprechende Datenliste aufgenommen.
	 * 
	 * @param xPos     Startkoordinate auf der x-Achse
	 * @param yPos     Startkoordinate auf der y-Achse
	 * @param width    Breite des Swing-Labels
	 * @param height   H�he des Swing-Labels
	 * @param speed    Geschwindigkeit der Sch�sse
	 * @param richtung Richtung des Hauptschusses
	 * @author Florian
	 */
	public void createSalve(int xPos, int yPos, int width, int height, int speed) {

		Vector2D verbindungsVektor = Vector2D.getVerbindungsVektor2D(
				Controller.getSpielframe().getSteuerung().getMouseAsVector(), new Vector2D(xPos, yPos));
		Vector2D hauptVektor = Vector2D.skaliereVektor(verbindungsVektor, speed);
		Vector2D nebenVektorLinks = hauptVektor.rotate(-45.0);
		Vector2D nebenVektorRechts = hauptVektor.rotate(45.0);

		Vector2D[] vektorArray = { hauptVektor, nebenVektorLinks, nebenVektorRechts };

		for (Vector2D v : vektorArray) {
			Schuss tempSchuss = new Schuss(xPos, yPos, width, height, speed);
			tempSchuss.setSchussRichtung(v);
			Controller.getSpielframe().addSchuss(xPos, yPos, width, height, tempSchuss);
		}

		cooldown = cooldown + 100;
	}

	/**
	 * �berpr�ft, ob die Kanone �berhitzt. Spart Rechenzeit (weil in
	 * Mouseevent aufgerufen), macht das Programm aber un�bersichtlicher.
	 * Alternativ: in create Schuss pr�fen
	 * 
	 * @return boolean-Wert, ob der Cooldown einen Schuss zul�sst
	 * @author Florian
	 */
	public boolean darfSchießen() {
		if (cooldown < MAX_COOLDOWN) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Zieht Konstante Werte vom cooldown ab, die abh�ngig davon sind, ob die
	 * Kanone �berhitzt ist oder nicht.
	 * 
	 * @author Florian
	 */
	public void decreaseCooldown() {
		if (0 < cooldown && darfSchießen()) {
			cooldown = cooldown - SUBTRAHEND_NORMAL_COOLDOWN;
		} else if (0 < cooldown && !darfSchießen()) {
			cooldown = cooldown - SUBTRAHEND_OVERHEAT_COOLDOWN;
		}
	}

	public void increaseKillCounter() {
		getoeteteGegner++;
	}

	public int getGetoeteteGegner() {
		return getoeteteGegner;
	}

	public int getScore() {
		return score;
	}

	public void increaseScore(int addition) {
		score = score + addition;
	}

	public void setGetoeteteGegner(int getoeteteGegner) {
		this.getoeteteGegner = getoeteteGegner;
	}

	public Item getAktuellesItem() {
		return aktuellesItem;
	}

	public void setAktuellesItem(Item aktuellesItem) {
		this.aktuellesItem = aktuellesItem;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}

	public ArrayList<Gegner> getGegnerDatenListe() {
		return gegnerDatenListe;
	}

	public void setGegnerDatenListe(ArrayList<Gegner> gegnerDatenListe) {
		this.gegnerDatenListe = gegnerDatenListe;
	}

	public int getSekunden() {
		return sekunden;
	}

	public void setSekunden(int sekunden) {
		this.sekunden = sekunden;
	}

	public ArrayList<Schuss> getSchussDatenListe() {
		return schussDatenListe;
	}

	public void setSchussDatenListe(ArrayList<Schuss> schussDatenListe) {
		this.schussDatenListe = schussDatenListe;
	}

	public boolean isAktivesItem() {
		return aktivesItem;
	}

	public void setAktivesItem(boolean aktivesItem) {
		this.aktivesItem = aktivesItem;
	}

	public ArrayList<Wand> getWandDatenListe() {
		return wandDatenListe;
	}

	public void setWandDatenListe(ArrayList<Wand> wandDatenListe) {
		this.wandDatenListe = wandDatenListe;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public int getMAX_COOLDOWN() {
		return MAX_COOLDOWN;
	}

	public boolean isSalveAn() {
		return salveAn;
	}

	public void setSalveAn(boolean salveAn) {
		this.salveAn = salveAn;
	}

}
