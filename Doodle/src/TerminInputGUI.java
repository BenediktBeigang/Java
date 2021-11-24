import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;

/**
 * @brief Diese Klasse stellt eine interaktive Oberfläche bereit mit der Termine
 *        eingetragen werden können. Die Klasse stellt dabei sicher dass diese
 *        Termine auch existieren.
 * @author Benedikt Beigang
 */
public class TerminInputGUI {
	private JFrame inputframe;
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private JTextField[] textfields;

	private Color green;
	private Color red;

	// CONSTRUCTOR
	public TerminInputGUI() {
		textfields = new JTextField[4];
		green = Color.decode("#91d982");
		red = Color.decode("#ff5454");
		newWindow();
	}

	// PAINT-FUNCTIONS
	// Diese Funktionen dienen dem Anzeigen aller Components im Fenster

	/**
	 * @brief Erstellt das Fenster zur Eingabe der Termine
	 */
	public void newWindow() {
		inputframe = new JFrame();
		inputframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputframe.setBounds(600, 300, 320, 170);
		inputframe.setResizable(false);
		contentPane = new JPanel();
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		paintUberschrift();
		paintDateInput();
		paintComboBox();
		paintButtons();

		inputframe.setVisible(true);
		inputframe.getContentPane().add(contentPane);
		comboBox.grabFocus();
	}

	/**
	 * @brief Erstellt und befüllt die ComboBox zur Anzeige der Eingetragenen
	 *        Termine
	 */
	public void paintComboBox() {
		comboBox = new JComboBox<String>();
		comboBox.setToolTipText("Zeigt alle bisher eingetragenen Termine an");
		comboBox.setEditable(false);
		comboBox.setBounds(0, 80, 230, 50);
		comboBox.setBackground(Color.GRAY);
		comboBox.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		comboBox.setFont(new Font("Arial Black", Font.BOLD, 12));
		for (LocalDateTime date : Terminverwaltung.getTermine()) {
			comboBox.addItem(Terminverwaltung.terminoutput(date));
		}
		contentPane.add(comboBox);
	}

	/**
	 * @brief Erstellt die zur Eingabe der Termine nötigen Components
	 */
	public void paintDateInput() {
		JPanel termin = new JPanel();
		termin.setBounds(0, 50, 275, 30);
		termin.setLayout(null);

		JTextField day = new JTextField("dd");
		JTextField month = new JTextField("mm");
		JTextField year = new JTextField("yyyy");
		JTextField time = new JTextField("hh:mm");
		
		day.setToolTipText("Tag im Monat eintragen");
		month.setToolTipText("Monat eintragen");
		year.setToolTipText("Jahr eintragen");
		time.setToolTipText("Uhrzeit eintragen");

		day.setBounds(0, 0, 50, 30);
		month.setBounds(50, 0, 50, 30);
		year.setBounds(100, 0, 75, 30);
		time.setBounds(175, 0, 100, 30);

		day.setName("dd");
		month.setName("mm");
		year.setName("yyyy");
		time.setName("hh:mm");

		textfields[0] = day;
		textfields[1] = month;
		textfields[2] = year;
		textfields[3] = time;

		for (JTextField tf : textfields) {
			tf.setBackground(Color.LIGHT_GRAY);
			tf.setForeground(Color.DARK_GRAY);
			tf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			tf.setFont(new Font("Arial Black", Font.BOLD, 18));
			tf.setHorizontalAlignment(JTextField.CENTER);
			tf.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					tf.setBackground(Color.LIGHT_GRAY);
					tf.setForeground(Color.DARK_GRAY);
					tf.setText("");
				}

				public void focusLost(FocusEvent e) {
					isInputCorrect(tf);
					if (tf.getText().equals("")) {
						tf.setText(tf.getName());
					}
				}

			});

			termin.add(tf);
		}
		contentPane.add(termin);
	}

	/**
	 * @brief Erstellt die Überschrift des Fensters
	 */
	public void paintUberschrift() {
		JTextField uberschrift = new JTextField("Termine eintragen:");
		uberschrift.setEditable(false);
		uberschrift.setBounds(0, 0, 305, 50);
		uberschrift.setHorizontalAlignment(JTextField.CENTER);
		uberschrift.setBackground(Color.DARK_GRAY);
		uberschrift.setForeground(Color.LIGHT_GRAY);
		uberschrift.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		uberschrift.setFont(new Font("Arial Black", Font.BOLD, 20));
		uberschrift.setFocusable(false);
		contentPane.add(uberschrift);
	}

	/**
	 * @brief Erstellt die Buttons des Fensters, 1) dazu zählt das Hinzufügen eines
	 *        Termins 2) das Bestätigen aller Termin-Eingaben 3) das entfernen eines
	 *        eingetragenen Termins
	 */
	public void paintButtons() {
		JButton[] buttons = new JButton[3];
		// Termin Hinzufügen
		JButton button = new JButton("+");
		button.setToolTipText("Fügt Termin hinzu");
		button.setBounds(275, 50, 30, 30);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetColor();
				comboBox.requestFocus();
				try {
					addTermin();
				} catch (Exception exc) {
					markIncorrectDates();
					JOptionPane.showMessageDialog(inputframe, "Dieses Datum existiert nicht");
				}
			}
		});
		buttons[0] = button;
		// Alle Termine eingetragen
		JButton button2 = new JButton("\u2714");
		button2.setToolTipText("Bestätigt Auswahl von Terminen, öffnet den Konferenzplaner");
		button2.setBounds(255, 80, 50, 50);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!Terminverwaltung.getTermine().isEmpty()) {
						Terminverwaltung.termineToTxt();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				closeWindow();
			}
		});
		buttons[1] = button2;
		// Termin Löschen
		JButton button3 = new JButton("\u2718");
		button3.setToolTipText("Löscht den derzeit ausgewählten Termin in der Combobox");
		button3.setBounds(230, 80, 25, 50);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSelectedDate();
				try {
					Terminverwaltung.termineToTxt();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttons[2] = button3;

		for (JButton b : buttons) {
			b.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			if (b.getText().equals("\u2718")) {
				b.setForeground(red);
			} else {
				b.setForeground(green);
			}
			sortTermine();
			b.setBackground(Color.GRAY);
			b.setFont(new Font("", 0, 18));
			b.setFocusable(false);
			contentPane.add(b);
		}
	}

	// CHECK-FUNCTIONS
	// Funktionen die dem Status verschiedenener Objekte erfragen

	/**
	 * @brief Überprüft ob ein String ausschließlich Zahlen enthält
	 * @param s Der zu überprüfende String
	 * @return boolean true=> nur Zahlen ODER false=> Es sind auch andere Zeichen
	 *         enthalten
	 */
	public boolean isStringOnlyNumbers(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @brief Überprüft ob ein bestimmtes Datum als String schon in der ComboBox
	 *        enthalten ist
	 * @param date Das zu überprüfende Datum als String
	 * @return boolean true=> Datum schon enthalten ODER false=> wenn Datum noch
	 *         nicht enthalten
	 */
	public boolean isContainingDate(String date) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			if (comboBox.getItemAt(i).equals(date)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @brief Überprüft ob alle Textfelder gültige Eingaben haben
	 * @return boolean true=> Alle Eingaben korrekt ODER false=> Mindestens eine
	 *         Eingabe fehlerhaft
	 */
	public boolean areAllInputsCorrect() {
		if (!isInputCorrect(textfields[0]) || !isInputCorrect(textfields[1]) || !isInputCorrect(textfields[2])
				|| !isInputCorrect(textfields[3])) {
			return false;
		}
		return true;
	}

	/**
	 * @brief Überprüft ob ein einzeles Textfeld ein gültiges Format aufweist
	 * @param tf Das zu überprüfende Textfeld
	 * @return booelan true=> Textfeld ist korrekt ausgefüllt ODER false=> Textfeld
	 *         ist fehlerhaft ausgefüllt
	 */
	public boolean isInputCorrect(JTextField tf) {
		boolean correct = true;

		if (tf.getName().equals("dd")) {
			if (tf.getText().length() == 1) {
				tf.setText("0" + tf.getText());
			}
			if (tf.getText().length() != 2 || !isStringOnlyNumbers(tf.getText())) {
				correct = false;
			} else {
				if (Integer.parseInt(tf.getText()) > 31) {
					correct = false;
				}
			}
		} else if (tf.getName().equals("mm")) {
			if (tf.getText().length() == 1) {
				tf.setText("0" + tf.getText());
			}
			if (tf.getText().length() != 2 || !isStringOnlyNumbers(tf.getText())) {
				correct = false;
			} else {
				if (Integer.parseInt(tf.getText()) > 12) {
					correct = false;
				}
			}
		} else if (tf.getName().equals("yyyy")) {
			if (tf.getText().length() != 4 || !isStringOnlyNumbers(tf.getText())) {
				correct = false;
			}
		} else if (tf.getName().equals("hh:mm")) {
			int pointPos = -1;
			if (tf.getText().contains(":")) {
				pointPos = tf.getText().indexOf(':');
				if (pointPos != 2) {
					correct = false;
				}
			} else {
				correct = false;
			}

			if (tf.getText().length() != 5) {
				correct = false;
			} else {
				int hh = Integer.parseInt(tf.getText().substring(0, 2));
				int mm = Integer.parseInt(tf.getText().substring(3, 5));
				if (hh > 23) {
					correct = false;
				} else if (mm > 59) {
					correct = false;
				}
			}
		}
		if (!correct) {
			tf.setBackground(red);
		}
		return correct;
	}

	// UPDATE-FUNCTIONS
	// Diese Funktionen updaten die Anzeigen sodass alle Components
	// richtig positioniert, richtig farbig makiert und richtig ausgewählt sind.

	/**
	 * @brief Fügt einen neuen Termin der ComboBox hinzu
	 */
	public void addTermin() throws Exception {
		if (areAllInputsCorrect()) {
			LocalDateTime termin = LocalDateTime.parse(textfields[2].getText() + "-" + textfields[1].getText() + "-"
					+ textfields[0].getText() + "T" + textfields[3].getText());
			String text = Terminverwaltung.terminoutput(termin);
			if (!isContainingDate(text)) {
				Terminverwaltung.getTermine().add(termin);
				comboBox.addItem(Terminverwaltung.terminoutput(termin));
			}
			Collections.sort(Terminverwaltung.getTermine());
			sortTermine();
		} else {
			throw new Exception();
		}
	}

	/**
	 * @brief Sortiert die Termine in der Combobox
	 */
	public void sortTermine() {
		comboBox.removeAllItems();
		for (LocalDateTime d : Terminverwaltung.getTermine()) {
			comboBox.addItem(Terminverwaltung.terminoutput(d));
		}
	}

	/**
	 * @brief Setzt alle Farbmakierungen falscher Eingaben zurück
	 */
	public void resetColor() {
		for (JTextField tf : textfields) {
			tf.setBackground(Color.LIGHT_GRAY);
		}

	}

	/**
	 * @brief Setzt alle Farbmakierungen von Datumsbezogenen Textfelder auf rot
	 */
	public void markIncorrectDates() {
		for (JTextField tf : textfields) {
			tf.setBackground(red);
		}
	}

	/**
	 * @brief Schließt das Fenster und öffnet das Fenster mit den vorher eingebenen
	 *        Terminen
	 */
	public void closeWindow() {
		if (Terminverwaltung.getTermine().size() > 0) {
			Collections.sort(Terminverwaltung.getTermine());
			inputframe.setVisible(false);
			Main.startWindow("GUI-2");
		}
	}

	// REMOVE-FUNCTIONS
	// Diese Funtionen entfernen Objekte

	/**
	 * @brief Entfernt das ausgewählte Datum in der ComboBox
	 */
	public void removeSelectedDate() {
		if (Terminverwaltung.getTermine().size() > 0) {
			int index = comboBox.getSelectedIndex();
			comboBox.removeItem(comboBox.getSelectedItem());
			Terminverwaltung.getTermine().remove(index);
		}

	}

	// UTILLITY-FUNCTIONS
	// Diese Funktionen unterstützen die Haupt-Funktionen

	/**
	 * @brief Gibt das Frame zurück
	 * @return JFrame
	 */
	public JFrame getFrame() {
		return inputframe;
	}

}