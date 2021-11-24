package gui;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import GlobaleKlassen.Controller;
import spieldaten.Animationen;

public class AnimationGUI extends JLabel {
	private static final long serialVersionUID = 1L;

	private int currentFrame = 0;
	private int animationType = 0;
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	private boolean hasTimer;
	private boolean hasLoop;
	private boolean hasRotation;
	private boolean hasMovment;
	private KollisionsObjekt obj;
	
	private BufferedImage img;
	
	public AnimationGUI (int posX, int posY, String type) {
		// Statische Animation
		hasTimer = false;
		hasMovment = false;
		hasRotation = false;
		determineType(type);
		updateBilder();
		int width = Animationen.getAnimationImg(animationType, 0).getWidth();
		int height = Animationen.getAnimationImg(animationType, 0).getHeight();
		int x = posX - this.getWidth()/2;
		int y = posY - this.getHeight()/2;
		this.setBounds(x, y, width, height);
	}
	
	public AnimationGUI (KollisionsObjekt obj, String type, int playTime, boolean hasLoop, boolean hasRotation) {
		// Animation, welche an einem Objekt heftet
		this.hasTimer = true;
		if(playTime==0) {
			hasTimer = false;
		}
		this.hasLoop = hasLoop;
		this.hasMovment = true;
		this.hasRotation =  hasRotation;
		this.obj = obj;
		determineType(type);
		this.setLocation(obj.getX()-this.getWidth()/2 + offsetX, obj.getY()-this.getHeight()/2 + offsetY);
		updateBilder();
	}
	
	public boolean updateBilder() {
		img = Animationen.getAnimationImg(animationType, currentFrame);
		this.setSize(img.getWidth(), img.getWidth());

		if(hasMovment) {
			this.setLocation(obj.getX()-this.getWidth()/2 + offsetX, obj.getY()-this.getHeight()/2 + offsetY);
			// Pr�fe ob das verfolgte objekt noch existiert
			if(!checkIfParrentObjektStillExists(obj)) {
				Controller.getSpielframe().addDeleteAnimationList(this);
			}
			if(obj==null) {
				Controller.getSpielframe().addDeleteAnimationList(this);
				System.out.println("Loesche gegner");
			}
		}
		
		if(hasRotation) {
			img = GUIObjektHilfsfunktionen.rotateImageByDegrees(img, obj.getDaten().getAktuelleRichtungGrad()*(-1));
		}
		
		ImageIcon icon = new ImageIcon(img);
		this.setIcon(icon);
		
		if(currentFrame<(Animationen.getAnimationLength(animationType))-1) {
			currentFrame++;
		} else {
			if(hasTimer || hasLoop) {
				currentFrame = 0;
			} else {
				Controller.getSpielframe().addDeleteAnimationList(this);
			}
		}
		
		return true;
	}
	
	private boolean checkIfParrentObjektStillExists(KollisionsObjekt obj) {
		if(obj.getClass() == SchussGUI.class) {
			for (SchussGUI schuss : Controller.getSpielframe().getZuLoeschendeSchuesse()) {
				if(schuss.equals(obj)) {
					return false;
				}
			}
		}
		if(obj.getClass() == GegnerGUI.class) {
			for (GegnerGUI gegner : Controller.getSpielframe().getZuLoeschendeGegner()) {
				if(gegner.equals(obj)) {
					return false;
				}
			}
		}
		if(obj.getClass() == ItemGUI.class) {
			if(Controller.getSpielframe().getItem() == null) {
				return false;
			}
		}
		return true;
	}
	
	private void determineType(String type) {
		switch(type) {
		  case "explosion_large":
			  	animationType = 0;
			  	offsetX = 0;
				offsetY = 0;
			  	break;
		  case "small_cracks":
				animationType = 1;
				offsetX = 0;
				offsetY = 0;
			    break;
		  case "pickup":
				animationType = 2;
				offsetX = 13;
				offsetY = 8;
			    break;
		  case "vapor":
				animationType = 3;
				offsetX = 13;
				offsetY = 8;
			    break;
		  case "vibration":
				animationType = 4;
				offsetX = 13;
				offsetY = 8;
			    break;
		  case "energy":
				animationType = 5;
				offsetX = 13;
				offsetY = 8;
			    break;
		  case "sling":
				animationType = 6;
				offsetX = 20;
				offsetY = 20;
			    break;
		  case "cartoon_hit":
			  	animationType = 7;
			  	offsetX = 0;
				offsetY = 0;
			  	break;
		  case "cartoon_hit_sw":
			  	animationType = 8;
			  	offsetX = 0;
				offsetY = 0;
			  	break;
		  case "walking":
			  	animationType = 9;
			  	offsetX = 11;
				offsetY = 17;
			  	break;
		  case "flash":
			  	animationType = 10;
			  	offsetX = 30;
				offsetY = 10;
			  	break;
		  case "health":
			  	animationType = 11;
			  	offsetX = 30;
				offsetY = 10;
			  	break;
		  case "100pt":
			  	animationType = 12;
			  	offsetX = 45;
				offsetY = -6;
			  	break;
		  case "500pt":
			  	animationType = 13;
			  	offsetX = 45;
				offsetY = -6;
			  	break;
		  case "10pt":
			  	animationType = 14;
			  	offsetX = 45;
				offsetY = -6;
			  	break;
		  case "player_hit":
			  	animationType = 15;
			  	offsetX = 20;
				offsetY = 20;
			  	break;
		  default:
			  	System.out.println("ERROR - Animationstyp " + type + " nicht existent");
			  	break;
		
		}
	}

}
