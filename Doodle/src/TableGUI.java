import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @brief Diese Klasse stellt eine interaktive Oberfläche bereit, die dazu dient
 *        dass Personen sich mit Ihrem Namen eintragen können und rechts daneben
 *        ihre favorisierten Termine makieren. Die Klasse zeigt anschließend an
 *        welcher Terminen den meisten Personen passt.
 * @author Benedikt Beigang
 */
public class TableGUI {
	private ArrayList<ButtonGroup> buttonGroups;
	private ArrayList<JRadioButton> radioButtons;
	private ArrayList<Box> radioButtonBoxes;
	private ArrayList<JButton> deleteButtons;
	private boolean[] wrongInputs;
	private int anzahlTermine;

	private JFrame mainframe;
	private JPanel contentPane;

	// Utility
	private JButton backButton;
	private JTextField empty;

	// Termine
	private JPanel terminPanel;

	// Input
	private JPanel inputPanel;
	private JTextField name;

	// Choices
	private DefaultTableModel selectionTableModel;
	private JTable selectionTable;
	private JPanel deleteButtonPanel;

	// Output
	private DefaultTableModel resultTableModel;
	private JTable resultTable;
	private JPanel outputPanel;

	// Colors
	private Color green;
	private Color red;
	private Color purple;

	// Konstruktor
	public TableGUI() {
		radioButtonBoxes = new ArrayList<Box>();
		anzahlTermine = Terminverwaltung.getAnzahlTermine();
		buttonGroups = new ArrayList<ButtonGroup>();
		wrongInputs = new boolean[anzahlTermine + 1];
		radioButtons = new ArrayList<JRadioButton>();
		deleteButtons = new ArrayList<JButton>();

		green = Color.decode("#91d982");
		red = Color.decode("#ff5454");
		purple = Color.decode("#ca85ff");

		newWindow();
	}

	// PAINT-FUNCTIONS AND FUNCTIONS FOR COMPONENT-CREATION
	// Diese Funtionen erstellen die erforderlichen Components, welche im Fenster zu
	// sehen sind

	/**
	 * @brief Erstellt das Fenster zu Darstellung des Terminplans
	 */
	public void newWindow() {
		mainframe = new JFrame();
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setBounds(400, 300, 700 + Terminverwaltung.getAnzahlTermine() * 50, 375);
		contentPane = new JPanel();
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		// Paint_Function_Calls
		paintBackwardPanel();
		paintTerminPanel();
		paintPersonTable();
		paintInputPanel();
		paintResultTable();
		paintOutputPanel();

		mainframe.getContentPane().add(contentPane);

		empty = new JTextField();
		empty.grabFocus();
		contentPane.add(empty);
	}

	/**
	 * @brief Erstellt einen Button der das Fenster zur Termineingabe öffnet
	 */
	public void paintBackwardPanel() {
		backButton = new JButton("\u2BB2 Termineingabe");
		backButton.setToolTipText("Bringt Sie zurück zur Termineingabe");
		backButton.setBounds(0, 0, 200, 25);
		backButton.setFont(new Font("", Font.BOLD, 20));
		backButton.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		backButton.setBackground(Color.GRAY);
		backButton.setForeground(Color.WHITE);
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.setVisible(false);
				Main.restartInputFrame();
			}
		});

		contentPane.add(backButton);
	}

	/**
	 * @brief Erstellt die Anzeige der Termine
	 */
	public void paintTerminPanel() {
		terminPanel = new JPanel();
		terminPanel.setBounds(200, 40, anzahlTermine * 50, 110);
		terminPanel.setLayout(null);

		// Time
		terminPanel.add(createTimes());

		// Days
		ArrayList<Integer> boxLengths = Terminverwaltung.getAmountsOfSame("Days");
		terminPanel.add(createDayOrMonthBoxes("Days", boxLengths));

		// Months
		boxLengths = Terminverwaltung.getAmountsOfSame("Months");
		terminPanel.add(createDayOrMonthBoxes("Months", boxLengths));

		contentPane.add(terminPanel);
	}

	/**
	 * @brief Erstellt die Leiste der Uhrzeiten
	 * @return Gibt ein JPanel zurück welche die Uhrzeiten der verschiedenen
	 *         Termine, in der richtigen Reihenfolge,hintereinander in Boxen
	 *         darstellt
	 */
	public JPanel createTimes() {
		int x = 0;
		JPanel times = new JPanel();
		times.setName("Zeiten");
		times.setLayout(null);
		times.setBounds(0, 60, anzahlTermine * 50, 50);
		times.setBackground(purple);
		for (LocalDateTime date : Terminverwaltung.getTermine()) {
			String text = "";
			if (date.getHour() > 9) {
				text = Integer.toString(date.getHour()) + ":";
			} else {
				text = "0" + Integer.toString(date.getHour()) + ":";
			}
			if (date.getMinute() > 9) {
				text += date.getMinute();
			} else {
				text += "0" + date.getMinute();
			}

			JPanel box = new JPanel();
			box.setBounds(x, 0, 50, 50);
			box.setBackground(purple);
			box.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

			JLabel uhrzeit = new JLabel(text);
			uhrzeit.setBounds(0, 0, 50, 25);

			JLabel uhr = new JLabel("Uhr");
			uhr.setBounds(0, 25, 50, 25);

			box.add(uhrzeit);
			box.add(uhr);
			times.add(box);

			x += 50;
		}
		return times;
	}

	/**
	 * @brief Erstellt die Leiste der Tage oder Monate, je nachdem welcher
	 *        indentifizierende String übergeben wurde (md == Days => Tage werden
	 *        erzeugt, md == Months => Monate werden erzeugt)
	 * @return Gibt ein JPanel zurück welches die Tage oder Monate der Termine in
	 *         aufsteigender Reihenfolge in Boxen erzeugt
	 */
	public JPanel createDayOrMonthBoxes(String md, ArrayList<Integer> boxLengths) {
		ArrayList<LocalDateTime> termine = Terminverwaltung.getTermine();
		JPanel panel = new JPanel();
		panel.setLayout(null);

		int boxcounter = 0;
		int x = 0;
		int y = 0;
		for (int i = 0; i < boxLengths.size(); i++) {
			int width = 50 * boxLengths.get(i);

			String text = "";

			if (md.equals("Days")) {
				y = 30;
				text = "  " + Terminverwaltung.getShortDayOfWeek(termine.get(boxcounter).getDayOfWeek()) + " "
						+ Integer.toString(termine.get(boxcounter).getDayOfMonth());
			} else if (md.equals("Months")) {
				if (md.equals("Months") && width < 75) {
					text = Terminverwaltung.getMonthInGermanShort((termine.get(boxcounter).getMonth())) + " '"
							+ Integer.toString(termine.get(boxcounter).getYear()).substring(2);
				} else {
					text = "  " + Terminverwaltung.getMonthInGerman((termine.get(boxcounter).getMonth())) + " "
							+ termine.get(boxcounter).getYear();
				}
			}

			JLabel label = new JLabel(text);
			label.setBounds(x, 0, width, 30);
			label.setBackground(purple);
			label.setForeground(Color.DARK_GRAY);
			label.setOpaque(true);
			label.setLayout(null);
			label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			panel.add(label);
			x += width;
			boxcounter += boxLengths.get(i);
		}
		panel.setBounds(0, y, anzahlTermine * 50, 30);
		return panel;
	}

	/**
	 * @brief Initialisiert die Tabelle welche die Auswahl der Personen darstellt
	 */
	public void paintPersonTable() {
		selectionTableModel = new DefaultTableModel(new Object[] { "Name" }, 0);
		selectionTable = new JTable(selectionTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		selectionTable.setToolTipText("Hier steht für jede Person ihre Auswahl, ob sie an einem Termin Zeit hat");
		selectionTable.setRowSelectionAllowed(false);
		selectionTable.setColumnSelectionAllowed(false);
		selectionTable.setFocusable(false);
		selectionTable.setTableHeader(null);
		selectionTable.setRowHeight(30);
		selectionTable.setBounds(50, 150, 150 + anzahlTermine * 50, 0);
		selectionTable.setFont(new Font("Arial Black", Font.BOLD, 16));
		selectionTable.setBackground(Color.LIGHT_GRAY);
		selectionTable.setForeground(Color.BLACK);
		selectionTable.setAlignmentX(JTable.CENTER_ALIGNMENT);
		selectionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 1; i < anzahlTermine + 1; i++) {
			selectionTableModel.addColumn("Date " + i);
		}

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);

		selectionTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
		selectionTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		selectionTable.getColumnModel().getColumn(0).setMinWidth(150);
		selectionTable.getColumnModel().getColumn(0).setMaxWidth(150);
		for (int i = 1; i < anzahlTermine + 1; i++) {
			selectionTable.getColumnModel().getColumn(i).setPreferredWidth(50);
			selectionTable.getColumnModel().getColumn(i).setMinWidth(50);
			selectionTable.getColumnModel().getColumn(i).setMaxWidth(50);
			selectionTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		deleteButtonPanel = new JPanel();
		deleteButtonPanel.setLayout(null);
		deleteButtonPanel.setBounds(200 + anzahlTermine * 50, 150, 30, 0);

		contentPane.add(deleteButtonPanel);
		contentPane.add(selectionTable);
		mainframe.setVisible(true);
	}

	/**
	 * @brief Erstellt das Eingabefeld für eine neue Person. Es besteht aus einem
	 *        Textfeld für den Namen und einer Reihe von Boxen gefüllt mit
	 *        RadioButtons, die zur Wahl der Verfügbarkeit einer Person dienen.
	 */
	public void paintInputPanel() {
		inputPanel = new JPanel();
		inputPanel.setName("inputPanel");
		inputPanel.setLayout(null);
		inputPanel.setBackground(Color.DARK_GRAY);
		inputPanel.setBounds(20, 150, 180 + 50 * anzahlTermine, 91);
		inputPanel.add(createSaveButton());
		inputPanel.add(createNameInput());

		for (int i = 0; i < anzahlTermine; i++) {
			int x = 180 + 50 * i;
			int y = 0;

			ButtonGroup buttonGroup = new ButtonGroup();
			Box verticalBox = Box.createVerticalBox();
			verticalBox.setBounds(x, y, 50, 91);
			verticalBox.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

			JRadioButton radioButton1 = new JRadioButton(new ImageIcon(getClass().getResource("green1.png")));
			JRadioButton radioButton2 = new JRadioButton(new ImageIcon(getClass().getResource("orange1.png")));
			JRadioButton radioButton3 = new JRadioButton(new ImageIcon(getClass().getResource("red1.png")));
			JRadioButton[] rbs = { radioButton1, radioButton2, radioButton3 };

			radioButton1.setSelectedIcon(new ImageIcon(getClass().getResource("green2.png")));
			radioButton2.setSelectedIcon(new ImageIcon(getClass().getResource("orange2.png")));
			radioButton3.setSelectedIcon(new ImageIcon(getClass().getResource("red2.png")));

			radioButton1.setName("yes");
			radioButton2.setName("maybe");
			radioButton3.setName("no");

			for (JRadioButton rb : rbs) {
				rb.setToolTipText("<html>Hier können sie für jeden Termin auswählen, ob sie Zeit haben.<br> "
						+ "Grüner Haken -> Habe Zeit<br>" + "Roter Haken -> Habe keine Zeit<br>"
						+ "Orangener Haken -> Habe vielleicht Zeit</html>");
				rb.setBorderPainted(true);
				rb.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
				rb.setBackground(Color.gray);
				radioButtons.add(rb);
				buttonGroup.add(rb);
				verticalBox.add(rb);
			}

			radioButtonBoxes.add(verticalBox);
			buttonGroups.add(buttonGroup);
			inputPanel.add(verticalBox);
		}
		contentPane.add(inputPanel);
	}

	/**
	 * @brief Erstellt und gibt einen Button, welcher zur Erstellung einer neuen
	 *        Person dient, zurück
	 * @param JButton Gibt einen Button zurück der zum Hinzufügen einer neuen Person
	 *                dient
	 */
	public JButton createSaveButton() {
		JButton button = new JButton("+");
		button.setToolTipText("Dieser Button fügt eine neue Person hinzu, wenn alle Eingaben korrekt waren");
		button.setFont(new Font("", Font.BOLD, 20));
		button.setBounds(0, 0, 30, 30);
		button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		button.setBackground(Color.GRAY);
		button.setForeground(green);
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isInputCorrect()) {
					addPersonToTable();
					markBest();
					name.setText("");
				}
				markIncorrect();
				mainframe.repaint();
			}
		});
		return button;
	}

	/**
	 * @brief Erstellt und gibt ein Textfeld in dem ein neuer Name eingegeben werden
	 *        kann, zurück
	 * @param JTextField Gibt ein TextFeld zurück welches zur Eingabe des Namens
	 *                   dient
	 */
	public JTextField createNameInput() {
		JTextField name = new JTextField("Name eingeben");
		name.setToolTipText("Hier können sie einen neuen Namen eintragen");
		name.setFont(new Font("Arial Black", Font.BOLD, 16));
		name.setColumns(10);
		name.setBackground(Color.LIGHT_GRAY);
		name.setForeground(Color.DARK_GRAY);
		name.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (name.getText().equals("Name eingeben")) {
					name.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
				if (name.getText().equals("")) {
					name.setText("Name eingeben");
				}
			}
		});
		name.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		name.setBounds(30, 0, 150, 30);
		this.name = name;
		return name;
	}

	/**
	 * @brief Erstellt das Ausgabefeld für die besten Termine rechts neben dem
	 *        Terminplan
	 */
	public void paintOutputPanel() {
		removeComponentByName("output");
		outputPanel = new JPanel();
		int height = 50;
		outputPanel.setName("output");
		outputPanel.setBounds(300 + 50 * anzahlTermine, 50, 300, height);
		outputPanel.setBackground(Color.GRAY);
		outputPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		JTextField uberschrift = createDefaultTextField();
		uberschrift.setText(" Beste Termine: ");
		uberschrift.setFont(new Font("Arial Black", Font.BOLD, 18));
		uberschrift.setBackground(Color.DARK_GRAY);
		uberschrift.setForeground(Color.LIGHT_GRAY);
		uberschrift.setBounds(0, 0, 150, 100);
		uberschrift.setFocusable(false);
		uberschrift.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		uberschrift.setEditable(false);
		outputPanel.add(uberschrift);

		contentPane.add(outputPanel);
	}

	/**
	 * @brief Initialisiert die Tablelle welche die Anzahlen von den Personen
	 *        positiven gewählten Terminen anzeigt.
	 */
	public void paintResultTable() {
		resultTableModel = new DefaultTableModel(new Object[] { "Date0" }, 1);
		resultTable = new JTable(resultTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		resultTable.setToolTipText("Diese Tabelle zeigt für jeden Tag an, wie viele Personen an diesem Tag Zeit haben");
		resultTable.setRowSelectionAllowed(false);
		resultTable.setColumnSelectionAllowed(false);
		resultTable.setFocusable(false);
		resultTable.setTableHeader(null);
		resultTable.setRowHeight(30);
		resultTable.setBounds(200, 241, anzahlTermine * 50, 30);
		resultTable.setFont(new Font("Arial", Font.BOLD, 16));
		resultTable.setBackground(Color.LIGHT_GRAY);
		resultTable.setForeground(Color.BLACK);
		resultTable.setAlignmentX(JTable.CENTER_ALIGNMENT);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 1; i < anzahlTermine; i++) {
			resultTableModel.addColumn("Date " + i);
		}

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < anzahlTermine; i++) {
			resultTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			resultTable.getColumnModel().getColumn(i).setPreferredWidth(50);
			resultTable.getColumnModel().getColumn(i).setMinWidth(50);
			resultTable.getColumnModel().getColumn(i).setMaxWidth(50);
		}

		resultTable.setVisible(false);
		contentPane.add(resultTable);
	}

	/**
	 * @brief Gibt ein Standard Textfeld für dieses Projekt zurück
	 */
	public JTextField createDefaultTextField() {
		JTextField tf = new JTextField();
		tf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		tf.setEditable(false);
		tf.setFocusable(false);
		return tf;
	}

	// UPDATE-FUNCTIONS
	// Diese Funktionen updaten die Anzeigen sodass alle Components
	// richtig Positioniert, richtig farbig makiert und richtig ausgewählt sind.

	/**
	 * @brief Updatet die Ergebnis-Tabelle (Für den Fall dass Personen "Vielleicht
	 *        Zeit" angegeben haben, wird die Anzahl dieser Personen als "+Zahl"
	 *        angezeigt) Wird aufgerufen nachdem eine Person hinzugefügt oder
	 *        gelöscht wurde
	 */
	public void updateResultTable() {
		if (Person.getPersonen().size() == 0) {
			resultTable.setVisible(false);
			return;
		}
		resultTable.setVisible(true);

		ArrayList<Integer> best = new ArrayList<Integer>();
		ArrayList<Integer[]> selected = Terminverwaltung.calcResults();
		int max = 0;

		for (int i = 0; i < anzahlTermine; i++) {
			Integer[] num = selected.get(i);
			// num[0]: Die Summe der "Habe Zeit" Angaben
			// num[1]: Die Summe der "Vielleicht Zeit" Angaben
			if (num[1] > 0) {
				resultTable.getModel().setValueAt(Integer.toString(num[0]) + " +" + num[1], 0, i);
			} else {
				resultTable.getModel().setValueAt(Integer.toString(num[0]), 0, i);
			}
			if (max < num[0]) {
				best = new ArrayList<Integer>();
				best.add(i);
				max = num[0];
			} else if (max == num[0] && max != 0) {
				best.add(i);
			}
		}
		Terminverwaltung.setBest(best);

		ResultTableColorCellRenderer renderer = new ResultTableColorCellRenderer();
		for (int i = 0; i < anzahlTermine; i++) {
			resultTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		empty.grabFocus();
		addBestToOutput(max);
	}
	
	/**
	 * @brief Fügt eine Person der selectionTabelle hinzu. Dabei beinhaltet eine
	 *        Zelle die möglichen Zusatände: OK => Person hat Zeit, ? => Person hat
	 *        vielleicht Zeit, X => Person hat keine Zeit an diesem Termin
	 */
	public void addPersonToTable() {
		Person newPerson = new Person(name.getText(), getRadioSelections());
		Person.getPersonen().add(newPerson);

		Object[] objects = new Object[2 + anzahlTermine];

		objects[0] = Person.getPersonen().get(Person.getPersonen().size() - 1).getName();
		selectionTableModel.addRow(objects);

		for (int i = 1; i < anzahlTermine + 1; i++) {
			selectionTable.getColumnModel().getColumn(i).setCellRenderer(new SelectionTableCellRenderer());
		}

		JButton delete = new JButton("\u2718");
		delete.setToolTipText("Löscht den Eintrag der Person in dieser Zeile");
		delete.setFont(new Font("", Font.BOLD, 15));
		delete.setAlignmentX(JButton.CENTER_ALIGNMENT);
		delete.setBounds(0, 30 * (Person.getPersonen().size() - 1), 30, 30);
		delete.setFocusable(false);
		delete.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		delete.setBackground(red);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePersonFromTable(deleteButtons.indexOf(delete));
			}
		});
		deleteButtons.add(delete);
		deleteButtonPanel.add(delete);

		deselectRadioButtons();
		updateResultTable();
		updateLayout(-1);
	}

	/**
	 * @brief Erstellt bzw.updated Textfelder die die besten Termine in Textform in
	 *        das OutPutPanel schreiben. Bei mehr als 10 Terminen wird jeder weitere
	 *        Weggelassen um nicht das Fenster zu sprengen. Es wird angezeigt wie
	 *        viele weitere Termine in Frage kommen würden.
	 */
	public void addBestToOutput(int max) {
		JTextField temp = (JTextField) outputPanel.getComponent(0);
		ArrayList<Integer> best = Terminverwaltung.getBest();
		outputPanel.removeAll();
		outputPanel.add(temp);
		int y = 25;
		int height = 50;
		if (best != null) {
			ArrayList<Integer> earlyBest = new ArrayList<Integer>();
			if (best.size() > 10) {
				for (int i = 0; i < 10; i++) {
					earlyBest.add(best.get(i));
				}
			} else {
				earlyBest = best;
			}

			height += 30 + earlyBest.size() * 33;
			outputPanel.setBounds(300 + 50 * anzahlTermine, 50, 300, height);

			for (Integer i : earlyBest) {
				JTextField newTermin = new JTextField(
						" " + Terminverwaltung.terminoutput(Terminverwaltung.getTermine().get(i)) + " ");
				newTermin.setFont(new Font("Arial Black", Font.BOLD, 16));
				newTermin.setBackground(Color.DARK_GRAY);
				newTermin.setForeground(Color.LIGHT_GRAY);
				newTermin.setBounds(0, y, 200, 100);
				newTermin.setFocusable(false);
				newTermin.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
				newTermin.setEditable(false);
				y += 25;
				outputPanel.add(newTermin);
			}

			String weggelasseneTermine = "";
			if (best.size() - earlyBest.size() > 0) {
				weggelasseneTermine = "(+" + (best.size() - earlyBest.size()) + ")";
			}

			JTextField stimmen = new JTextField();
			if (max == 1) {
				stimmen.setText("mit " + max + " Stimme. " + weggelasseneTermine);
			} else {
				stimmen.setText("mit " + max + " Stimmen. " + weggelasseneTermine);
			}
			stimmen.setFocusable(false);
			stimmen.setEditable(false);
			stimmen.setBorder(null);
			stimmen.setFont(new Font("Arial Black", Font.BOLD, 16));
			stimmen.setBackground(Color.GRAY);
			stimmen.setForeground(Color.DARK_GRAY);
			stimmen.setBounds(0, y, 2, 100);

			outputPanel.add(stimmen);
		} else {
			outputPanel.setBounds(300 + 50 * anzahlTermine, 50, 300, height);
		}

	}

	/**
	 * @brief Erneuert die Positionen der einzelen Haupt-Components nach hinzufügen
	 *        oder löschen einer Person
	 * @param index definiert welche Zeile gelöscht wurde und verschiebt die
	 *              relevanten Components nach oben Sollte -1 übergeben werden,
	 *              wurde eine Person hinzugefügt. Die relevanten Components werden
	 *              nach unten geschoben
	 */
	public void updateLayout(int index) {
		// -1 => neue Reihe hinzugefügt
		// i>=0 => Reihe an Position i gelöscht
		int distanz;
		if (index == -1) {
			distanz = 30;
		} else {
			distanz = -30;
			// Lösche Buttons werden nach oben geschoben falls sie unter der gelöschten
			// Zeile sind
			for (int i = index; i < Person.getPersonen().size(); i++) {
				JButton b = (JButton) deleteButtons.get(i);
				b.setBounds(b.getX(), b.getY() - 30, b.getWidth(), b.getHeight());
			}
		}

		resultTable.setLocation(resultTable.getX(), resultTable.getY() + distanz);
		inputPanel.setLocation(inputPanel.getX(), inputPanel.getY() + distanz);

		deleteButtonPanel.setSize(deleteButtonPanel.getWidth(), deleteButtonPanel.getHeight() + distanz);
		selectionTable.setSize(selectionTable.getWidth(), selectionTable.getHeight() + distanz);

		int heightResultBox = 50 + outputPanel.getHeight();
		int heightTabel = 270 + Person.getPersonen().size() * 30;
		mainframe.setBounds(mainframe.getX(), mainframe.getY(), mainframe.getWidth(),
				Math.max(heightTabel, heightResultBox) + 100);
	}

	/**
	 * @brief Wählt alle RadioButtons ab
	 */
	public void deselectRadioButtons() {
		for (ButtonGroup bg : buttonGroups) {
			bg.clearSelection();
		}
	}

	/**
	 * @brief Makiert die Uhrzeitblöcke welche zu den BEsten Terminen gehören
	 */
	public void markBest() {
		JPanel panel = (JPanel) terminPanel.getComponent(0);
		ArrayList<Integer> best = Terminverwaltung.getBest();
		for (int i = 0; i < anzahlTermine; i++) {
			panel.getComponent(i).setBackground(purple);
			if (best != null && best.contains(i)) {
				panel.getComponent(i).setBackground(green);
			}
		}
	}

	/**
	 * @brief Markiert alle fehlerhaften Eingaben fabig
	 */
	public void markIncorrect() {
		name.setBackground(Color.LIGHT_GRAY);
		name.setForeground(Color.BLACK);
		for (Box x : radioButtonBoxes) {
			x.setBackground(Color.DARK_GRAY);
		}

		if (wrongInputs[0] == true) {
			name.setBackground(red);
			name.setForeground(Color.BLACK);
		}
		for (int i = 1; i < wrongInputs.length; i++) {
			if (wrongInputs[i] == true) {
				radioButtonBoxes.get(i - 1).setBackground(red);
			}
		}
	}

	// REMOVE-FUNCTIONS
	// Diese Funktionen sorgen für das Entfernen von Objekten

	/**
	 * @brief Löscht alle Einträge zu einer bestimmten Person und ruft die
	 *        Funktionen auf die die GUI entsprechend Updaten
	 * @param i Der Index der Einträge einer Person
	 */
	public void removePersonFromTable(int i) {
		Person.getPersonen().remove(i);
		((DefaultTableModel) selectionTable.getModel()).removeRow(i);
		deleteButtons.remove(i);
		deleteButtonPanel.remove(i);
		if (Person.getPersonen().isEmpty()) {
			Terminverwaltung.setBest(null);
		}
		addBestToOutput(0);
		updateResultTable();
		updateLayout(i);
		markBest();
		mainframe.repaint();
	}

	/**
	 * @brief Löscht ein gewünschtes Component aus dem contentPane anhand des Namens
	 * @param s String der den Namen des gesuchten Components enthält
	 * @post Component im contentPane gelöscht
	 */
	public void removeComponentByName(String s) {
		int remove = -1;
		for (int i = 0; i < contentPane.getComponents().length; i++) {
			if (contentPane.getComponent(i).getName() != null && contentPane.getComponent(i).getName().equals(s)) {
				remove = i;
			}
		}
		if (remove != -1) {
			contentPane.remove(remove);
		}
	}

	// UTILLITY-FUNCTIONS
	// Diese Funktionen unterstützen die Haupt-Funktionen

	/**
	 * @brief Gibt für eine RadioButton-Group zurück, welche der drei möglichen
	 *        RadioButton ausgewählt ist
	 * @param i Dieser Integer übergibt den Spaltenindex
	 * @return int Definiert welcher RadioButton ausgewählt ist (Habe Zeit => 2,
	 *         Habe Vielleicht Zeit => 1, Habe keine Zeit => 0)
	 */
	public int whichRadioButtonSelected(int i) {
		if (radioButtons.get(i * 3).isSelected()) {
			return 2;
		}
		if (radioButtons.get((i * 3) + 1).isSelected()) {
			return 1;
		}
		if (radioButtons.get((i * 3) + 2).isSelected()) {
			return 0;
		}
		return -1;
	}

	/**
	 * @brief Sammelt alle ausgewählten RadioButton und gibt diese Daten zurück
	 * @return ArrayList<Integer> Die ArrayList enthält für jeden Eintrag den
	 *         gewählten RadioButton als Integer-Wert (2,1,0), siehe Funktion:
	 *         whichRadioButtonSelected()
	 */
	public ArrayList<Integer> getRadioSelections() {
		ArrayList<Integer> selection = new ArrayList<Integer>();
		for (int i = 0; i < anzahlTermine; i++) {
			selection.add(whichRadioButtonSelected(i));
		}
		return selection;
	}

	// CHECK-FUNCTIONS
	// Diese Funktionen prüfen bestimmte Zustände ab

	/**
	 * @brief Gibt zurück ob die in die Textfelder geschriebenen Daten gültig sind.
	 *        Dazu zählt: Ob ein Name hereingeschrieben wurde, ob der Name noch
	 *        nicht vorhanden ist und dass alle RadioButton-Groups ausgefüllt sind
	 *        Außerdem wird das Array wrongInputs befüllt welches die fehlerhaften
	 *        Einträge verwaltet.
	 * @return boolean true=> Alle Eingaben OK ODER Eine oder mehere Eingaben
	 *         fehlerhaft
	 */
	public boolean isInputCorrect() {
		boolean out = true;
		for (int i = 0; i < wrongInputs.length; i++) {
			wrongInputs[i] = false;
		}
		if (name.getText().equals("") || name.getText().equals("Name eingeben") || Person.isNameAlreadyThere(name.getText())) {
			out = false;
			wrongInputs[0] = true;
		}
		for (int i = 0; i < wrongInputs.length - 1; i++) {
			if (!radioButtons.get(i * 3).isSelected() && !radioButtons.get(i * 3 + 1).isSelected()
					&& !radioButtons.get(i * 3 + 2).isSelected()) {
				out = false;
				wrongInputs[i + 1] = true;
			}
		}
		return out;
	}
}

// TABLE-RENDERER
// Diese Klassen Gestallten die Zellen der Tabellen

/**
 * @brief Diese Klasse stellt die nötigen Funktionen zur Verfügung um einzele
 *        Zellen der Result Tabelle zu Gestalten
 * @author Benedikt Beigang
 */
class ResultTableColorCellRenderer implements TableCellRenderer {
	private static final TableCellRenderer renderer = new DefaultTableCellRenderer();

	/**
	 * @brief Diese Funktion gibt ein JLabel zurück welche eine Zelle in einer
	 *        Tabelle entsprechen farbig makiert. Sie wird grün gefärbt für den Fall
	 *        dass der Eintrag zu den Besten Terminen zählt und bleibt grau für das
	 *        Gegenteil
	 * @param table Die zu verändernde Tabelle, value Der Wert der Zelle, isSelected
	 *              Wird die Zelle gerade ausgewählt, hasFocus Liegt der Focus
	 *              gerade auf der Zelle, row Die Zeile der Zelle, cloumn die Spalte
	 *              der Zelle
	 * @return JLabel Das gefärbte JLabel wird zurückgegeben
	 */
	public JLabel getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel l = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		l.setHorizontalAlignment((int) JPanel.CENTER_ALIGNMENT);
		if (Terminverwaltung.getBest().contains(column)) {
			l.setBackground(Color.decode("#91d982"));
		} else {
			l.setBackground(Color.LIGHT_GRAY);
		}
		return l;
	}
}

/**
 * @brief Diese Klasse stellt die nötigen Funktionen zur Verfügung um einzele
 *        Zellen der Selection Tabelle zu Gestalten
 * @author Benedikt Beigang
 */
class SelectionTableCellRenderer implements TableCellRenderer {
	private JLabel l = new JLabel();

	/**
	 * @brief Diese Funktion gibt ein JLabel zurück welche eine Zelle in einer
	 *        Tabelle entsprechen mit einem Icon makiert. Je nachdem ob die
	 *        jeweilige Person an einem bestimmten Termin Zeit hat oder nicht wird
	 *        entsprechend das richtige Icon ausgewählt und dargestellt
	 * @param table Die zu verändernde Tabelle, value Der Wert der Zelle, isSelected
	 *              Wird die Zelle gerade ausgewählt, hasFocus Liegt der Focus
	 *              gerade auf der Zelle, row Die Zeile der Zelle, cloumn die Spalte
	 *              der Zelle
	 * @return JLabel Das bearbeitete JLabel wird zurückgegeben
	 */
	public JLabel getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		int selection = Person.getPersonen().get(row).getSelections().get(column - 1);
		switch (selection) {
		case 2:
			l.setIcon(new ImageIcon(getClass().getResource("green1.png")));
			break;
		case 1:
			l.setIcon(new ImageIcon(getClass().getResource("orange1.png")));
			break;
		case 0:
			l.setIcon(new ImageIcon(getClass().getResource("red1.png")));
			break;
		default:
			l.setIcon(null);
			break;
		}
		return l;
	}
}
