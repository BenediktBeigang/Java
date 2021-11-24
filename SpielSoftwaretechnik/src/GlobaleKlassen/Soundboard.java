package GlobaleKlassen;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Diese Klasse dient als Sound- und Musikplayer. Sie ermöglicht verschiedene Musik arten abzuspielen
 * (Hauptmenü-, oder Spielmusik). Zu dem ist es möglich unbegrenz viele Sounds parallel abzuspielen.
 * 
 * @author Garlef
 */

public class Soundboard {
	private int volumeSounds = 60;
	private static int volumeMusic = 70;
	private static float MINIMAL_GAIN = -30f;
	private boolean soundMuted = false;
	private boolean musicMuted = false;
	private String soundUrl ="...";
	private String musicUrl = "MenuMusic.wav";
	private String DIR_SEPERATOR = java.io.File.separator;
	private long currentPosition = 0;
	
	private Thread musicThread = new Thread(new MusicRunnable());
	private static Clip musicClip;
	
	/**
	 * Soundboard() ist der Konstruktor der Klasse. Hier wird geprüft ob die Startlautstärke
	 * stumm entspricht.
	 * 
	 * @author Garlef
	 */
	public Soundboard() {
		setSoundVolume(volumeSounds);
		setMusicVolume(volumeMusic);
		musicThread.start();
	}
	
	/**
	 * SoundRunnable ist ein Runnable, welches die erzeugung eines neuen soundspielenden
	 * Prozesses ermöglicht.
	 * 
	 * @author Garlef
	 */
	public class SoundRunnable implements Runnable {
		@Override
		public void run() {
			Clip clip;
			File file = new File("./Sounds" + DIR_SEPERATOR + soundUrl);
			
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(file));
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				float effectsGain = (100f - (float)volumeSounds) / 100f * MINIMAL_GAIN;
				gainControl.setValue(effectsGain);
				clip.start();
				Thread.sleep(clip.getMicrosecondLength()/1000);
				clip.stop();
				if(file.getPath().contains("NewWave")) {
					resumeMusic();
				}
				
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * MusicRunnable ist ein Runnable, welches die erzeugung eines globalen musikspielenden
	 * Prozesses ermöglicht, welcher 'anhaltbar' ist.
	 * 
	 * @author Garlef
	 */
	public class MusicRunnable implements Runnable {
		@Override
		public void run() {
			setUpMusic();
			musicClip.start();
			musicClip.setMicrosecondPosition(currentPosition);
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			musicClip.stop();
			}
		}
	
	public void setUpMusic() {
		File file = new File("./Sounds" + DIR_SEPERATOR + musicUrl);
		try {
			musicClip = AudioSystem.getClip();
			musicClip.open(AudioSystem.getAudioInputStream(file));
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
		float musicGain = (100f - (float)volumeMusic) / 100f * MINIMAL_GAIN;
		gainControl.setValue(musicGain);
	}
	
	/**
	 * setSoundVolume() Kann u.a. im Menü aufgerufen werden um die Lautstäre von Sounds anzupassen.
	 * 
	 * @param volume Die Soundlautstärke (Prozent) zw. 0 und 100
	 * @author Garlef
	 */
	public void setSoundVolume(int volume) {
		this.volumeSounds = volume;
		if(volume == 0) {
			soundMuted = true;
		}
		if(volume>0 && volume <= 100) {
			soundMuted = false;
			volumeSounds = volume;
		}
	}

	/**
	 * setMusicVolume() Kann u.a. im Menü aufgerufen werden um die Lautstäre der Musik anzupassen.
	 * 
	 * @param volume Die Musiklautstärke (Prozent) zw. 0 und 100
	 * @author Garlef
	 */
	public void setMusicVolume(int volume) {
		this.volumeMusic = volume;
		if(volume == 0) {
			musicMuted = true;
		}
		if(volume>0 && volume <= 100) {
			musicMuted = false;
			volumeMusic = volume;
		}
	}
	
	public int getSoundVolume() {
		return volumeSounds;
	}
	
	public int getMusicVolume() {
		return volumeMusic;
	}

	/**
	 * playSound() Öffnet und startet einen neuen soundspielenden Thread.
	 * 
	 * @author Garlef
	 */
	private void playSound() {
		if(!soundMuted) {
			Thread soundThread = new Thread(new SoundRunnable());
			soundThread.start();
		}
	}

	/**
	 * playMainMenuMusic() Startet den musikspielenden Thread mit Menümusik.
	 * 
	 * @author Garlef
	 */
	public void playMainMenuMusic() {
		currentPosition = 0;
		if(!musicMuted) {
			musicClip.stop();
			musicClip.close();
			musicUrl = "MenuMusic.wav";
			setUpMusic();
			musicClip.start();
		}
	}
	
	/**
	 * playGameMusic() Startet den musikspielenden Thread mit Levelmusik.
	 * 
	 * @author Garlef
	 */
	public void playGameMusic() {
		currentPosition = 0;
		if(!musicMuted) {
			musicClip.stop();
			musicClip.close();
			musicUrl = "Music.wav";
			setUpMusic();
			musicClip.start();
		}
	}
	
	/**
	 * stopMusic() Stopt den musikspielenden Thread.
	 * 
	 * @author Garlef
	 */
	public void stopMusic() {
		musicClip.stop();
	}
	
	public void pauseMusic() {
		musicClip.stop();
	}
	
	public void resumeMusic() {
		if(!musicMuted) {
			musicClip.start();
		}
	}

	/**
	 * playShot() Spielt einen Schusssound aus der Schusssoundliste und löscht ihn aus besagter Liste.
	 * 
	 * @author Garlef
	 */
	public void playShot() {
		playWhoosh();
		/*
		int index = random.nextInt(shotsFileNames.size());
		soundUrl = shotsFileNames.get(index);
		shotsFileNames.remove(index);
		if(shotsFileNames.isEmpty()) {
			fillShotsList();
		}
		System.out.println(soundUrl);
		playSound();
		*/
	}
	
	/**
	 * playWhoosh() Spielt einen Whoosh-Sound.
	 * 
	 * @author Garlef
	 */
	public void playWhoosh() {
		soundUrl = "Whoosh.wav";
		playSound();
	}

	/**
	 * playWallCollision() Spielt einen Wandkollisionssound.
	 * 
	 * @author Garlef
	 */
	public void playWallCollision() {
		soundUrl = "Collision.wav";
		playSound();
	}
	
	/**
	 * playGameOver() Spielt eine Abschlussmusik.
	 * 
	 * @author Garlef
	 */
	public void playGameOver() {
		soundUrl = "GameOver.wav";
		playSound();
	}
	

	/**
	 * playEnemyGotShot() Spielt einen Gegnertodessound.
	 * 
	 * @author Garlef
	 */
	public void playEnemyGotShot() {
		soundUrl = "EnemyDies.wav";
		playSound();
	}
	
	/**
	 * playDrinkCan() Spielt einen trink-Sound.
	 * 
	 * @author Garlef
	 */
	public void playDrinkCan() {
		soundUrl = "DrinkCan.wav";
		playSound();
	}
	
	/**
	 * playEats() Spielt einen ess-Sound.
	 * 
	 * @author Garlef
	 */
	public void playEats() {
		soundUrl = "Eats.wav";
		playSound();
	}
	
	/**
	 * playOof() Spielt einen Oof-Sound.
	 * 
	 * @author Garlef
	 */
	public void playOof() {
		soundUrl = "Oof.wav";
		playSound();
	}
	
	/**
	 * playSmash() Spielt einen Smash-Sound.
	 * 
	 * @author Garlef
	 */
	public void playSmash() {
		soundUrl = "Smash.wav";
		playSound();
	}
	
	/**
	 * playSmash() Spielt einen Smash-Sound.
	 * 
	 * @author Garlef
	 */
	public void playPop() {
		soundUrl = "Pop.wav";
		playSound();
	}
	
	public void playClick() {
		soundUrl = "Click.wav";
		playSound();
	}
	
	public void playPause() {
		soundUrl = "Pause.wav";
		playSound();
	}
	
	public void playUnpause() {
		soundUrl = "Unpause.wav";
		playSound();
	}
	
	public void playNewWave() {
		pauseMusic();
		soundUrl = "NewWave.wav";
		playSound();
	}
	
	public void playDrama() {
		soundUrl = "Drama.wav";
		playSound();
	}
	
	public void playCinematic() {
		soundUrl = "Cinematic.wav";
		playSound();
	}
	
	public void playScore() {
		soundUrl = "NewScore.wav";
		playSound();
	}
	
}
