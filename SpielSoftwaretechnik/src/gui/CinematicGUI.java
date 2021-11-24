package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import GlobaleKlassen.Controller;
import spieldaten.Animationen;

public class CinematicGUI extends JFrame{
	private static final long serialVersionUID = 1L;


	private static int currentFrame = 1;
	private static int frameLegth = 459;
	
	private JLabel animationsLabel;
	
	public CinematicGUI() {	
		this.setBounds(507, 131, 946, 946);
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
		animationsLabel = new JLabel("FILE NOT FOUND");
		animationsLabel.setBackground(Color.gray);
		animationsLabel.setOpaque(true);
		animationsLabel.setBounds(10, 10, 200, 200);
		animationsLabel.setLayout(null);
		this.add(animationsLabel);
		
		playVideo();
	}
	
	public void playVideo() { 
		BufferedImage[] imgSeq = Animationen.getCinematic();
		Controller.getSoundboard().playCinematic();
        new Timer(33, (ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(currentFrame<frameLegth) {
					ImageIcon icon = new ImageIcon(imgSeq[currentFrame]);
					animationsLabel.setIcon(icon);
					
					currentFrame++;
				} else {
					stopCinematic();
				}
			}
		}).start();
	}
	
	public void stopCinematic() {
		this.dispose();
		Controller.getSoundboard().resumeMusic();
	}
	
	public static int getFrameLength() {
		return frameLegth;
	}
	

}
