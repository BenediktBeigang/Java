package GlobaleKlassen;

import java.util.ArrayList;

import javax.swing.Timer;

public class TimerManager {
	static ArrayList<Timer> timerListe = new ArrayList<Timer>();
	
	static void stopAllTimers() {
		for(Timer t : timerListe) {
			t.stop();
		}
		timerListe.clear();
	}

	public static ArrayList<Timer> getTimerListe() {
		return timerListe;
	}

	public static void addTimer(Timer timer) {
		TimerManager.timerListe.add(timer);
	}
	
}
