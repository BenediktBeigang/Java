package gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import GlobaleKlassen.Controller;
import GlobaleKlassen.State;
import GlobaleKlassen.Vector2D;
import spieldaten.Spieler;

/**
 * Diese Klasse kï¿½mmert sich um die Steuerung des Spielers und die Bewegung der
 * Spielobjekte.
 * 
 * @author Benedikt
 */
public class Steuerung {
	private ArrayList<Integer> allPressedKeys;
	private boolean[] pressedMovementKeys;
	private boolean[] blockedMovementKeys;
	private boolean[] diagonaleKeysPressed;

	private boolean leftMouseButtonPressed;
	private int mouseX;
	private int mouseY;

	/**
	 * Steuerung() ist der Konstruktor der Klasse und initialisiert die Variablen
	 * und Listen der Klasse.
	 * 
	 * @author Benedikt
	 */
	public Steuerung() {
		allPressedKeys = new ArrayList<Integer>();
		pressedMovementKeys = new boolean[4];
		blockedMovementKeys = new boolean[4];
		for (int i = 0; i < 4; i++) {
			pressedMovementKeys[i] = false;
			blockedMovementKeys[i] = false;
		}
		diagonaleKeysPressed = new boolean[4];

		mouseX = 0;
		mouseY = 0;
	}

	/**
	 * whatDirectionPressed() koordiniert die Bewegung des Spielers anhand der
	 * gedrï¿½ckten Pfeil-Tasten.
	 * 
	 * @author Benedikt
	 */
	public void whatDirectionPressed() {
		if (!getAllPressedKeys().isEmpty()) {
			if (allPressedKeys.contains(37) || allPressedKeys.contains(65)) {
				bewegeSpieler(0);
			}
			if (allPressedKeys.contains(38) || allPressedKeys.contains(87)) {
				bewegeSpieler(1);
			}
			if (allPressedKeys.contains(39) || allPressedKeys.contains(68)) {
				bewegeSpieler(2);
			}
			if (allPressedKeys.contains(40) || allPressedKeys.contains(83)) {
				bewegeSpieler(3);
			}
		}
	}
	
	/**
	 * Wenn die Taste "P" gedrÃ¼ckt ist, wird der Status geÃ¤ndert, um PauseGUI anzuzeigen
	 * 
	 * @author Henrike, Anne
	 */
	
	public void whatKeyPressedPause() {
		if (!getAllPressedKeys().isEmpty()) {
			if (allPressedKeys.contains(80)) { // P - Pause
				
				State.setState(State.STATE.PAUSE);
				Controller.pausiereSpiel();
				allPressedKeys.clear();
				
			} 
		}
	}
	

	/**
	 * Wenn die linke Maustaste gedrï¿½ckt wird, wird leftMouseButtonPressed auf true
	 * gesetzt und pruefeSchuss() aufgerufen.
	 * 
	 * @param e Mausevent
	 * @author Florian
	 */
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			leftMouseButtonPressed = true;
			pruefeSchuss();
		}
	}

	/**
	 * Setzt leftMouseButtonPressed auf false, wenn die linke Maustaste losgelassen
	 * wurde
	 * 
	 * @param e Mausevent
	 * @author Florian
	 */
	public void mouseReleased(MouseEvent e) {
		leftMouseButtonPressed = false;
	}

	/**
	 * ï¿½berprï¿½ft solange, ob geschossen werden darf und erstellt neue Schï¿½sse, bis
	 * die linke Maustaste losgelassen wurde.
	 * 
	 * @author Florian
	 */
	public void pruefeSchuss() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (Controller.getSpieldaten().darfSchieÃŸen() && leftMouseButtonPressed) {
					schiesse();
				} else if (!leftMouseButtonPressed) {
					timer.cancel();
				}
			}
		}, 0, 200);
	}

	/**
	 * Erstellt Schuss und gibt ihm abhängig von der Spielerausrichtung eine Startposition in
	 * einem bestimmten Radius um den Spieler. Wenn Salve aktiviert ist, wird stattdessen eine Salve(3 Schüsse)
	 * abgegeben.
	 * 
	 * @author Florian
	 */
	public void schiesse() {
		JLabel spielerGUI = Controller.getSpielframe().getSpieler();
		int widthSchuss = 10; // eventuell in Klasse/ Frame standardisieren
		// Startposition anpassen,damit vom Rand des Spielerobjekts geschossen wird
		int radius=20;
		int xMittelpunkt=spielerGUI.getX()+(widthSchuss/2);
		int yMittelpunkt=spielerGUI.getY()+(widthSchuss/2);
		
		//Vektor von Mittelpunkt zur Maus skalieren
		Vector2D mittelpunktVektor = new Vector2D(xMittelpunkt,yMittelpunkt);
		Vector2D mausVektor = getMouseAsVector();
		Vector2D verbindungsVektor = Vector2D.getVerbindungsVektor2D(mausVektor, mittelpunktVektor);
		Vector2D startpunktVektor = Vector2D.addiereVektoren(mittelpunktVektor,Vector2D.skaliereVektor((verbindungsVektor), radius));
		int xStart=(int) startpunktVektor.getX();
		int yStart=(int) startpunktVektor.getY();
		
		if(Controller.getSpieldaten().isSalveAn()) {
			Controller.getSpieldaten().createSalve(xStart, yStart, 10, 10, 7);
		} else {
			Controller.getSpieldaten().createSchuss(xStart, yStart, 10, 10, 7);
		}
		
		
	}

	/**
	 * isDiagonaleBewegung() prï¿½ft ob zwei benachbarte Pfeil-Tasten gedrï¿½ckt sind.
	 * Dabei wird auch gespeichert welche Diagonale Richtung grade aktiv ist.
	 * 
	 * @return Gibt true zurï¿½ck wenn zwei benachbarte Pfeil-Tasten gedrï¿½ckt sind und
	 *         false wenn nicht
	 * @author Benedikt
	 */
	public boolean isDiagonaleBewegung() {
		for (int i = 0; i < 4; i++) {
			diagonaleKeysPressed[i] = false;
		}
		if ((allPressedKeys.contains(65) && allPressedKeys.contains(87))
				|| (allPressedKeys.contains(37) && allPressedKeys.contains(38))) { // UP LEFT
			diagonaleKeysPressed[0] = true;
			return true;
		}
		if (allPressedKeys.contains(87) && allPressedKeys.contains(68)
				|| (allPressedKeys.contains(38) && allPressedKeys.contains(39))) { // UP RIGHT
			diagonaleKeysPressed[1] = true;
			return true;
		}
		if (allPressedKeys.contains(83) && allPressedKeys.contains(68)
				|| (allPressedKeys.contains(39) && allPressedKeys.contains(40))) { // DOWN RIGHT
			diagonaleKeysPressed[2] = true;
			return true;
		}
		if (allPressedKeys.contains(65) && allPressedKeys.contains(83)
				|| (allPressedKeys.contains(37) && allPressedKeys.contains(40))) { // DOWN LEFT
			diagonaleKeysPressed[3] = true;
			return true;
		}
		return false;
	}

	/**
	 * bewegeObjekt(JLabel objekt, int x, int y) bewegt das ï¿½bergebene JLabel auf
	 * die ï¿½bergebene Position
	 * 
	 * @param objekt Das zu bewegene Objekt
	 * @param x      Neuer X-Wert
	 * @param y      Neuer Y-Wert
	 * @author Benedikt
	 */
	public void bewegeObjekt(JLabel objekt, int x, int y) {
		objekt.setLocation(x, y);
	}

	public void increaseSpeed() {
		Spieler spieler = Controller.getSpieldaten().getSpieler();
		spieler.setSpeedBoost(true);
		spieler.setSpeed(13);
		
		Timer speedBoost = new Timer();
		TimerTask tt = new TimerTask() {
			boolean off = false;

			public void run() {
				if (off) {
					spieler.setSpeed(7);
					spieler.setSpeedBoost(false);
					speedBoost.cancel();
				} else {
					off = true;
				}
			}
		};
		speedBoost.schedule(tt, 1000, 5000);
	}
	
	/**
	 * Startet einen 10 Sekunden langes Zeitfenster, in welchem der Spieler Salven 
	 * schießen kann.
	 * @author Florian
	 */
	public void startSalveTimer() {
		Timer salveTimer = new Timer();
		TimerTask tt = new TimerTask() {
			
			public void run() {
				Controller.getSpieldaten().setSalveAn(false);
			}
		};
		salveTimer.schedule(tt, 10000); //10 Sekunden delay
	}
	
	/**
	 * bewegeSpieler(int direction) bewegt Spieler anhand der gedrï¿½kten Pfeil-Tasten
	 * in eine Richtung. Kann verweigert werden wenn diese Richtung zu den
	 * blockierten Richtungen gehï¿½rt.
	 * 
	 * @param direction Die Richtung in die sich der Spieler bewegen soll.
	 * @author Benedikt
	 */
	public void bewegeSpieler(int direction) {
		Spieler spieler = Controller.getSpieldaten().getSpieler();
		SpielerGUI spielerGUI = Controller.getSpielframe().getSpieler();
		int digSpeed = spieler.diagonalSpeed();
		int speed = spieler.getSpeed();

		if (!getBlockedMovementKeys()[direction]) {
			int x;
			int y;
			switch (direction) {
			case 0:
				if (diagonaleKeysPressed[0] || diagonaleKeysPressed[3]) {
					x = spielerGUI.getX() - digSpeed;
				} else {
					x = spielerGUI.getX() - speed;
				}
				y = spielerGUI.getY();
				break;
			case 1:
				x = spielerGUI.getX();
				if (diagonaleKeysPressed[0] || diagonaleKeysPressed[1]) {
					y = spielerGUI.getY() - digSpeed;
				} else {
					y = spielerGUI.getY() - speed;
				}
				break;
			case 2:
				if (diagonaleKeysPressed[1] || diagonaleKeysPressed[2]) {
					x = spielerGUI.getX() + digSpeed;
				} else {
					x = spielerGUI.getX() + speed;
				}
				y = spielerGUI.getY();
				break;
			case 3:
				x = spielerGUI.getX();
				if (diagonaleKeysPressed[2] || diagonaleKeysPressed[3]) {
					y = spielerGUI.getY() + digSpeed;
				} else {
					y = spielerGUI.getY() + speed;
				}
				break;
			default:
				x = 0;
				y = 0;
				break;
			}
			bewegeObjekt(spielerGUI, x, y);
		}

	}

	/**
	 * Bewegt einen Schuss in die Richtung weiter, in die sie abgefeuert wurde.
	 * 
	 * @author Florian
	 */
	public void bewegeSchuss() {
		try {
			for (SchussGUI schuss : Controller.getSpielframe().getSchussGUIListe()) {
				Vector2D pos = new Vector2D(schuss.getX(), schuss.getY());
				Vector2D neuePosition = Vector2D.addiereVektoren(pos, schuss.getDaten().getSchussRichtung());
				bewegeObjekt(schuss, (int) neuePosition.getX(), (int) neuePosition.getY());
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("Unerlaubter Zugriff auf SchussGUIListe in Steuerung");
		}

	}

	/**
	 * bewegeGegner() bewegt Gegner anhand des Verbindungsvektors zum Spieler.
	 * 
	 * @author Benedikt
	 */
	public void bewegeGegner() {
		try {
			for (GegnerGUI gegner : Controller.getSpielframe().getGegnerGUIListe()) {
				Vector2D koordinaten = new Vector2D();
				koordinaten = verfolgeSpieler(gegner);
				bewegeObjekt(gegner, (int) koordinaten.getIntVektor().getX(), (int) koordinaten.getIntVektor().getY());
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("Unerlaubter Zugriff auf GegnerGUIListe in Steuerung");
		}
	}

	/**
	 * updateBlickrichtung() Setzt das Richtungs-Bild des Objekts auf Basis des
	 * Verbindungsvektor zwischen start und ziel.
	 * 
	 * @author Benedikt
	 */
	public void updateBlickrichtung(KollisionsObjekt obj) {
		Vector2D start = new Vector2D(obj.getX() + (obj.getWidth() / 2), obj.getY() + (obj.getHeight() / 2));

		Vector2D ziel = new Vector2D();
		if (obj instanceof SpielerGUI) {
			ziel = new Vector2D(mouseX - 8, mouseY - 30);
		} else if (obj instanceof GegnerGUI) {
			SpielerGUI spieler = Controller.getSpielframe().getSpieler();
			ziel = new Vector2D(spieler.getX(), spieler.getY());
		} else {
			ziel = new Vector2D();
		}

		Vector2D blickrichtungsVektor = Vector2D.getVerbindungsVektor2D(start, ziel);

		
		double winkel = Math.toDegrees(Math.atan2(blickrichtungsVektor.getX(), blickrichtungsVektor.getY()));


		obj.getDaten().setAktuelleRichtungGrad(winkel);

		//obj.getDaten().setAktuelleRichtung(neueBlickrichtung);
	}

	/**
	 * verfolgeSpieler(GegnerGUI gegner) gibt fï¿½r den ï¿½bergebenen Gegner, mithilfe
	 * des Verbindungsvektors zum Spieler, neue Positionsdaten zurï¿½ck. Dazu wird die
	 * Lï¿½nge des Verbindungsvektors zurï¿½ckgerechnet auf die Geschwindigkeit des
	 * Gegners, um die X- und Y-Geschwindigkeit zu ermitteln.
	 * 
	 * @param gegner Gegner der neue Positionsdaten braucht
	 * @return Gibt die neuen Positionsdaten als Vector2D zurï¿½ck
	 * @author Benedikt
	 */
	public Vector2D verfolgeSpieler(GegnerGUI gegner) {
		SpielerGUI spieler = Controller.getSpielframe().getSpieler();

		Vector2D verbindungsVektor = Vector2D.getVerbindungsVektor2DWithObjects(spieler, gegner);
		Vector2D skalierterVektor = Vector2D.skaliereVektor(verbindungsVektor, gegner.getDaten().getSpeed());

		return new Vector2D(gegner.getX() + skalierterVektor.getX(), gegner.getY() + skalierterVektor.getY());
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (!allPressedKeys.contains(key)) {
			allPressedKeys.add(key);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (allPressedKeys.contains(key)) {
			allPressedKeys.remove(Integer.valueOf(key));
		}

		
		if (key == 37) {
			pressedMovementKeys[0] = false;
		}
		if (key == 38) {
			pressedMovementKeys[1] = false;
		}
		if (key == 39) {
			pressedMovementKeys[2] = false;
		}
		if (key == 40) {
			pressedMovementKeys[3] = false;
		}

		if (key == 65) {
			pressedMovementKeys[0] = false;
		}
		if (key == 87) {
			pressedMovementKeys[1] = false;
		}
		if (key == 68) {
			pressedMovementKeys[2] = false;
		}
		if (key == 83) {
			pressedMovementKeys[3] = false;
		}

	}
	

	public ArrayList<Integer> getAllPressedKeys() {
		return allPressedKeys;
	}

	public void setAllPressedKeys(ArrayList<Integer> allPressedKeys) {
		this.allPressedKeys = allPressedKeys;
	}

	public boolean[] getPressedMovementKeys() {
		return pressedMovementKeys;
	}

	public void setPressedMovementKeys(boolean[] pressedMovementKeys) {
		this.pressedMovementKeys = pressedMovementKeys;
	}

	public boolean[] getBlockedMovementKeys() {
		return blockedMovementKeys;
	}

	public void setBlockedMovementKeys(boolean[] blockedMovementKeys) {
		this.blockedMovementKeys = blockedMovementKeys;
	}

	public Vector2D getMouseAsVector() {
		return new Vector2D(mouseX, mouseY);
	}

	public void setMousePosition(int x, int y) {
		mouseX = x;
		mouseY = y;
	}

	public boolean isLeftMouseButtonPressed() {
		return leftMouseButtonPressed;
	}

	public void setLeftMouseButtonPressed(boolean leftMouseButtonPressed) {
		this.leftMouseButtonPressed = leftMouseButtonPressed;
	}

}
