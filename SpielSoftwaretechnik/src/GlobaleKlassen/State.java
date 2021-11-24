package GlobaleKlassen;

public class State {
	
	public enum STATE {
		MENU,
		STARTUP,
		LOADING,
		GAME,
		PAUSE,
		TOT
	};
	
	static STATE state;
	
	public State() {
		
		state = STATE.STARTUP;
	}
	
	public STATE getState() {
		return state;
	}

	public static void setState(STATE neuState) {
		if(neuState == STATE.PAUSE) {
			Controller.getSoundboard().playPause();
			Controller.getSoundboard().pauseMusic();
		}
		if(neuState == STATE.GAME && state == STATE.PAUSE) {
			Controller.getSoundboard().playUnpause();
			Controller.getSoundboard().resumeMusic();
		}
		if(neuState == STATE.TOT) {
			Controller.getSoundboard().playGameOver();
			Controller.getSoundboard().pauseMusic();
		}
		state = neuState;
	}
}

