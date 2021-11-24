package GlobaleKlassen;

public class Level {
	
	private String name;
	private String description;
	private String fileName;
	private String bmpFileName;
	private String highscoreName;
	private int[][] map;
	private String[][] textureMap;
	private int speed;
	private int thougness;
	private int highscore;
	private String bgTextur;
	
	public Level(String name, String description, String fileName, String bmpFileName, int speed, int thougness, int[][] map, String[][] textureMap, int highscore, String highscoreName, String bgTextur) {
		this.name = name;
		this.description = description;
		this.fileName = fileName;
		this.bmpFileName = bmpFileName;
		this.speed = speed;
		this.thougness = thougness;
		this.map = map;
		this.textureMap = textureMap;
		this.highscore = highscore;
		this.highscoreName = highscoreName;
		this.bgTextur = bgTextur;
	}
	
	
	
	// Intelegent getter
	public String[] getAllLevelNames(Level[] allLevel) {
		String[] allLevelNames = new String[allLevel.length];
		for (int i=0; i<allLevel.length; i++) {
			allLevelNames[i] = allLevel[i].getName();
		}
		return allLevelNames;
	}
	public String getTextureNameAtXY(int x, int y) {
		return textureMap[y][x];
	}
	
	
	// Getter
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
		public String getFileName() {
			return fileName;
		}
		public String getBmpFileName() {
			return bmpFileName;
		}
		public int getSpeed() {
			return speed;
		}
		public int getThougness() {
			return thougness;
		}
		public int getHighscore() {
			return highscore;
		}
		public int[][] getMap(){
			return map;
		}
		public String[][] getTextureMap(){
			return textureMap;
		}
		public String getHighscoreName() {
			return highscoreName;
		}

	
	
	// Setter
	public void setNewHighscore(int highscore, String highscoreName) {
		this.highscore = highscore;
		this.highscoreName = highscoreName;
	}
	public void setNewHighscore(int highscore) {
		this.highscore = highscore;
	}
	public void setNewHighscore(String highscoreName) {
		this.highscoreName = highscoreName;
	}



	public String getBgTextur() {
		return bgTextur;
	}



	public void setBgTextur(String bgTextur) {
		this.bgTextur = bgTextur;
	}
	
}
