package model;

import java.util.HashMap;

/**
 * Class to handle the history data from JSON.
 */
public class HistoryFromJson {
	private HashMap<Integer, History> historyList;

	/**
	 * Constructor that initializes the history list.
	 *
	 * @param path The path to the JSON file.
	 */
	public HistoryFromJson(String path) {
		super();
		this.historyList = new HashMap<Integer, History>();
		// implement json import method
	}
	
	/**
	 * Retrieves the history data from JSON.
	 *
	 * @return The history list.
	 */
	public HashMap<Integer, History> getHistoryFromJson() {
		return historyList;
	}

	/**
	 * Retrieves the history list.
	 *
	 * @return The history list.
	 */
	public HashMap<Integer, History> getHistoryList() {
		return historyList;
	}
}
