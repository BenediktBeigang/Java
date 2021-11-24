package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import GlobaleKlassen.Controller;
import GlobaleKlassen.Level;
import GlobaleKlassen.State;

/**
 * Diese Klasse stellt das JFrame bereit, welches angezeigt wird, wenn das Hauptmen�
 * angezeigt wird.
 * Zudem lassen sich die unterschiedlichen Level ausw�hlen.
 * 
 * @author Henrike, Anne
 */
public class MenuGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, menuButtonPanel, levelPanel;
	
	private static String DIR_SEPERATOR = java.io.File.separator;
	
	private Level[] alleLevel;
	private Level currentLevel;
	private int currentLevelInt = 0;
	
	private JButton startButton, levelButton, endButton, naechstesLvelButton, vorherigesLevelButton, lZurueckButton, muteButton; 
	private JLabel levelTitel, levelImage, levelNummer;
	private JTextArea levelDescription;
	private Font normalFont, titleFont, textFont;
	
	private BufferedImage bodenImage;

	/**
	 * MenuGUI() ist der Konstruktor der Klasse. Alle Variablen werden initialisiert.
	 * Die JButton, welche im Hauptmen� angezeigt werden, und ihre Funktionen werden erstellt
	 * 
	 * @author Henrike, Anne
	 */
	public MenuGUI() {
		alleLevel = Controller.getAlleLevel();
		
		if(checkForFirstLaunch()) {
			Controller.getSoundboard().pauseMusic();
			Controller.startCinematic();
		}
		
		if(alleLevel.length==0) {
			System.out.println("ERROR - Kein Level gefunden");
		}
		
		currentLevel = alleLevel[currentLevelInt];
		
		titleFont = new Font( "Arial", Font.BOLD, 16);
		textFont = new Font( "Courier", Font.PLAIN, 12);
		
		this.setBounds(500, 100, 960, 960);
//		this.setLayout(null);
		this.setBackground(Color.white);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
//		contentPane.grabFocus();
		contentPane = new JPanel();
		contentPane.setBackground(Color.blue);
		contentPane.setBounds(0, 0, 960, 960);
		contentPane.setLayout(null);
		
		menuButtonPanel = new JPanel();
	    menuButtonPanel.setBackground(Color.black);
	    menuButtonPanel.setBounds(700, 680, 200, 230);
	    menuButtonPanel.setLayout(null);
	    
	    levelPanel = new JPanel();
//	    levelPanel.setBounds(600,600,220,170);
	    levelPanel.setBackground(Color.black);
	    levelPanel.setBounds(540, 680, 400, 200);
	    levelPanel.setLayout(null);
	    levelPanel.setVisible(false);
	    
	    startButton = new JButton("START");
	    startButton.setBounds(5, 5, 190, 60);
	    startButton.setBackground(Color.GRAY);
	    startButton.setForeground(Color.white);
	    startButton.setFont(normalFont);
	    startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.getSoundboard().playClick();		
				Controller.setGeladenesLevel(currentLevel);
				schliesseFenster();
		        State.setState(State.STATE.GAME);
		        Controller.startGame();
			}
	    });
    
    	levelButton = new JButton("LEVEL");
    	levelButton.setBackground(Color.GRAY);
    	levelButton.setForeground(Color.white);
    	levelButton.setFont(normalFont);
    	levelButton.setBounds(5, 70, 190, 60);
    	levelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.getSoundboard().playClick();
				menuButtonPanel.setVisible(false);
				levelPanel.setVisible(true);
			}
    	});
    
    	endButton = new JButton("ENDE");
    	endButton.setBackground(Color.GRAY);
    	endButton.setForeground(Color.white);
    	endButton.setFont(normalFont);
    	endButton.setBounds(5, 135, 190, 60);
    	endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.getSoundboard().playClick();
				System.exit(0);
			}
    	});
    	
    	muteButton = new JButton();
    	muteButton.setBackground(Color.GRAY);
    	muteButton.setForeground(Color.white);
    	muteButton.setFont(normalFont);
    	muteButton.setBounds(5, 200, 25, 25);
    	try {
			BufferedImage img = ImageIO.read(new File("Assets" + DIR_SEPERATOR + "Background" + DIR_SEPERATOR + "Unmute.png"));
			muteButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			System.out.println("ERROR - Menue - Unmute Bild wurde nicht gefunden");
		}
    	muteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Controller.getSoundboard().getMusicVolume()>0) {
					Controller.getSoundboard().setMusicVolume(0);
					try {
						BufferedImage img = ImageIO.read(new File("Assets" + DIR_SEPERATOR + "Background" + DIR_SEPERATOR + "Mute.png"));
						muteButton.setIcon(new ImageIcon(img));
					} catch (IOException ex) {
						System.out.println("ERROR - Menue - Mute Bild wurde nicht gefunden");
					}
				} else {
					Controller.getSoundboard().setMusicVolume(70);
					try {
						BufferedImage img = ImageIO.read(new File("Assets" + DIR_SEPERATOR + "Background" + DIR_SEPERATOR + "Unmute.png"));
						muteButton.setIcon(new ImageIcon(img));
					} catch (IOException ex) {
						System.out.println("ERROR - Menue - Unmute Bild wurde nicht gefunden");
					}
				}
				Controller.getSoundboard().stopMusic();
				Controller.getSoundboard().playMainMenuMusic();
				System.out.println("INFO - Musiklautst�rke: " + Controller.getSoundboard().getMusicVolume() + "%");
			}
    	});
    
    	vorherigesLevelButton = new JButton ("<");
    	vorherigesLevelButton.setBackground(Color.black);
    	vorherigesLevelButton.setBounds(5, 5, 30, 30);
    	try {
			BufferedImage arrowRight = ImageIO.read(new File("Assets" + DIR_SEPERATOR + "arrowLeft.png"));
			ImageIcon icon = new ImageIcon(arrowRight);
			vorherigesLevelButton.setIcon(icon);
		} catch (IOException ex) {
			System.out.println("ERROR - Icon Menu arrowLeft nicht gefunden");
		}
    	vorherigesLevelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.getSoundboard().playClick();
				if(currentLevelInt>0) {
					currentLevelInt--;
				} else {
					currentLevelInt = alleLevel.length - 1;
				}
				currentLevel = alleLevel[currentLevelInt];
				updateLevelDescription();
			}
    	});
    	
    	naechstesLvelButton = new JButton (">");
    	naechstesLvelButton.setBackground(Color.black);
    	naechstesLvelButton.setBounds(levelPanel.getWidth()-35, 5, 30, 30);
    	try {
			BufferedImage arrowRight = ImageIO.read(new File("Assets" + DIR_SEPERATOR + "arrowRight.png"));
			ImageIcon icon = new ImageIcon(arrowRight);
			naechstesLvelButton.setIcon(icon);
		} catch (IOException ex) {
			System.out.println("ERROR - Icon Menu arrowRight nicht gefunden");
		}
    	naechstesLvelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.getSoundboard().playClick();
				if(currentLevelInt<alleLevel.length-1) {
					currentLevelInt++;
				} else {
					currentLevelInt = 0;
				}
				currentLevel = alleLevel[currentLevelInt];
				updateLevelDescription();
			}
    	});
    
    	/*
    	lDreiButton = new JButton ("Level 03");
    	lDreiButton.setBackground(Color.red);
    	lDreiButton.setForeground(Color.white);
    	lDreiButton.setFont(normalFont);
    	lDreiButton.setBounds(5, 102, 190, 50);
    	*/
    	
    	levelTitel = new JLabel ("...");
    	//levelTitel.setBackground(Color.red);
    	levelTitel.setForeground(Color.white);
    	levelTitel.setFont(titleFont);
    	levelTitel.setBounds(45, 5, 310, 30);
    	
    	levelImage = new JLabel ();
    	levelImage.setBackground(Color.darkGray);
    	levelImage.setOpaque(true);
    	levelImage.setFont(titleFont);
    	levelImage.setBounds(10, 40, 80, 80);
    	
    	levelDescription = new JTextArea ("...");
    	levelDescription.setOpaque(false);
    	levelDescription.setEditable(false);
    	levelDescription.setForeground(Color.white);
    	levelDescription.setFont(textFont);
    	levelDescription.setLineWrap(true);
    	levelDescription.setWrapStyleWord(true);
    	levelDescription.setBounds(100, 40, 290, 120);
    	
    	lZurueckButton = new JButton ("Zurück");
    	lZurueckButton.setBackground(Color.GRAY);
    	lZurueckButton.setForeground(Color.white);
    	lZurueckButton.setFont(normalFont);
    	
    	levelNummer = new JLabel ("(x/x)", SwingConstants.RIGHT);
    	//levelNummer.setOpaque(false);
    	levelNummer.setForeground(Color.white);
    	levelNummer.setFont(normalFont);
    	levelNummer.setBounds(330, 140, 60, 20);
    	
    	lZurueckButton = new JButton ("OKAY");
    	lZurueckButton.setBackground(Color.GRAY);
    	lZurueckButton.setForeground(Color.white);
    	lZurueckButton.setFont(normalFont);
    	lZurueckButton.setBounds(5, levelPanel.getHeight()-35, levelPanel.getWidth()-10, 30);
    	lZurueckButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getSoundboard().playClick();
				menuButtonPanel.setVisible(true);
				levelPanel.setVisible(false);
			}
    	});
    
    	levelPanel.add(vorherigesLevelButton);
    	levelPanel.add(naechstesLvelButton);
    	levelPanel.add(lZurueckButton);
    	levelPanel.add(levelTitel);
    	levelPanel.add(levelDescription);
    	levelPanel.add(levelImage);
    	levelPanel.add(levelNummer);
    	
    	
    	menuButtonPanel.add(startButton);
    	menuButtonPanel.add(levelButton);
    	menuButtonPanel.add(endButton);
    	menuButtonPanel.add(muteButton);
    	
    	contentPane = initialisiereContentPane();
    	
    	this.add(contentPane);
    	contentPane.add(levelPanel);
    	contentPane.add(menuButtonPanel);
    	
//    	this.getContentPane().add(contentPane);
    	updateLevelDescription();
	}
	
	/**
	 * sorgt f�r die Anzeige des Hintergrundbildes
	 *
	 * @author Henrike, Anne
	 */

	public JPanel initialisiereContentPane() {
		try {
			bodenImage = ImageIO.read(new File("Assets" + DIR_SEPERATOR + "Background" + DIR_SEPERATOR + "Titelbild.png"));
		} catch (IOException ex) {
			System.out.println("ERROR - Menue - Hintergrundbild wurde nicht gefunden");
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
		return newContentPane;
	}
	
	private void updateLevelDescription() {
		String highscoreText = "";
		if(currentLevel.getHighscore()==0) {
			highscoreText = "\n\nNoch kein Highscore aufgestellt!";
		} else {
			highscoreText = "\n\nHighscore gehalten von " + currentLevel.getHighscoreName() + " mit " + currentLevel.getHighscore() + " Punkten.";
		}
		
		levelTitel.setText(currentLevel.getName());
		levelDescription.setText(currentLevel.getDescription() + highscoreText);
		levelNummer.setText("(" + (currentLevelInt+1) + "/" + alleLevel.length + ")");
		
		try {
			BufferedImage levelImg = ImageIO.read(new File("Level" + DIR_SEPERATOR + currentLevel.getBmpFileName()));
			ImageIcon icon = new ImageIcon(GUIObjektHilfsfunktionen.resizeBuffredImage(levelImg, levelImage.getWidth(), levelImage.getHeight()));
			levelImage.setIcon(icon);
		} catch (IOException ex) {
			System.out.println("ERROR - Levelmen� - Level Bild wurde nicht gefunden");
		}
	}
	
	public static void schliesseFenster() {
		Controller.getMenu().dispose();
	}
	
	public boolean checkForFirstLaunch() {
		for (Level level : alleLevel) {
			if(level.getHighscore()>0) {
				return false;
			}
		}
		return true;
	}
}
