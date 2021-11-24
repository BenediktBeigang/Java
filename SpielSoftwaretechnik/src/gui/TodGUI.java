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
 * Diese Klasse stellt das JFrame bereit, welches im Endmen� angezeigt wird,
 * wenn kein Highscore gebrochen wurde.
 * 
 * @author Henrike, Anne
 */
public class TodGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, menuButtonPanel;
	private JButton hauptmenuButton, endButton;
	private Font normalFont;
	private BufferedImage totImage;
	
	
	public TodGUI() {
		
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
	    menuButtonPanel.setBounds(150, 275, 200, 135);
	    menuButtonPanel.setLayout(null);
	    
	    hauptmenuButton = new JButton("ZUM HAUPTMENU");
	    hauptmenuButton.setBounds(5, 5, 190, 60);
	    hauptmenuButton.setBackground(Color.GRAY);
	    hauptmenuButton.setForeground(Color.white);
	    hauptmenuButton.setFont(normalFont);
	    hauptmenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getSpielframe().dispose();
				Controller.getTot().dispose();
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
		endButton.setBounds(5, 70, 190, 60);
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getSpielframe().dispose();
				System.exit(0);
			}
		});
		
		menuButtonPanel.add(hauptmenuButton);
    	menuButtonPanel.add(endButton);
    	
    	contentPane = initialisiereContentPane();
    	
    	this.add(contentPane);
    	contentPane.add(menuButtonPanel);
		
	}
	
	public JPanel initialisiereContentPane() {
		try {
			totImage = ImageIO.read(new File("Assets/Background/TodGUI.png")); //BILD ÄNDERN
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

