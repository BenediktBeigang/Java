import java.util.ArrayList;

/**
 * @brief Dise Klasse dient dem Organisieren von Personen. Außerdem werden alle
 *        Personen welche im Programm erfasst sind hier gespeichert.
 * @author Benedikt Beigang
 *
 */
public class Person {
	private static ArrayList<Person> personen;
	private String name;
	private ArrayList<Integer> selections;

	/**
	 * @brief Konstruktor der Klasse
	 * @param n     Name der Person
	 * @param selec Die Terminauswahl
	 */
	public Person(String n, ArrayList<Integer> selec) {
		name = n;
		selections = selec;
	}
	
	/**
	 * @brief Gibt zurück ob ein Name schon in der Namenliste vorhanden ist
	 * @return boolean true=> name schon vorhanden ODER false=> name noch nicht
	 *         vorhanden
	 */
	public static boolean isNameAlreadyThere(String name) {
		for (Person p : Person.getPersonen()) {
			if (p.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @brief Gibt Namen zurück
	 * @return String Der Name wird als String zurückgegeben
	 */
	public String getName() {
		return name;
	}

	/**
	 * @brief Ersetzt den Namen der Person
	 * @param name Der Name als String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @brief Gibt die Auswahl dieser Person zurück
	 * @return ArrayList<Integer> Die Auswahlen werden als ArrayList zurückgeben
	 */
	public ArrayList<Integer> getSelections() {
		return selections;
	}

	/**
	 * @brief Ersetzt die Auswahl dieser Person
	 * @param selections Die Auswahl diser Person als ArrayList
	 */
	public void setSelections(ArrayList<Integer> selections) {
		this.selections = selections;
	}

	/**
	 * @brief Gibt die Personen zurück welche sich eingetragen haben
	 * @return ArrayList<Person> Die Personen werden als ArrayList zurückgegeben
	 */
	public static ArrayList<Person> getPersonen() {
		if (personen == null) {
			personen = new ArrayList<Person>();
		}
		return personen;
	}
}
