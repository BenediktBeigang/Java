package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import GlobaleKlassen.Controller;
import GlobaleKlassen.Level;
import GlobaleKlassen.ReaderWriter;
import GlobaleKlassen.State.STATE;
import GlobaleKlassen.TimerManager;
import spieldaten.Gegner;
import spieldaten.Schuss;
import spieldaten.Wand;

/**
 * Diese Klasse stellt das JFrame bereit, welches angezeigt wird. Au�erdem
 * dient sie als Knotenpunkt um die visuellen Objekte wie zB einen Gegner zu
 * erstellen, beinhaltet die Kollisionsphysik und die Steuerung.
 * 
 * @author Benedikt
 */
public class Frame extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private static String DIR_SEPERATOR = java.io.File.separator;

	private Steuerung steuerung;
	private Kollision kollision;

	private JPanel contentPane;
	private SpielerGUI spielerGUI;
	private ItemGUI item;
	private ArrayList<GegnerGUI> gegnerGUIListe;
	private ArrayList<WandGUI> wandGUIListe;
	private ArrayList<SchussGUI> schussGUIListe;

	private ArrayList<AnimationGUI> animationenListe;
	private ArrayList<AnimationGUI> zuLoeschendeAnimationen;

	private Anzeige lebensAnzeige;
	private Anzeige cooldownAnzeige;

	private ArrayList<GegnerGUI> zuLoeschendeGegner;
	private ArrayList<SchussGUI> zuLoeschendeSchuesse;

	private JLabel zeit;
	private JLabel kills;
	private JLabel zeitKillsBg;
	private JLabel scoreBg;
	private JLabel fps;
	private JLabel score;
	private JLabel barNewHigscoreBG;
	private JLabel barNewHigscore;
	private JLabel highscoreText;

	private int frame;
	private double framesPerSecond;
	private boolean reachedNewHigscore;

	private BufferedImage bodenImage;
	int test = 0;

	/**
	 * Frame() ist der Konstruktor der Klasse. Hier werden alle Variablen
	 * initialisiert und das Frame in den Ausgangszustand gesetzt.
	 * 
	 * @author Benedikt
	 */
	public Frame() {
		initialisiere();
		printWalls(Controller.getGeladenesLevel());
		runKollision();
		runBewegung();
		runImageRenderer();
	}

	/**
	 * runKollision() startet einen Timer welche im Intervall von 1ms pr�ft, ob es
	 * zu einer Kollision zwischen Objekten gekommen ist.
	 * 
	 * @author Benedikt
	 */
	public void runKollision() {
		Timer time = new Timer(16, (ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Controller.getStatus().getState() == STATE.GAME && spielerGUI != null
						&& Controller.getStatus().getState() == STATE.GAME) {
					kollision.playerCollisionDetection();
					kollision.gegnerCollisionDetection();
					kollision.schussCollisionDetection();
				}
			}
		});
		time.start();
		TimerManager.addTimer(time);
	}

	/**
	 * runImageRenderer() startet einen Timer der im Intervall von 10ms
	 * 
	 * @author Benedikt
	 */
	public void runImageRenderer() {
		Timer time = new Timer(16, (ActionListener) new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (Controller.getStatus().getState() == STATE.GAME && spielerGUI != null
						&& Controller.getStatus().getState() == STATE.GAME) {
					// Frame
					updateText();

					// Spieler
					steuerung.updateBlickrichtung(spielerGUI);
					spielerGUI.updateBilder();
					spielerGUI.updateAnzeigenPosition();
					cooldownAnzeige.setValue(Controller.getSpieldaten().getCooldown());

					// Gegner
					for (GegnerGUI gegner : gegnerGUIListe) {
						steuerung.updateBlickrichtung(gegner);
						gegner.repaint();
					}
					updateGegnerBilder();

					// Schuss
					for (SchussGUI schuss : schussGUIListe) {
						schuss.repaint();
						schuss.revalidate();
					}

					// Testanimation
					deleteAnimation();
					try {
						for (AnimationGUI animation : animationenListe) {
							animation.updateBilder();
							animation.repaint();
						}
					} catch (ConcurrentModificationException e) {
						System.out.println("ERROR - Unerlaubter Zugriff auf Animationen");
					}
				}
				frame++;
			}
		});
		time.start();
		TimerManager.addTimer(time);
	}

	/**
	 * runBewegung() startet einen Timer der im Intervall von 20ms, die
	 * Bewegungsrichtung der Objekte aktualisiert und die Objekte bewegt. Au�erdem
	 * wird der Cooldown aktualisiert.
	 * 
	 * @author Benedikt
	 */
	public void runBewegung() {
		Timer time = new Timer(20, (ActionListener) new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (/* Controller.getSpieldaten().isSpielAn() */ Controller.getStatus().getState() == STATE.GAME
						&& spielerGUI != null && Controller.getStatus().getState() == STATE.GAME) {
					steuerung.whatKeyPressedPause();
					steuerung.whatDirectionPressed();
					steuerung.bewegeGegner();
					steuerung.bewegeSchuss();
					steuerung.isDiagonaleBewegung();
					Controller.getSpieldaten().decreaseCooldown();
				}
			}
		});
		time.start();
		TimerManager.addTimer(time);
	}

	/**
	 * updateText() aktualisiert die JLabels zeit und kill
	 * 
	 * @author Benedikt
	 */
	public void updateText() {
		int sekunden = Controller.getSpieldaten().getSekunden();
		int min = (int) sekunden / 60;
		int restSekunden = (int) sekunden % 60;
		if (restSekunden < 10) {
			zeit.setText("Zeit: " + min + ":0" + restSekunden);
		} else {
			zeit.setText("Zeit: " + min + ":" + restSekunden);
		}

		kills.setText("Kills: " + Controller.getSpieldaten().getGetoeteteGegner());
		fps.setText("FPS: " + framesPerSecond);
		score.setText(Controller.getSpieldaten().getScore() + " EP");
		
		// Berechne Score Anzeigen
		double ratio = (double)Controller.getSpieldaten().getScore()/(double)Controller.getGeladenesLevel().getHighscore();
		if(ratio<1) {
			int width = (int)Math.round(barNewHigscoreBG.getWidth()*ratio);
			int x = barNewHigscoreBG.getX() + barNewHigscoreBG.getWidth()/2 -width/2;
			int pointsToNewHighscore = Controller.getGeladenesLevel().getHighscore() - Controller.getSpieldaten().getScore();
			barNewHigscore.setBounds(x, barNewHigscore.getY(), width, barNewHigscore.getHeight());
			highscoreText.setText(pointsToNewHighscore + " EP bis zum Highscore");
		} else {
			if(!reachedNewHigscore) {
				reachedNewHigscore = true;
				barNewHigscore.setBounds(barNewHigscoreBG.getBounds());
				highscoreText.setText("Neuen Highscore aufgestellt!");
				score.setForeground(Color.green);
				Controller.getSoundboard().playScore();
				Controller.getSpielframe().addAnimation(450, 30, "test");
			}
		}
		
		if (framesPerSecond > 59) {
			fps.setForeground(Color.black);
		}
		if (framesPerSecond <= 59) {
			fps.setForeground(Color.red);
		}
	}

	/**
	 * updateGegnerBilder() ruft die Funktion updateBilder in der Klasse GegnerGUI
	 * f�r jeden Gegner auf.
	 * 
	 * @author Benedikt
	 */
	public void updateGegnerBilder() {
		for (GegnerGUI gegner : gegnerGUIListe) {
			gegner.updateBilder();
		}
	}

	/**
	 * printWalls() wird im Konstruktor aufgerufen. Erstellt und f�gt die Wand
	 * Objekte der Welt in das contetPane hinzu.
	 * 
	 * @param w Die Welt die geprintet werden soll im Frame.
	 * @author Benedikt
	 */
	public void printWalls(Level l) {
		int pixels_x, pixels_y;
		for (int y = 0; y < 48; y++) {
			pixels_y = y * 20;
			for (int x = 0; x < 48; x++) {
				pixels_x = x * 20;
				if (ReaderWriter.hasWall(x, y, l.getMap())) {
					Wand wandDaten = new Wand(pixels_x, pixels_y, 20, 20, 0);
					WandGUI wand = new WandGUI(wandDaten);
					wand.setLocation(pixels_x, pixels_y);
					wand.setSize(20, 20);
					// wand.setBackground(Color.black);
					wand.setOpaque(false);

					String textureName = Controller.getGeladenesLevel().getTextureNameAtXY(x, y);
					File file = new File("./Assets" + DIR_SEPERATOR + textureName);
					if (file.exists()) {
						ImageIcon img = new ImageIcon("./Assets" + DIR_SEPERATOR + textureName);
						wand.setIcon(img);
					} else {
						ImageIcon img = new ImageIcon("./Assets" + DIR_SEPERATOR + "error.png");
						wand.setIcon(img);
						System.out.println("ERROR - " + textureName + " not found");
					}

					wandGUIListe.add(wand);
					contentPane.add(wand);
				}
			}
		}
	}

	/**
	 * deaktiviereSpielobjekt(JLabel zuLoeschendesJLabel) deaktiviert auf Basis des
	 * Klassentyps das Objekt korrekt und f�gt es den L�schlisten hinzu.
	 * 
	 * @param zuLoeschendesJLabel JLabel das gel�scht werden soll
	 */
	public void deaktiviereSpielobjekt(JLabel zuLoeschendesJLabel) {
		zuLoeschendesJLabel.setVisible(false);

		if (zuLoeschendesJLabel instanceof GegnerGUI) {
			Gegner gegner = ((GegnerGUI) zuLoeschendesJLabel).getDaten();
			gegner.setAktiviert(false);
			gegner.setTodesgrund("schuss");
			zuLoeschendeGegner.add((GegnerGUI) zuLoeschendesJLabel);
		} else if (zuLoeschendesJLabel instanceof SchussGUI) {
			zuLoeschendeSchuesse.add((SchussGUI) zuLoeschendesJLabel);
			((SchussGUI) zuLoeschendesJLabel).getDaten().setAktiviert(false);
		} else if (zuLoeschendesJLabel instanceof ItemGUI) {
			// TODO
		}
	}

	/**
	 * addGegner() erstellt neuen Gegner und f�gt diesen dem contentPane hinzu.
	 * 
	 * @param posX   X-Wert der Startposition
	 * @param posY   Y-Wert der Startposition
	 * @param width  Breite des Gegners
	 * @param height H�he des Gegners
	 * @param gegner Daten des Gegners (f�r einfachere R�ckverfolgung der Daten)
	 * @param id     Identifikationsnummer zum Verbinden der GUI Klasse mit der
	 *               Datenklasse
	 * @author Benedikt
	 */
	public void addGegner(int posX, int posY, int width, int height, Gegner gegner) {
		GegnerGUI neuerGegner = new GegnerGUI(gegner, posX, posY, width, height);
		neuerGegner.setBackground(new Color(0, 0, 0, 0));
		gegnerGUIListe.add(neuerGegner);
		contentPane.add(neuerGegner);
		Controller.getSpielframe().addAnimation(neuerGegner, "walking", 0, true, true);
	}

	/**
	 * F�gt das JLabel eines Schusses dem Frame hinzu.
	 * 
	 * @param xPos   Startkoordinate auf der x-Achse
	 * @param yPos   Startkoordinate auf der y-Achse
	 * @param width  Breite des Swing-Labels
	 * @param height H�he des Swing-Labels
	 * @param schuss Schuss der eingef�gt werden soll
	 * @author Florian
	 */
	public void addSchuss(int xPos, int yPos, int width, int height, Schuss schuss) {
		SchussGUI neuerSchuss = new SchussGUI(schuss, xPos, yPos, width, height);
		// neuerSchuss.setBackground(Color.green);
		// neuerSchuss.setOpaque(true);
		Controller.getSpielframe().addAnimation(Controller.getSpielframe().getSpieler(), "sling", 0, false, true);
		schussGUIListe.add(neuerSchuss);
		contentPane.add(neuerSchuss);
		Controller.getSoundboard().playShot();
	}

	public void addAnimation(int posX, int posY, String type) {
		AnimationGUI neueAnimation = new AnimationGUI(posX, posY, type);
		animationenListe.add(neueAnimation);
		contentPane.add(neueAnimation);
	}

	public void addAnimation(KollisionsObjekt obj, String type, int playTime, boolean hasLoop, boolean hasRotation) {
		AnimationGUI neueAnimation = new AnimationGUI(obj, type, playTime, hasLoop, hasRotation);
		animationenListe.add(neueAnimation);
		contentPane.add(neueAnimation);
	}

	/**
	 * L�scht alle Sch�sse aus der GUIListe und der Anzeige, die in
	 * zuLoeschendeSchuesse stehen und leert zuLoeschendeSchuesse.
	 * 
	 * @author Florian
	 */
	public void deleteSchuesse() {
		if (zuLoeschendeSchuesse != null) {
			for (SchussGUI schuss : zuLoeschendeSchuesse) {
				Controller.getSpieldaten().getSchussDatenListe().remove(schuss.getDaten());
				schussGUIListe.remove(schuss);
				contentPane.remove(schuss);
			}
			zuLoeschendeSchuesse.clear();
		}
	}

	/**
	 * deleteGegner() arbeitet die Liste der zu L�schenden Gegner ab. Wird von
	 * runSpielogik() in Spiel aufgerufen.
	 * 
	 * @author Benedikt
	 */
	public void deleteGegner() {
		if (zuLoeschendeGegner != null) {
			for (GegnerGUI gegner : zuLoeschendeGegner) {
				Controller.getSpieldaten().getGegnerDatenListe().remove(gegner.getDaten());
				gegnerGUIListe.remove(gegner);
				contentPane.remove(gegner);
			}
			zuLoeschendeGegner.clear();
		}
	}

	public void deleteAnimation() {
		for (AnimationGUI zuLoeschendeAnimation : zuLoeschendeAnimationen) {
			contentPane.remove(zuLoeschendeAnimation);
			animationenListe.remove(zuLoeschendeAnimation);
		}
		zuLoeschendeAnimationen.clear();
	}

	public void addDeleteAnimationList(AnimationGUI animation) {
		zuLoeschendeAnimationen.add(animation);
	}

	public void addItem(ItemGUI item) {
		this.item = item;
		item.setOpaque(true);
		contentPane.add(item);
	}

	public void deleteItem() {
		item.setVisible(false);
		Controller.getSpieldaten().setAktivesItem(false);
		Controller.getSpieldaten().setAktuellesItem(null);
		item = null;
	}

	public void optionPane() {
		JOptionPane.showMessageDialog(this, "Keine Leben mehr", "Inane error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * initialisiere() initalisiert alle Variablen und Listen der Klasse.
	 * 
	 * @author Benedikt
	 */
	public void initialisiere() {
		steuerung = new Steuerung();
		kollision = new Kollision();

		spielerGUI = new SpielerGUI(Controller.getSpieldaten().getSpieler(), 465, 465, 40, new Color(0, 0, 0, 0));
		lebensAnzeige = new Anzeige(10, 10, Color.red);
		cooldownAnzeige = new Anzeige(Controller.getSpieldaten().getMAX_COOLDOWN(), 0, Color.blue);

		Font statsFont = new Font("Source Sans Pro", Font.BOLD, 18);
		Font scoreFont = new Font("Source Sans Pro", Font.BOLD, 24);
		Font smallFont = new Font("Source Sans Pro", Font.BOLD, 12);
		
		reachedNewHigscore = false;

		zeit = new JLabel();
		zeit.setForeground(Color.black);
		zeit.setFont(statsFont);
		zeit.setBounds(10, 5, 75, 30);
		zeit.setText("Zeit: 0:00");

		kills = new JLabel();
		kills.setForeground(Color.black);
		kills.setFont(statsFont);
		kills.setBounds(100, 5, 75, 30);
		kills.setText("Kills: 0");

		zeitKillsBg = new JLabel();
		zeitKillsBg.setBounds(0, 0, 200, 40);
		zeitKillsBg.setText("Kills: 0");
		ImageIcon img = new ImageIcon("./Assets" + DIR_SEPERATOR + "zeitKillsBg.png");
		zeitKillsBg.setIcon(img);

		fps = new JLabel();
		fps.setForeground(Color.black);
		fps.setBounds(900, 0, 75, 20);
		fps.setText("FPS: 0");

		scoreBg = new JLabel();
		scoreBg.setBounds(360, 0, 200, 60);
		scoreBg.setText("Kills: 0");
		img = new ImageIcon("./Assets" + DIR_SEPERATOR + "scoreBg.png");
		scoreBg.setIcon(img);
		
		score = new JLabel("0 EP", SwingConstants.CENTER);
		score.setForeground(Color.black);
		score.setFont(scoreFont);
		score.setBounds(360, 0, 200, 40);
		
		highscoreText = new JLabel("x EP bis zum Highscore", SwingConstants.CENTER);
		highscoreText.setForeground(Color.black);
		highscoreText.setFont(smallFont);
		highscoreText.setBounds(360, 42, 200, 10);
		
		barNewHigscoreBG = new JLabel();
		barNewHigscoreBG.setBackground(Color.black);
		barNewHigscoreBG.setOpaque(true);
		barNewHigscoreBG.setFont(scoreFont);
		barNewHigscoreBG.setBounds(scoreBg.getX()+10, 35, 180, 5);
		
		barNewHigscore = new JLabel();
		barNewHigscore.setBackground(Color.green);
		barNewHigscore.setOpaque(true);
		barNewHigscore.setFont(scoreFont);
		barNewHigscore.setBounds(scoreBg.getX()+10, 35, 0, 5);
		
		gegnerGUIListe = new ArrayList<GegnerGUI>();
		wandGUIListe = new ArrayList<WandGUI>();
		schussGUIListe = new ArrayList<SchussGUI>();
		zuLoeschendeGegner = new ArrayList<GegnerGUI>();

		animationenListe = new ArrayList<AnimationGUI>();
		zuLoeschendeAnimationen = new ArrayList<AnimationGUI>();

		setZuLoeschendeSchuesse(new ArrayList<SchussGUI>());

		contentPane = initialisiereContentPane();
		initialisiereFrame();

	}

	public JPanel initialisiereContentPane() {
		File file = new File("Assets/Background/" + Controller.getGeladenesLevel().getBgTextur());
		try {
			bodenImage = ImageIO.read(file);
		} catch (IOException ex) {
			System.out.println("ERROR - Hintergrundbild nicht gefunden " + file.getAbsolutePath());
		}

		JPanel newContentPane = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bodenImage, 0, 0, this);
			}
		};

		newContentPane.setBounds(0, 0, 980, 1000);
		newContentPane.setLayout(null);
		newContentPane.setBackground(Color.white);
		newContentPane.grabFocus();
		newContentPane.setBackground(Color.gray);

		newContentPane.add(spielerGUI);
		newContentPane.add(lebensAnzeige);
		newContentPane.add(cooldownAnzeige);
		newContentPane.add(zeit);
		newContentPane.add(kills);
		newContentPane.add(zeitKillsBg);
		newContentPane.add(fps);
		newContentPane.add(barNewHigscore);
		newContentPane.add(highscoreText);
		newContentPane.add(barNewHigscoreBG);
		newContentPane.add(score);
		newContentPane.add(scoreBg);

		return newContentPane;

	}

	public void initialisiereFrame() {
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBounds(500, 100, 975, 1000);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().add(contentPane);
		this.setVisible(true);
	}

	/**
	 * Funktionen der Listener-Interfaces
	 */

	// Keyboard Listener
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		steuerung.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		steuerung.keyReleased(e);
	}

	// Mouse Motion Listener
	public void mouseDragged(MouseEvent e) {
		steuerung.setMousePosition(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		steuerung.setMousePosition(e.getX(), e.getY());
	}

	// Mouse Listener
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		steuerung.mousePressed(e);

	}

	public void mouseReleased(MouseEvent e) {
		steuerung.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public Steuerung getSteuerung() {
		return steuerung;
	}

	public void setSteuerung(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public Kollision getKollision() {
		return kollision;
	}

	public void setKollision(Kollision kollision) {
		this.kollision = kollision;
	}

	public SpielerGUI getSpieler() {
		return spielerGUI;
	}

	public void setSpieler(SpielerGUI spieler) {
		this.spielerGUI = spieler;
	}

	public ItemGUI getItem() {
		return item;
	}

	public void setItem(ItemGUI item) {
		this.item = item;
	}

	public ArrayList<WandGUI> getWandGUIListe() {
		return wandGUIListe;
	}

	public void setWandGUIListe(ArrayList<WandGUI> wandGUIListe) {
		this.wandGUIListe = wandGUIListe;
	}

	public ArrayList<SchussGUI> getSchussGUIListe() {
		return schussGUIListe;
	}

	public void setSchussGUIListe(ArrayList<SchussGUI> schussGUIListe) {
		this.schussGUIListe = schussGUIListe;
	}

	public ArrayList<GegnerGUI> getGegnerGUIListe() {
		return gegnerGUIListe;
	}

	public void setGegnerGUIListe(ArrayList<GegnerGUI> gegnerGUIListe) {
		this.gegnerGUIListe = gegnerGUIListe;
	}

	public ArrayList<GegnerGUI> getZuLoeschendeGegner() {
		return zuLoeschendeGegner;
	}

	public void setZuLoeschendeGegner(ArrayList<GegnerGUI> zuLoeschendeGegner) {
		this.zuLoeschendeGegner = zuLoeschendeGegner;
	}

	public JLabel getZeit() {
		return zeit;
	}

	public void setZeit(JLabel zeit) {
		this.zeit = zeit;
	}

	public JLabel getKills() {
		return kills;
	}

	public void setKills(JLabel kills) {
		this.kills = kills;
	}

	public ArrayList<SchussGUI> getZuLoeschendeSchuesse() {
		return zuLoeschendeSchuesse;
	}

	public void setZuLoeschendeSchuesse(ArrayList<SchussGUI> zuLoeschendeSchuesse) {
		this.zuLoeschendeSchuesse = zuLoeschendeSchuesse;
	}

	public Anzeige getLebensAnzeige() {
		return lebensAnzeige;
	}

	public void setLebensAnzeige(Anzeige lebensAnzeige) {
		this.lebensAnzeige = lebensAnzeige;
	}

	public Anzeige getCooldownAnzeige() {
		return cooldownAnzeige;
	}

	public void setCooldownAnzeige(Anzeige cooldownAnzeige) {
		this.cooldownAnzeige = cooldownAnzeige;
	}

	public double getFramesPerSecond() {
		return framesPerSecond;
	}

	public SpielerGUI getSpielerGUI() {
		return spielerGUI;
	}

	public void setFramesPerSecond(double framesPerSecond) {
		this.framesPerSecond = framesPerSecond;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

}
