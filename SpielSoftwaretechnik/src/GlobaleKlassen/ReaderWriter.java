package GlobaleKlassen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ReaderWriter {
	
	static private String DIR_SEPERATOR = java.io.File.separator;
	static final private int RESOLUTION = 48;
	static final private int TXT_LINES = 16;
	
	static public Level[] readAllLevelIn(String path) { // Okay
		String[] txtFileNames = readTxtFileNames(path);
		if(txtFileNames.length==0) {
			System.out.println("0 level delivered");
			return new Level[0];
		}
		Level[] levelArray = new Level[txtFileNames.length];
		
		for(int i=0; i<levelArray.length; i++) {
			levelArray[i] = readLevelIn(path + DIR_SEPERATOR + txtFileNames[i]);
		}
		
		System.out.println("\n" + levelArray.length + " level delivered\n");
		return levelArray;
	}
	
	static private Level readLevelIn(String path) { // Okay
		String name = "";
		String description = "";
		String txtFileName = "";
		String bmpFileName = "";
		String highscoreName = "";
		String bgTextur = "";
		int[][] map;
		String[][] textureMap;
		int speed = 0;
		int thougness = 0;
		int highscore = 0;
		try {
			File file = new File(path);
			System.out.println("Reading in " + file.getPath());
			txtFileName = file.getName();
		    Scanner myReader = new Scanner(file);
		    int currentLine = 0;
		    while (myReader.hasNextLine()) {
		    	currentLine++;
		    	String lineString = myReader.nextLine();
		    	switch(currentLine) {
		    	case 2:
		    		name = lineString;
		    		break;
		    	case 4:
		    		description = lineString;
		    		break;
		    	case 6:
		    		thougness = Integer.parseInt(lineString);
		    		break;
		    	case 8:
		    		speed = Integer.parseInt(lineString);
		    		break;
		    	case 10:
		    		bmpFileName = lineString;
		    		break;
		    	case 12:
		    		highscore = Integer.parseInt(lineString);
		    		break;
		    	case 14:
		    		highscoreName = lineString;
		    		break;
		    	case 16:
		    		bgTextur = lineString;
		    	default:
		    		//...
		    	}
		    }
		      myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		map = readBitmapIn("./Level" + DIR_SEPERATOR + bmpFileName);
		
		String texture = "wand";
		textureMap = makeTextureMap(texture, map);
		
		return new Level(name, description, txtFileName, bmpFileName, speed, thougness, map, textureMap, highscore, highscoreName, bgTextur);
	}
	
 	static private String[] readTxtFileNames(String path) { // Okay
		boolean ceepSuffix = true;
		LinkedList<String> fileList = new LinkedList<String>();
		
		File directoryPath = new File(path);
		System.out.println("Loking for valid txt files in " + directoryPath.getPath());
		
		File[] files = directoryPath.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		for (File file : files) {
			if(verifyLevelTxt(file)) {
				fileList.add(file.getName());
			}
		}
		
		if (fileList.size()==0) {
			System.out.println("NO valid files found\n");
			return new String[0];
		}
		
		Object[] objectAarray = fileList.toArray();
	      int length = objectAarray.length;;
	      String [] stringArray = new String[length];
	      for(int i = 0; i < length; i++) {
	    	  if (ceepSuffix) {
	    		  stringArray[i] = (String) objectAarray[i];
	    	  } else {
	    		  stringArray[i] = ((String) objectAarray[i]).split(".txt")[0];
	    	  }
	      }
	    System.out.println(stringArray.length + " valid file(s) found\n");
		return stringArray;
	}
	
	static private boolean verifyLevelTxt(File file) { // Okay
		int lines = 0;
		String imgFileName = "";
		try {
		      Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		    	  String lineString = myReader.nextLine();
		    	  if(lines==9) {
		    		  imgFileName = lineString;
		    	  }
		    	  lines++;
		      }
		      myReader.close();
		} catch (FileNotFoundException e) {
		      e.printStackTrace();
		}
		if (lines == TXT_LINES) {
			File imgFile = new File(file.getParent() + DIR_SEPERATOR + imgFileName);
  		  	if(!verifyLevelBmp(imgFile)) {
  		  		return false;
  		  	}
			return true;
		} else {
			System.out.println("ERROR - has not " + TXT_LINES + " textlines " + file.getPath());
		}
		
		return false;
	}
	
	static private boolean verifyLevelBmp(File file) { // Okay
		if(!file.exists()) {
			System.out.println("ERROR - Bitmap path NOT found " + file.getPath());
			return false;
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Prüfe ob Bitmap auflösung 24*24 besitzt
		if(img.getHeight() == RESOLUTION && img.getWidth() == RESOLUTION) {
			return true;
		}
		System.out.println("ERROR - Bitmap has unvalid resolution " + file.getPath());
		return false;
	}
	
	static private int[][] readBitmapIn(String path) { // Okay
		File importImage = new File(path);
		System.out.println("Reading in " + importImage.getPath());
		//.asdadasdada
		if(verifyLevelBmp(importImage)) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(importImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Lese Bitmap ein
			int[][] map = new int[img.getHeight()][img.getWidth()];
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					Color c = new Color(img.getRGB(x, y));
					map[y][x] = (c.getRed()+c.getGreen()+c.getBlue())/3;
				}
			}
			
			//printInt2DArray(map);
			return map;
		}
		return null;
	}

	static public boolean rewriteLevelTxt(String path, Level level) { // Okay
		String txtPath = path + DIR_SEPERATOR + level.getFileName();
		File file = new File(txtPath);
		System.out.println("\nINFO - Speichere Level");
		System.out.println("INFO - Lösche " + file.getPath());
		System.out.println("path: " + path);
		System.out.println("name: " + level.getFileName());
		if(file.exists()) {
			file.delete();
			System.out.println("INFO - Loechen erfolgreich");
		}
		File newFile = new File(txtPath);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
			for (int i=0; i<14; i++) {
				switch(i) {
				case 0:
					writer.write("Name:");
					break;
				case 1:
					writer.write(level.getName());
					break;
				case 2:
					writer.write("Beschreibung:");
					break;
				case 3:
					writer.write(level.getDescription());
					break;
				case 4:
					writer.write("Schwierigkeit:");
					break;
				case 5:
					writer.write(Integer.toString(level.getThougness()));
					break;
				case 6:
					writer.write("Schnelligkeit:");
					break;
				case 7:
					writer.write(Integer.toString(level.getSpeed()));
					break;
				case 8:
					writer.write("Level-Bitmap Name:");
					break;
				case 9:
					writer.write(level.getBmpFileName());
					break;
				case 10:
					writer.write("Highscore:");
					break;
				case 11:
					writer.write(Integer.toString(level.getHighscore()));
					break;
				case 12:
					writer.write("Rekordhalter:");
					break;
				case 13:
					writer.write(level.getHighscoreName());
					break;
				case 14:
					writer.write("BG-Textur:");
					break;
				case 15:
					writer.write(level.getBgTextur());
					break;
				default:
					//...
					break;
				}
				if(i!=13) {
					writer.newLine();
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	static public boolean drawBitmap(String path, int[][] map) {
		System.out.println("Draw new Bitmap " + (new File(path).getPath()));
		if(map.length!=24 || map[0].length!=24) {
			return false;
		}
		BufferedImage img = new BufferedImage( 24, 24, BufferedImage.TYPE_INT_RGB);
		for (int h = 0; h < img.getHeight(); h++) {
			for (int w = 0; w < img.getWidth(); w++) {
				// RGB zu int-color
				int c_int = (0 & 0xff) << 24 | (map[h][w] & 0xff) << 16 | (map[h][w] & 0xff) << 8 | (map[h][w] & 0xff);
				img.setRGB(w, h, c_int);
			}
		}
		try {
			ImageIO.write(img, "bmp", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	static private String[][] makeTextureMap(String texture, int[][] map){
		String[][] textureMap = new String[map.length][map[0].length];
		
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				if(hasWall(x, y, map)) {
					String newTexture = texture + "_" + detectBorderPiece(x, y, map) + ".png";
					/*
					File file = new File("./Assets\\" + texture);
					if(!file.exists()) {
						System.out.println("ERROR - Datei: " + newTexture + " nicht gefunden");
						newTexture = "error.png";
					}
					file = new File("./Assets\\" + texture);
					System.out.println("Ersatzfiele existiert: " + file.exists());
					*/
					textureMap[y][x] = newTexture;
				}
			}
		}
					
		return textureMap;
	}
	
 	static public int detectBorderPiece(int x, int y, int[][] map) {
		int borderType = 0;
		int maxX=map[0].length-1;
		int maxY=map.length-1;
		
		if(x<0||y<0||x>maxX||y>maxY) {
			System.out.println("ERROR - Out of bounds in edge detection");
			return 0;
		}
		
		// Checke Flächen
		if (y>0) { // Checke Fläche oben
			if(!hasWall(x, y-1, map)) {
				borderType = borderType + 2;
				//System.out.println("oben");
			}
		}
		if (x<maxX) { // Checke Fläche rechts
			if(!hasWall(x+1, y, map)) {
				borderType = borderType + 8;
				//System.out.println("rechts");
			}
		}
		if (y<maxY) { // Checke Fläche unten
			if(!hasWall(x, y+1, map)) {
				borderType = borderType + 32;
				//System.out.println("unten");
			}
		}
		if (x>0) { // Checke Fläche links
			if(!hasWall(x-1, y, map)) {
				borderType = borderType + 128;
				//System.out.println("links");
			}
		}
		
		
		// Checke kleine Ecken
		if (x>0 && y>0) { // Checke Ecke oben-links
			if(!hasWall(x-1, y-1, map) && hasWall(x-1, y, map) && hasWall(x, y-1, map)) {
				borderType = borderType + 1;
				//System.out.println("oben-links");
			}
		}
		if (x<maxX && y>0) { // Checke Ecke oben-rechts
			if(!hasWall(x+1, y-1, map) && hasWall(x, y-1, map) && hasWall(x+1, y, map)) {
				borderType = borderType + 4;
				//System.out.println("oben-rechts");
			}
		}
		if (x<maxX && y<maxY) { // Checke Ecke unten-rechts
			if(!hasWall(x+1, y+1, map) && hasWall(x+1, y, map) && hasWall(x, y+1, map)) {
				borderType = borderType + 16;
				//System.out.println("unten-rechts");
			}
		}
		if (x>0 && y<maxY) { // Checke Ecke unten-links
			if(!hasWall(x-1, y+1, map) && hasWall(x, y+1, map) && hasWall(x-1, y, map)) {
				borderType = borderType + 64;
				//System.out.println("unten-links");
			}
		}

		// borderType zw. 0 und 256
		return borderType;
	}
 	
 	public static BufferedImage readBufferedImageIn(String path) throws IOException {
 		BufferedImage img = ImageIO.read(new File(path));
 		return img;
 	}
 	
 	public static BufferedImage readBufferedImageIn(File file) throws IOException {
 		return readBufferedImageIn(file.getAbsolutePath());
 	}
	
	public static boolean hasWall(int x, int y, int[][] map) {
		if(map[y][x]>=30) {
			return true;
		}
		return false;
	}

	static public void printInt2DArray(int[][] in) {
		System.out.print("\n");
		for (int i=0; i<in.length; i++) {
			for (int j=0; j<in[0].length; j++) {
				System.out.printf("%03d ", in[i][j]);
			}
			System.out.print("\n");
		}
	}
}
