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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GlobaleKlassen.Controller;
import GlobaleKlassen.State;

/**
 * Diese Klasse stellt das JFrame bereit, welches im Pausenmen� angezeigt wird.
 * Zugriff darauf erfolgt durch dr�cken von p
 * 
 * @author Henrike, Anne
 */
public class PauseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, menuButtonPanel;
	private JButton hauptmenuButton, endButton, weiterButton;
	private Font normalFont;
	private BufferedImage totImage;
	

	/**
	 * PauseGUI() ist der Konstruktor der Klasse. Alle Variablen werden initialisiert.
	 * Die JButton, welche im Pausenmen� angezeigt werden, und ihre Funktionen werden erstellt
	 * 
	 * @author Henrike, Anne
	 */
	public PauseGUI() {
		
		this.setBounds(780, 440, 400, 400);
		this.setBackground(Color.white);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.blue);
		contentPane.setBounds(300, 300, 400, 400);
		contentPane.setLayout(null);
		
		menuButtonPanel = new JPanel();
	    menuButtonPanel.setBackground(Color.black);
	    menuButtonPanel.setBounds(100, 100, 200, 200);
	    menuButtonPanel.setLayout(null);
	    
	    weiterButton = new JButton("WEITER");
		weiterButton.setBackground(Color.GRAY);
		weiterButton.setForeground(Color.white);
		weiterButton.setFont(normalFont);
		weiterButton.setBounds(5, 5, 190, 60);
		weiterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Controller.getSoundboard().playClick();
				contentPane.setVisible(false);
				State.setState(State.STATE.GAME);
				dispose();
			}
		});
		
		hauptmenuButton = new JButton("ZUM HAUPTMENU");
	    hauptmenuButton.setBounds(5, 70, 190, 60);
	    hauptmenuButton.setBackground(Color.GRAY);
	    hauptmenuButton.setForeground(Color.white);
	    hauptmenuButton.setFont(normalFont);
	    hauptmenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Controller.getSoundboard().playClick();
				contentPane.setVisible(false);
				contentPane.repaint();
		        State.setState(State.STATE.MENU);
		        Controller.startMenu();
		        Controller.getSpielframe().dispose();
		        Controller.getPause().dispose();
			}
	    });
	
		endButton = new JButton("SPIEL BEENDEN");
		endButton.setBackground(Color.GRAY);
		endButton.setForeground(Color.white);
		endButton.setFont(normalFont);
		endButton.setBounds(5, 135, 190, 60);
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Controller.getSoundboard().playClick();
				System.exit(0);
			}
		});
		
		menuButtonPanel.add(hauptmenuButton);
		menuButtonPanel.add(weiterButton);
    	menuButtonPanel.add(endButton);
    	
    	contentPane = initialisiereContentPane();
    	contentPane.add(menuButtonPanel);
    	this.add(contentPane);
    	this.setVisible(true);
		
	}
	
	/**
	 * sorgt f�r die Anzeige des Hintergrundbildes
	 * 
	 * @author Henrike, Anne
	 */
	public JPanel initialisiereContentPane() {
		try {
			totImage = ImageIO.read(new File("Assets/Background/PauseBild.png")); //BILD ÄNDERN
		} catch (IOException ex) {
			System.out.println("Bild für die Pause wurde nicht gefunden!");
		}
		
		JPanel newContentPane = new JPanel() {
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(totImage, 0, 0, this);
			}
		};
		
		newContentPane.setBounds(0, 0, 980, 1000);
		newContentPane.setLayout(null);
		newContentPane.setBackground(Color.white);
		newContentPane.grabFocus();
		newContentPane.setBackground(Color.gray);

		return newContentPane;
		
	}
}
