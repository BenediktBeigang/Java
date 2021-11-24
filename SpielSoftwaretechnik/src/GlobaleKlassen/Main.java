package GlobaleKlassen;

import java.awt.EventQueue;

import GlobaleKlassen.State.STATE;

public class Main {

	public static void main(String[] args) {
		
		if (Controller.getStatus().getState() == STATE.GAME) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
				
					try {
						Controller.startGame();	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (Controller.getStatus().getState() == STATE.MENU) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Controller.startMenu();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (Controller.getStatus().getState() == STATE.PAUSE ) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Controller.pausiereSpiel();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (Controller.getStatus().getState() == STATE.STARTUP ) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Controller.ladeAnimationen();
						Controller.startMenu();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
