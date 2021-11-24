package gui;

import java.util.ConcurrentModificationException;

import GlobaleKlassen.Controller;
import GlobaleKlassen.Vector2D;

public class Kollision {

	/**
	 * Koollision() ist der leere Konstruktor der Klasse.
	 * 
	 * @author Benedikt
	 */
	public Kollision() {
	}

	/**
	 * playerCollisionDetection() prüft ob der Spieler mit einer Wand oder den
	 * Framebegrenzungen kollidiert um anschließend darauf zu reagieren. Bei
	 * Kollision wird die entsprechende Bewegungstaste blockiert.
	 * 
	 * @author Benedikt
	 */
	public void playerCollisionDetection() {
		blockArrow(-1);
		SpielerGUI spieler = Controller.getSpielframe().getSpieler();
		try {
			for (WandGUI wand : Controller.getSpielframe().getWandGUIListe()) {
				if (isClose(spieler, wand)) {
					blockCorrectArrowKeyWall(wand);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("Unerlaubter Zugriff auf WandGUIListe in Kollision -> PlayerDetection");
		}
		ItemGUI item = Controller.getSpielframe().getItem();
		if (item != null && isClose(spieler, item)) {
			Controller.getSpieldaten().itemAktion(item.getDaten());
			
			Controller.getSpieldaten().increaseScore(500);
			Controller.getSpielframe().addAnimation(Controller.getSpielframe().getSpieler(), "500pt", 0, false, false);
			
			Controller.getSpielframe().deleteItem();
		}
		blockCorrectArrowKeyBoundaries();

	}

	/**
	 * gegnerCollisionDetection() prüft ob ein Gegner mit einer Wand oder dem
	 * Spieler kollidiert um anschließend darauf zu reagieren. Bei Kollision wird
	 * der Gegner deaktiviert.
	 * 
	 * @author Benedikt
	 */
	public void gegnerCollisionDetection() {
		try {
			for (GegnerGUI gegner : Controller.getSpielframe().getGegnerGUIListe()) {
				if (gegner.getDaten().isAktiviert()) {
					try {
						for (WandGUI wand : Controller.getSpielframe().getWandGUIListe()) {
							if (isClose(gegner, wand)) {
								Controller.getSpielframe().deaktiviereSpielobjekt(gegner);
								Controller.getSpielframe().addAnimation(gegner.getX(), gegner.getY(), "cartoon_hit_sw");
								
								Controller.getSpieldaten().increaseScore(10);
								Controller.getSpielframe().addAnimation(Controller.getSpielframe().getSpieler(), "10pt", 0, false, false);
								
							}
						}
					} catch (ConcurrentModificationException e) {
						System.out.println("ERROR - Unerlaubter Zugriff auf WandGUIListe in Kollision -> GegnerDetection");
					}
					SpielerGUI spieler = Controller.getSpielframe().getSpieler();
					if (isClose(gegner, spieler)) {
						Frame spielframe = Controller.getSpielframe();	
						
						Controller.getSoundboard().playOof();
						spielframe.addAnimation(spieler, "player_hit", 0, false, false);
						
						spielframe.deaktiviereSpielobjekt(gegner);
						Controller.getSpieldaten().getSpieler().decreaseLeben();
						spielframe.getLebensAnzeige().changeValueBy(-1);
						
						System.out.println("Leben: " + Controller.getSpieldaten().getSpieler().getLeben() + "---");
					}
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("ERROR - Unerlaubter Zugriff auf GegnerGUIListe in Kollision -> GegnerDetection");
		}
	}

	/**
	 * schussCollisionDetection() prüft ob ein Schuss mit einer Wand, einem Gegner
	 * oder der Framebegrenzung kollidiert um anschließend darauf zu reagieren. Bei
	 * Kollision wird der Schuss und der Gegner deaktiviert.
	 * 
	 * @author Benedikt
	 */
	public void schussCollisionDetection() {
		try {
			for (SchussGUI schuss : Controller.getSpielframe().getSchussGUIListe()) {
				if (schuss.getDaten().isAktiviert()) {
					// Ausserhalb des Spielfeldes
					if (isSchussOutOfFrame(schuss)) {
						Controller.getSpielframe().deaktiviereSpielobjekt(schuss);
					}
					try {
						// Kollidiert mit Gegner
						for (GegnerGUI gegner : Controller.getSpielframe().getGegnerGUIListe()) {
							if (gegner.getDaten().isAktiviert() && isClose(schuss, gegner)) {
								Controller.getSpielframe().addAnimation(schuss.getX(), schuss.getY(), "small_cracks");
								Controller.getSpielframe().addAnimation(schuss.getX(), schuss.getY(), "cartoon_hit");
								
								Controller.getSpieldaten().increaseScore(100);
								Controller.getSpielframe().addAnimation(Controller.getSpielframe().getSpieler(), "100pt", 0, false, false);
								
								
								Controller.getSpieldaten().increaseKillCounter();
								Controller.getSoundboard().playEnemyGotShot();

								Controller.getSpielframe().deaktiviereSpielobjekt(schuss);
								Controller.getSpielframe().deaktiviereSpielobjekt(gegner);
							}
						}
					} catch (ConcurrentModificationException e) {
						System.out.println("ERROR - Unerlaubter Zugriff auf GegnerGUIListe in Kollision -> SchussDetection");
					}
					// Kollidiert mit Wand
					for (WandGUI wand : Controller.getSpielframe().getWandGUIListe()) {

						if (isClose(schuss, wand)) {
							Controller.getSpielframe().addAnimation(schuss.getX(), schuss.getY(), "small_cracks");
							Controller.getSpielframe().deaktiviereSpielobjekt(schuss);
						}
					}
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("ERROR - Unerlaubter Zugriff auf SchussGUIListe in Kollision -> SchussDetection");
		}
	}

	/**
	 * isSchussOutOfFrame(SchussGUI schuss) prüft ob Schuss die Spielbegrenzung
	 * überquert hat
	 * 
	 * @param schuss Der zu überprüfende Schuss
	 * @return Es wird zurückgegeben ob ein Schuss ausserhalb des SPielfeldes ist.
	 * @author Benedikt
	 */
	public boolean isSchussOutOfFrame(SchussGUI schuss) {
		if (schuss.getX() <= -40 || schuss.getX() >= 1000 || schuss.getY() <= -40 || schuss.getY() >= 1000) {
			return true;
		}
		return false;
	}

	/**
	 * overshootCorrection(int delta, int direction) korrigiert den Effekt des
	 * reinglitchen in eine Wand, wenn die SpielerGUI durch seine Geschwindigkeit in
	 * die Wand gesetzt wird. Das Objekt wird die entsprechende Distanz in die es
	 * sich reingeglitched hat zurückteleportiert.
	 * 
	 * @param delta     Die Distanz in die sich SpielerGUI zuviel geschoben hat.
	 * @param direction Die Richtung in die sich der Spieler bewegt hat.
	 * @author Benedikt
	 */
	public void overshootCorrection(int delta, int direction) {
		SpielerGUI spieler = Controller.getSpielframe().getSpieler();
		switch (direction) {
		case 0:
			spieler.setLocation(spieler.getX() + delta, spieler.getY()); // Left Arrow, Right Movement
			break;
		case 1:
			spieler.setLocation(spieler.getX(), spieler.getY() + delta); // Up Arrow, Down Movement
			break;
		case 2:
			spieler.setLocation(spieler.getX() - delta, spieler.getY()); // Right Arrow, Left Movement
			break;
		case 3:
			spieler.setLocation(spieler.getX(), spieler.getY() - delta); // Down Arrow, Up Movement
			break;
		default:
			break;
		}
	}

	/**
	 * blockArrow() blockiert einen bestimmte Pfeil-Taste
	 * 
	 * @param k Die Richtung die blockiert werden soll. -1 wenn alle Pfeile
	 *          entblockiert werden sollen.
	 * @author Benedikt
	 */
	public void blockArrow(int k) {
		if (k == -1) {
			for (int i = 0; i < 4; i++) {
				Controller.getSpielframe().getSteuerung().getBlockedMovementKeys()[i] = false;
			}
		} else {
			Controller.getSpielframe().getSteuerung().getBlockedMovementKeys()[k] = true;
		}
	}

	/**
	 * blockCorrectArrowKeyBoundaries() blockiert die richtigen Pfeil-Tasten wenn
	 * Spieler die Framegrenzen erreicht.
	 * 
	 * @author Benedikt
	 */
	public void blockCorrectArrowKeyBoundaries() {
		SpielerGUI spieler = Controller.getSpielframe().getSpieler();
		int deltaX = 0;
		int deltaY = 0;
		if (spieler.getX() <= 0) {
			blockArrow(0);
			deltaX = Math.abs(spieler.getX());
			overshootCorrection(deltaX, 0);
		}
		if (spieler.getX() >= 920) {
			blockArrow(2);
			deltaX = Math.abs(spieler.getX() - 920);
			overshootCorrection(deltaX, 2);
		}
		if (spieler.getY() <= 0) {
			blockArrow(1);
			deltaY = Math.abs(spieler.getY());
			overshootCorrection(deltaY, 1);
		}
		if (spieler.getY() >= 920) {
			blockArrow(3);
			deltaY = Math.abs(spieler.getY() - 920);
			overshootCorrection(deltaX, 3);
		}
	}

	/**
	 * blockCorrectArrowKeyWall(WandGUI wand) blockiert die richtigen Pfeil-Tasten
	 * anhand der übergebenen Wand. Dabei wird die relative Position von Spieler und
	 * Wand errechnet um die richtige blockierrichtung zu bestimmen. Ausserdem wird
	 * hier die Funktion overshootCorrection() aufgerufen um den Effekt des
	 * Reinglitchen auszugleichen.
	 * 
	 * @param wand Die blockierende Wand
	 * @author Benedikt
	 */
	public void blockCorrectArrowKeyWall(WandGUI wand) {
		SpielerGUI spieler = Controller.getSpielframe().getSpieler();
		int x1 = spieler.getX() + (spieler.getWidth() / 2);
		int y1 = spieler.getY() + (spieler.getHeight() / 2);

		int x2 = wand.getX() + (wand.getWidth() / 2);
		int y2 = wand.getY() + (wand.getHeight() / 2);

		int deltaX = Math.abs(x1 - x2);
		int deltaY = Math.abs(y1 - y2);

		// System.out.println("DeltaX: " + deltaX + "\nDeltaY: " + deltaY +
		// "\n-------------");

		// Position des Spielers wird mit der Position der Wand verglichen um die
		// richtige Taste zu sperren.
		Vector2D spielerVektor = new Vector2D(x1,y1);
		Vector2D wandVektor = new Vector2D(x2,y2);	
		double distance = Vector2D.getVerbindungsVektor2D(spielerVektor, wandVektor).getLaenge();
				
		if(distance < 42.426) {
			if (deltaX > deltaY) {
				if (x1 <= x2) {
					blockArrow(2);
					overshootCorrection(wand.getDeltaXSpieler(), 2);
				} else if (x1 > x2) {
					blockArrow(0);
					overshootCorrection(wand.getDeltaXSpieler(), 0);
				}
			} else {
				if (y1 <= y2) {
					blockArrow(3);
					overshootCorrection(wand.getDeltaYSpieler(), 3);
				} else if (y1 > y2) {
					blockArrow(1);
					overshootCorrection(wand.getDeltaYSpieler(), 1);
				}
			}
		}
	}

	/**
	 * isClose(KollisionsObjekt obj1, KollisionsObjekt obj2) überprüft für zwei
	 * Kollisionsobjekte ob diese sich berühren.
	 * 
	 * @param obj1 Erstes Objekt
	 * @param obj2 Zewites Objekt
	 * @return Es wird true zurückgegeben wenn sie sich berühren und false wenn
	 *         nicht.
	 * @author Benedikt
	 */
	public boolean isClose(KollisionsObjekt obj1, KollisionsObjekt obj2) {
		int[] xyValuesObj1 = GUIObjektHilfsfunktionen.getXYMinMax(obj1);
		int[] xyValuesObj2 = GUIObjektHilfsfunktionen.getXYMinMax(obj2);

		int leftX_S = xyValuesObj1[0];
		int rightX_W = xyValuesObj2[1];

		int leftX_W = xyValuesObj2[0];
		int rightX_S = xyValuesObj1[1];

		int upY_S = xyValuesObj1[2];
		int downY_W = xyValuesObj2[3];

		int upY_W = xyValuesObj2[2];
		int downY_S = xyValuesObj1[3];

		if (obj1 instanceof WandGUI && obj2 instanceof SpielerGUI) {
			int deltaX = Math.min(Math.abs(leftX_S - rightX_W), Math.abs(leftX_W - rightX_S));
			int deltaY = Math.min(Math.abs(upY_S - downY_W), Math.abs(upY_W - downY_S));
			((WandGUI) obj1).setDeltaXSpieler(deltaX);
			((WandGUI) obj1).setDeltaYSpieler(deltaY);
		} else if (obj2 instanceof WandGUI && obj1 instanceof SpielerGUI) {
			int deltaX = Math.min(Math.abs(leftX_S - rightX_W), Math.abs(leftX_W - rightX_S));
			int deltaY = Math.min(Math.abs(upY_S - downY_W), Math.abs(upY_W - downY_S));
			((WandGUI) obj2).setDeltaXSpieler(deltaX);
			((WandGUI) obj2).setDeltaYSpieler(deltaY);
		}

		// Berechnung der Überlappung
		if (leftX_S <= rightX_W && leftX_W <= rightX_S && upY_S <= downY_W && upY_W <= downY_S) {
			return true;
		}
		return false;
	}

}
