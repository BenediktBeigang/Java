package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GUIObjektHilfsfunktionen {
	
	
	public static int[] getXYMinMax(KollisionsObjekt obj) {
		/*
		 * 0: left x | 1: right x | 2: up y | 3: down y
		 */
		int[] xyMinMax = new int[4];
		xyMinMax[0] = obj.getX();
		xyMinMax[1] = obj.getX() + obj.getWidth();
		xyMinMax[2] = obj.getY();
		xyMinMax[3] = obj.getY() + obj.getHeight();
		return xyMinMax;
	}
	
	public static BufferedImage getImageMitRichtung(KollisionsObjekt obj, BufferedImage img) {
		double rotation = 360 - obj.getDaten().getAktuelleRichtungGrad();
		return rotateImageByDegrees(img, rotation);
	}
	
	public static BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }
	
	public static BufferedImage resizeBuffredImage(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
}
