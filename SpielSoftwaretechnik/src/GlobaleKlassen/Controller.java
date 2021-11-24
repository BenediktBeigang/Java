package GlobaleKlassen;

import GlobaleKlassen.State.STATE;
import gui.*;
import spieldaten.*;

public class Controller {
	static private Spiel spieldaten;
	static private Frame spielframe;
	static private Animationen animationen;
	static private MenuGUI menu;
	static private PauseGUI pause;
	static private TodGUI tot;
	static private HighscoreGUI highscore;
	static private Soundboard soundboard;
	static private State status = new State();
	static private CinematicGUI cinematic;
	
	static private Level geladenesLevel;
	static private Level[] alleLevel = ReaderWriter.readAllLevelIn("Level");
	
	public static State getStatus() {
		return status;
	}

	public static void setStatus(State status) {
		Controller.status = status;
	}

	static public void startGame() {
		TimerManager.stopAllTimers();
		soundboard.stopMusic();
		spieldaten = new Spiel();
		spielframe = new Frame();
		Controller.getSoundboard().playGameMusic();
	}
	
	static public void startCinematic() {
		cinematic = new CinematicGUI();
	}
	
	static public void ladeAnimationen() {
		animationen = new Animationen();
	}
	
	// Hauptmenu
	static public void startMenu() {
		soundboard = new Soundboard();
		menu = new MenuGUI();
	}
	
	
	public static MenuGUI getMenu() {
		return menu;
	}

	public static void setMenu(MenuGUI menu) {
		Controller.menu = menu;
	}
	
	// Pausemenu
	static public void pausiereSpiel() {
		pause = new PauseGUI();
	}

	public static PauseGUI getPause() {
		return pause;
	}

	public static void setPause(PauseGUI pause) {
		Controller.pause = pause;
	}

	// Todesbildschirm
	public static void totMenu() {
		if(status.getState() == STATE.TOT) {
			if(geladenesLevel.getHighscore() < spieldaten.getScore() ) {
				
				highscore = new HighscoreGUI();
			}
			else {
				tot = new TodGUI();
			}
		}
	}
	
	public static TodGUI getTot() {
		return tot;
	}

	public static void setTot(TodGUI tot) {
		Controller.tot = tot;
	}

	// Highscoremenu	
	public static HighscoreGUI getHighscore() {
		return highscore;
	}

	public static void setHighscore(HighscoreGUI highscore) {
		Controller.highscore = highscore;
	}

	static public Spiel getSpieldaten() {
		return spieldaten;
	}
	static public Frame getSpielframe() {
		return spielframe;
	}
	static public Soundboard getSoundboard() {
		return soundboard;
	}
	
	static public Level getGeladenesLevel() {
		return geladenesLevel;
	}
	
	static public void setSpieldaten(Spiel sd) {
		spieldaten = sd;
	}
	static public void setSpielframe(Frame sf) {
		spielframe = sf;
	}
	
	static public void setGeladenesLevel(Level neuesLevel) {
		geladenesLevel = neuesLevel;
	}

	public static Level[] getAlleLevel() {
		return alleLevel;
	}

	public static void setAlleLevel(Level[] alleLevel) {
		Controller.alleLevel = alleLevel;
	}
}

//test