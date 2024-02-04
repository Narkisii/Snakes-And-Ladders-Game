package model;

import java.util.HashMap;

public class HistoryFromJson {
	private HashMap<Integer, History> historyList;

	public HistoryFromJson(String path) {
		super();
		this.historyList = new HashMap<Integer, History>();
		// implement json import method
	}
	
	public HashMap<Integer, History> getHistoryFromJson() {
		return historyList;
	}

	public HashMap<Integer, History> getHistoryList() {
		return historyList;
	}
}
