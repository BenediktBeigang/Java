import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @brief Diese Klasse stellt sämtliche Funktionen bereit die zur Eingabe,
 *        Verarbeitung und Ausgabe von Terminen gebraucht werden. 
 * @author Benedikt Beigang
 */
public class Terminverwaltung {
	static private ArrayList<LocalDateTime> termine;
	static private ArrayList<Integer> best = new ArrayList<Integer>();

	/**
	 * @brief Errechnet für jeden Termin wie viele Peronen Zeit haben
	 * @return ArrayList<Integer[]> Jeder Eintrag der ArrayListe enthält die Anzahl
	 *         der Personen die Zeit haben
	 */
	public static ArrayList<Integer[]> calcResults() {
		ArrayList<Integer[]> result = new ArrayList<Integer[]>();
		for (int i = 0; i < termine.size(); i++) {
			int sum = 0;
			int sumOfMaybe = 0;
			for (int j = 0; j < Person.getPersonen().size(); j++) {
				switch (Person.getPersonen().get(j).getSelections().get(i)) {
				case 2:
					sum++;
					break;
				case 1:
					sumOfMaybe++;
					break;
				default:
					break;
				}
			}
			Integer[] res = { sum, sumOfMaybe };
			result.add(res);
		}
		return result;
	}

	/**
	 * @brief Gibt ein übergebenes Datum als formatierten String aus
	 * @param d Das zu formatierende Datum
	 * @return String Der formatierte String
	 */
	static public String terminoutput(LocalDateTime d) {
		String out = "";
		DayOfWeek dow = d.getDayOfWeek();
		int day = d.getDayOfMonth();
		Month month = d.getMonth();
		int year = d.getYear();
		int h = d.getHour();
		String hour = "";
		if (h < 10) {
			hour = "0" + h;
		} else {
			hour = "" + h;
		}
		int m = d.getMinute();
		String minute = "";
		if (m < 10) {
			minute = "0" + m;
		} else {
			minute = "" + m;
		}
		out = Terminverwaltung.getShortDayOfWeek(dow) + " " + day + ". " + Terminverwaltung.getMonthInGerman(month)
				+ " " + year + " - " + hour + ":" + minute;
		return out;
	}
	
	/**
	 * @brief Errechnet und gibt zurück wie lang die Boxen eines Tages oder Monats
	 *        sein müssen
	 * @param md Gibt an ob man die Längen für die Tage ("days") oder für die
	 *           Monate("months") haben möchte
	 * @return ArrayList<Integer> Gibt die verschiedenen Längen der Boxen in einer
	 *         ArrayListe aus
	 */
	public static ArrayList<Integer> getAmountsOfSame(String md) {
		ArrayList<Integer> boxLengths = new ArrayList<Integer>();
		boxLengths.add(0);
		int currentIndex = 0;
		int currentDay = getFirstDay();
		int currentMonth = getFirstMonth();
		int currentYear = getFirstYear();
		for (LocalDateTime date : getTermine()) {
			if (md.equals("Days")) {
				if (date.getDayOfMonth() != currentDay || date.getMonthValue() != currentMonth
						|| date.getYear() != currentYear) {
					currentIndex++;
					boxLengths.add(0);
				}
			} else if (md.equals("Months")) {
				if (date.getMonthValue() != currentMonth || date.getYear() != currentYear) {
					currentIndex++;
					boxLengths.add(0);
				}
			}
			if (md.equals("Days") && date.getDayOfMonth() != currentDay) {
				currentDay = date.getDayOfMonth();
			}
			if (date.getMonthValue() != currentMonth) {
				currentMonth = date.getMonthValue();
			}
			if (date.getYear() != currentYear) {
				currentYear = date.getYear();
			}
			int newValue = boxLengths.get(currentIndex) + 1;
			boxLengths.set(currentIndex, newValue);
		}
		return boxLengths;
	}

	/**
	 * @brief Wandelt einen übergebenen Monat in eine deutsche Kurzform um
	 * @param month Der zu übersetzende Monat
	 * @return String Die deutsche Kurzform des Monats
	 */
	static public String getMonthInGermanShort(Month month) {
		if (month.equals(Month.JANUARY)) {
			return "Jan";
		}
		if (month.equals(Month.FEBRUARY)) {
			return "Feb";
		}
		if (month.equals(Month.MARCH)) {
			return "Mär";
		}
		if (month.equals(Month.APRIL)) {
			return "Apr";
		}
		if (month.equals(Month.MAY)) {
			return "Mai";
		}
		if (month.equals(Month.JUNE)) {
			return "Jun";
		}
		if (month.equals(Month.JULY)) {
			return "Jul";
		}
		if (month.equals(Month.AUGUST)) {
			return "Aug";
		}
		if (month.equals(Month.SEPTEMBER)) {
			return "Sep";
		}
		if (month.equals(Month.OCTOBER)) {
			return "Okt";
		}
		if (month.equals(Month.NOVEMBER)) {
			return "Nov";
		}
		if (month.equals(Month.DECEMBER)) {
			return "Dez";
		}
		return "";
	}

	/**
	 * @brief Übersetzte den Monat ins deutsche und gibt ihn als String zurück
	 * @param month Der zu übersetzende String
	 * @return String Der deutsche Monat
	 */
	static public String getMonthInGerman(Month month) {
		if (month.equals(Month.JANUARY)) {
			return "Januar";
		}
		if (month.equals(Month.FEBRUARY)) {
			return "Februar";
		}
		if (month.equals(Month.MARCH)) {
			return "März";
		}
		if (month.equals(Month.APRIL)) {
			return "April";
		}
		if (month.equals(Month.MAY)) {
			return "Mai";
		}
		if (month.equals(Month.JUNE)) {
			return "Juni";
		}
		if (month.equals(Month.JULY)) {
			return "Juli";
		}
		if (month.equals(Month.AUGUST)) {
			return "August";
		}
		if (month.equals(Month.SEPTEMBER)) {
			return "September";
		}
		if (month.equals(Month.OCTOBER)) {
			return "Oktober";
		}
		if (month.equals(Month.NOVEMBER)) {
			return "November";
		}
		if (month.equals(Month.DECEMBER)) {
			return "Dezember";
		}
		return "";
	}

	/**
	 * @brief Übersetzt den übergebenen Tag der Woche in die deutsche Kurzform
	 * @param day Der zu übersetzende Tag
	 * @return String Die übersetzte deutsche Kurzform
	 */
	static public String getShortDayOfWeek(DayOfWeek day) {
		if (day.equals(DayOfWeek.MONDAY)) {
			return "Mo";
		}
		if (day.equals(DayOfWeek.FRIDAY)) {
			return "Fr";
		}
		if (day.equals(DayOfWeek.SATURDAY)) {
			return "Sa";
		}
		if (day.equals(DayOfWeek.SUNDAY)) {
			return "So";
		}
		if (day.equals(DayOfWeek.THURSDAY)) {
			return "Do";
		}
		if (day.equals(DayOfWeek.TUESDAY)) {
			return "Di";
		}
		if (day.equals(DayOfWeek.WEDNESDAY)) {
			return "Mi";
		}
		return "";
	}

	/**
	 * @brief Gibt das älteste Jahr der Termine zurück
	 * @return int Das Jahr wird als int zurückgegeben
	 */
	static public int getFirstYear() {
		if (termine != null) {
			return termine.get(0).getYear();
		}
		return 0;
	}

	/**
	 * @brief Gibt den ersten Monat der Termine zurück
	 * @return int Der Monat wird als int zurückgegeben
	 */
	static public int getFirstMonth() {
		if (termine != null) {
			return termine.get(0).getMonthValue();
		}
		return 0;
	}

	/**
	 * @brief Gibt den ersten Tag der Termine zurück
	 * @return int Der Tag wird als int zurückgegeben
	 */
	static public int getFirstDay() {
		if (termine != null) {
			return termine.get(0).getDayOfMonth();
		}
		return 0;
	}

	/**
	 * @brief Gibt alle Termine eines bestimmten Jahres als ArrayListe zurück
	 * @param year Das gewählte Jahr
	 * @return ArrayList<LocalDateTime> Alle Termine des gewählten Jahres
	 */
	static public ArrayList<LocalDateTime> getDatesByYear(int year) {
		ArrayList<LocalDateTime> termineByYear = new ArrayList<LocalDateTime>();
		for (LocalDateTime date : termine) {
			if (date.getYear() == year) {
				termineByYear.add(date);
			}
		}
		return termineByYear;
	}

	/**
	 * @brief Gibt alle Termine eines bestimmten Monats als ArrayListe zurück
	 * @termineByYear Liste aller Termine in einem Jahr
	 * @param month Der gewählte Monat
	 * @return ArrayList<LocalDateTime> Alle Termine des gewählten Jahres
	 */
	static public ArrayList<LocalDateTime> getDatesByMonth(ArrayList<LocalDateTime> termineByYear, int month) {
		ArrayList<LocalDateTime> termineByMonth = new ArrayList<LocalDateTime>();
		for (LocalDateTime date : termineByYear) {
			if (date.getMonthValue() == month) {
				termineByMonth.add(date);
			}
		}
		return termineByMonth;
	}

	/**
	 * @brief Gibt alle Termine eines bestimmten Tages aus
	 * @param termineByMonth Liste aller Termine in diesem Monat
	 * @param day            Der gewählte Tag
	 * @return ArrayList<LocalDateTime> Alle Termine des gewählten Tages
	 */
	static public ArrayList<LocalDateTime> getDatesByDay(ArrayList<LocalDateTime> termineByMonth, int day) {
		ArrayList<LocalDateTime> termineByDay = new ArrayList<LocalDateTime>();
		for (LocalDateTime date : termineByMonth) {
			if (date.getDayOfMonth() == day) {
				termineByDay.add(date);
			}
		}
		return termineByDay;
	}

	/**
	 * @brief Gibt alle Jahre die in den Terminen vorkommen als Liste aus
	 * @return ArrayList<Integer> Die Jahre werden als Integer in der Liste
	 *         gespeichert
	 */
	static public ArrayList<Integer> getJahre() {
		ArrayList<Integer> jahre = new ArrayList<Integer>();
		for (LocalDateTime date : termine) {
			if (!jahre.contains(date.getYear())) {
				jahre.add(date.getYear());
			}
		}
		return jahre;
	}

	/**
	 * @brief Gibt die Anzahl der verschiedenen Jahre aus
	 * @return int Die Anzahl wird als in zurückgegeben
	 */
	static int getAnzahlJahre() {
		return getJahre().size();
	}

	/**
	 * @brief Gibt alle Terimine zurück
	 * @return ArrayList<LocalDateTime> Die Termine werden als ArraListe
	 *         zurückgegeben
	 */
	public static ArrayList<LocalDateTime> getTermine() {
		return termine;
	}

	/**
	 * @brief Setzt die Termine neu
	 * @param t Die Liste der neuen Termine
	 */
	public static void setTermine(ArrayList<LocalDateTime> t) {
		termine = t;
	}

	/**
	 * @brief Gibt die Anzahl von Terminen zurück
	 * @return int Die Anzahl wird als int zurückgegeben
	 */
	public static int getAnzahlTermine() {
		return termine.size();
	}

	/**
	 * @brief Gibt die Position der Spalten mit den Besten Terminen als ArrayList
	 *        zurück
	 * @return ArrayList<Integer> Liste mit den Besten Terminen
	 */
	public static ArrayList<Integer> getBest() {
		return best;
	}

	/**
	 * @brief Setzt die besten Termine
	 * @param b Die neuen besten Termine
	 */
	public static void setBest(ArrayList<Integer> b) {
		best = b;
	}

	/**
	 * @throws FileNotFoundException
	 * @brief Wandelt die eingetragenen Termine in String um und speichert ihn in
	 *        einer Textdatei ab
	 */
	public static void termineToTxt() throws FileNotFoundException {
		String str = "";

		for (LocalDateTime date : termine) {
			String year = Integer.toString(date.getYear());

			String mon = "";
			if (date.getMonthValue() < 10) {
				mon += "0";
			}
			mon += Integer.toString(date.getMonthValue());

			String day = "";
			if (date.getDayOfMonth() < 10) {
				day += "0";
			}
			day += Integer.toString(date.getDayOfMonth());

			String hour = "";
			if (date.getHour() < 10) {
				hour += "0";
			}
			hour += date.getHour();

			String min = "";
			if (date.getMinute() < 10) {
				min += "0";
			}
			min += date.getMinute();

			str += year + "-" + mon + "-" + day + "T" + hour + ":" + min + "\n";
		}

		PrintWriter out = new PrintWriter("termine.txt");
		out.println(str);
		out.close();
	}

	/**
	 * @brief Importiert die gespeicherten Termine aus der Txt in eine ArrayList und
	 *        gibt sie zurück
	 * @return ArrayList<LocalDateTime> Liste welche alle Termine als LocalDateTime
	 *         speichert
	 * @throws IOException Wird geworfen wenn ein Fehler beim Einlesende passiert
	 */
	public static ArrayList<LocalDateTime> terminTxtToArrayList() throws IOException {
		ArrayList<LocalDateTime> termine = new ArrayList<LocalDateTime>();
		File file = new File("termine.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line = "";
		while ((line = br.readLine()) != null) {
			try {
				LocalDateTime date = LocalDateTime.parse(line);
				termine.add(date);
			} catch (Exception e) {
				if(!line.equals("")) {
					System.out.println("Fehlerhafte Zeile in Datei");
				}
			}
		}
		br.close();
		Collections.sort(termine);
		return termine;
	}

	/**
	 * @brief Ermöglicht die Eingabe von Terminen über die Konsole. Diese Funktion
	 *        wird nicht mehr verwedent seit es ein eigenes GUI gibt für die
	 *        Termineingabe. Sollte trotzdem bei der Bewertung wert auf die Eingabe
	 *        in der Konsole gelegt werden wird diese Funktion weiterhin zur
	 *        Verfügung gestellt.
	 */
	static public void termineEinlesen() {
		Scanner console = new Scanner(System.in);
		boolean eingelesen = false;

		while (!eingelesen) {
			System.out.println("Termin eingeben (Format: \"jjjj-mm-ddThh:mm\" )");
			String input = console.next();

			try {
				System.out.println("Correct Input");
				LocalDateTime date = LocalDateTime.parse(input);
				termine.add(date);
			} catch (Exception e) {
				System.out.println("Incorrect Input");
			}

			boolean correctInput = false;
			while (!correctInput) {
				System.out.println("Neuer Termin? (y/n)");
				console.reset();
				input = console.next();
				System.out.println("Eingabe: " + input);
				if (input.equals("n")) {
					eingelesen = true;
					correctInput = true;
					System.out.println("Einlesen beendet");
				} else if (input.equals("y")) {
					correctInput = true;
					eingelesen = false;
				} else {
					correctInput = false;
					eingelesen = false;
					System.out.println("Ungültige Eingabe");
				}
			}
		}
		Collections.sort(termine);
		console.close();
	}
}
