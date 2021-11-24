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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GlobaleKlassen.Controller;
import GlobaleKlassen.ReaderWriter;
import GlobaleKlassen.State;

/**
 * Diese Klasse stellt das JFrame bereit, welches im Endmen� angezeigt wird,
 * wenn ein Highscore gebrochen wurde
 * 
 * @author Henrike, Anne
 */
public class HighscoreGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, menuButtonPanel;
	private JTextField spieler;
	private JButton hauptmenuButton, endButton;
	private Font normalFont;
	private BufferedImage totImage;

	public HighscoreGUI() {
		Controller.getSoundboard().playScore();
		this.setBounds(780, 440, 500, 500);
		this.setBackground(Color.white);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.blue);
		contentPane.setBounds(this.getWidth(), this.getHeight(), 500, 500);
		contentPane.setLayout(null);
		
		menuButtonPanel = new JPanel();
	    menuButtonPanel.setBackground(Color.black);
	    menuButtonPanel.setBounds(150, 210, 200, 220);
	    menuButtonPanel.setLayout(null);
			
		contentPane = initialisiereContentPane();

		// Name für Spieler - Highscore
		spieler = new JTextField();
		
		
		spieler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spieler.getText();
				Controller.getGeladenesLevel().setNewHighscore(Controller.getSpieldaten().getScore(), spieler.getText());
			}
		});
		spieler.setBounds(5, 30, 190, 20);
		
		menuButtonPanel.add(spieler);

		JLabel name = new JLabel("Gib deinen Namen ein:");
		name.setBounds(5, 5, 190, 20);
		name.setFont(normalFont);
		name.setForeground(Color.white);
		menuButtonPanel.add(name);

		hauptmenuButton = new JButton("ZUM HAUPTMENU");
		hauptmenuButton.setBounds(5, 85, 190, 60);
		hauptmenuButton.setBackground(Color.GRAY);
		hauptmenuButton.setForeground(Color.white);
		hauptmenuButton.setFont(normalFont);
		hauptmenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getSpielframe().dispose();
				Controller.getSoundboard().playClick();
				Controller.getHighscore().dispose();
				
				Controller.getGeladenesLevel().setNewHighscore(Controller.getSpieldaten().getScore());
				Controller.getGeladenesLevel().setNewHighscore(spieler.getText());
				
				String path = "./Level";
				ReaderWriter.rewriteLevelTxt(path, Controller.getGeladenesLevel());
				
				System.out.println("Highscore " + Controller.getGeladenesLevel().getHighscore());
				
				contentPane.setVisible(false);
				State.setState(State.STATE.MENU);
				Controller.startMenu();
				
				Controller.getSpieldaten().getSpieler().setLeben(10);
			}
		});

		endButton = new JButton("SPIEL BEENDEN");
		endButton.setBackground(Color.GRAY);
		endButton.setForeground(Color.white);
		endButton.setFont(normalFont);
		endButton.setBounds(5, 150, 190, 60);
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getSoundboard().playClick();
				System.exit(0);
			}
		});

		menuButtonPanel.add(hauptmenuButton);
		menuButtonPanel.add(endButton);

//		contentPane = initialisiereContentPane();
		
		contentPane.add(menuButtonPanel);
		this.getContentPane().add(contentPane);
	}

	public JPanel initialisiereContentPane() {
		try {
			totImage = ImageIO.read(new File("Assets/Background/HscGUI.png")); // BILD ÄNDERN
		} catch (IOException ex) {
			System.out.println("Hintergrundbild wurde nicht gefunden");
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
