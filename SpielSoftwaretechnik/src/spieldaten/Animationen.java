package spieldaten;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import GlobaleKlassen.State;
import GlobaleKlassen.State.STATE;
import gui.CinematicGUI;

public class Animationen {
	
	static private String DIR_SEPERATOR = java.io.File.separator;
	
	private static BufferedImage[][] animationen;

	
	private static int zahlAnimationen = 16;
	private static int imageCounter = 0;
	private static int allFramesNumber = 0;
	
	
	public Animationen() {
		animationen = loadAnimations();
		System.out.println("INFO - Laden erfolgreich\n");
		State.setState(STATE.MENU);
		
	}
	
	public static BufferedImage[] loadCinematic() {
		int frameLegth = CinematicGUI.getFrameLength();
		System.out.println("INFO - Loading");
		
		imageCounter = 0;
		
		BufferedImage[] imgSeq = new BufferedImage[frameLegth];
		for(int i=0; i<frameLegth; i++) {
			imageCounter++;
			try {
				String path = "./Assets" + DIR_SEPERATOR + "Cinematic" + DIR_SEPERATOR + "Cinematic (" + (i+1) + ").jpg";
				imgSeq[i] = ImageIO.read(new File(path)); //BILD ÄNDERN
			} catch (IOException ex) {
				System.out.println("ERROR - Cinematic frame " + i + " not found");
			}
			if(imageCounter%20==0) {
				System.out.println("INFO - Lade cinematic " + imageCounter*100/frameLegth + "%");
			}
		}
		System.out.println("INFO - Laden erfolgreich\n");
		return imgSeq;
	}
	
	public static BufferedImage[][] loadAnimations() {
		BufferedImage[][] imgSeqArray = new BufferedImage[zahlAnimationen][];
		
		String path = "./Assets" + DIR_SEPERATOR + "Cinematic";
		File folder = new File(path);
		allFramesNumber = folder.list().length;
		
		int frameLength = 0;
		String textureName = "";
		for(int i=0; i<zahlAnimationen; i++) {
			switch(i) {
			  case 0:
				  	frameLength = 49;
				  	textureName = "Test";
				  	break;
			  case 1:
					frameLength = 9;
					textureName = "SmallCrack";
				    break;
			  case 2:
					frameLength = 30;
					textureName = "Pickup";
				    break;
			  case 3:
					frameLength = 60;
					textureName = "Vapor";
				    break;
			  case 4:
					frameLength = 25;
					textureName = "Vibration";
				    break;
			  case 5:
					frameLength = 90;
					textureName = "Energy";
				    break;
			  case 6:
					frameLength = 15;
					textureName = "Sling";
				    break;
			  case 7:
				  	frameLength = 31;
				  	textureName = "CartoonHit";
				  	break;
			  case 8:
				  	frameLength = 30;
				  	textureName = "CartoonHitSw";
				  	break;
			  case 9:
				  	frameLength = 20;
				  	textureName = "Walking";
				  	break;
			  case 10:
				  	frameLength = 41;
				  	textureName = "Flash";
				  	break;
			  case 11:
				  	frameLength = 60;
				  	textureName = "Health";
				  	break;
			  case 12:
				  	frameLength = 26;
				  	textureName = "100PT";
				  	break;
			  case 13:
				  	frameLength = 26;
				  	textureName = "500PT";
				  	break;
			  case 14:
				  	frameLength = 26;
				  	textureName = "10PT";
				  	break;
			  case 15:
				  	frameLength = 40;
				  	textureName = "PlayerHit";
				  	break;
			}
			imgSeqArray[i] = loadAnimation(frameLength, textureName);
		}
		return imgSeqArray;
	}
	
	private static BufferedImage[] loadAnimation(int length, String textureName) {
		State.setState(STATE.LOADING);
		BufferedImage[] imgSeq = new BufferedImage[length];
		
		for(int currentFrame=0; currentFrame<length; currentFrame++) {
			imageCounter++;
			File file = new File("./AnimationAssets" + DIR_SEPERATOR + textureName + " (" + (currentFrame+1) + ").png");
			BufferedImage img;
			if (file.exists()) {
				try {
					img = ImageIO.read(file);
					imgSeq[currentFrame] = img;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("ERROR - Animation file not found: " + file.getPath());
			}
			if(imageCounter%20==0) {
				System.out.println("INFO - Lade animationen " + imageCounter*100/allFramesNumber + "%");
			}
		}
		State.setState(STATE.MENU);
		return imgSeq;
	}
	
	
	public static BufferedImage[] getCinematic() {
		return loadCinematic();
	}
	
	public static int getAnimationLength(int type) {
		return animationen[type].length;
	}
	
	public static BufferedImage[] getAnimation(int type) {
		return animationen[type];
	}
	
	public static BufferedImage getAnimationImg(int x, int y) {
		if(y>=animationen[x].length) {
			System.out.println("ERROR - Index �berschreitung bei Animationswiedergabe");
			//return animationen[x][y-1];
		}
		if(x>=animationen.length) {
			System.out.println("ERROR - Animation nicht exsistent");
			//return animationen[x-1][y];
			}
		return animationen[x][y];
	}

}
