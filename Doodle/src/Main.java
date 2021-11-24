import java.awt.EventQueue;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @brief Diese Klasse organisiert den Start des Programms sowie den Start neuer
 *        Fenster.
 * @author Benedikt Beigang
 *
 */
public class Main {
	static private TerminInputGUI inputFrame;
	@SuppressWarnings("unused")
	static private TableGUI tableFrame;

	/**
	 * @brief Main-Funktion welche das Programm startet
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Terminverwaltung.setTermine(Terminverwaltung.terminTxtToArrayList());
		} catch (Exception e) {
			Terminverwaltung.setTermine(new ArrayList<LocalDateTime>());
		}
		startWindow("GUI-1");
	}

	/**
	 * @brief Startet das Fenster
	 * @param gui Dient der Bestimmung welches Fenster geöffnet werden soll (GUI-1
	 *            -> Termineingabe | GUI-2 -> Personeneingabe bzw. Hauptfenster)
	 */
	static public void startWindow(String gui) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (gui.equals("GUI-1")) {
						inputFrame = new TerminInputGUI();
					} else if (gui.equals("GUI-2")) {
						tableFrame = new TableGUI();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @brief startet bzw. setzt das Fenster mit der Termineingabe auf sichtbar
	 */
	static public void restartInputFrame() {
		inputFrame.getFrame().setVisible(true);
	}

}
