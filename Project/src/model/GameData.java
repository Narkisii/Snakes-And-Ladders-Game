package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import control.HistoryControl;

public class GameData {
	private int numberOfPlayers = 1;
	private String difficulty;
	private HashMap<Integer, Question> questions;
	private LinkedList<Player> players;
	private LinkedList<Snake> sankes;
	private LinkedList<Ladder> ladders;
	private LinkedList<Tile> specialTiles;
	private HistoryControl History;
	private int playerTurn;
//    private static Dice dice;

	// Singleton instance
	private static GameData instance = null;

	// Private constructor
	private GameData() {
		// Initialization code here
		questions = new HashMap<Integer, Question>();
		players = new LinkedList<Player>();
		sankes = new LinkedList<Snake>();
		ladders = new LinkedList<Ladder>();
		specialTiles = new LinkedList<Tile>();
		questions = new HashMap<Integer, Question>();
		difficulty = "Easy";
		playerTurn = 0;
	}

	// Static method to get the singleton instance
	public static GameData getInstance() {
		if (instance == null) {
			instance = new GameData();
		}
		return instance;
	}

	/**
	 * @return the difficulty
	 */
	public String getDifficulty() {
		return difficulty;
	}

	/**
	 * @return the playerTurn
	 */
	public int getPlayerTurn() {
		return playerTurn;
	}

	/**
	 * @param playerTurn the playerTurn to set
	 */
	public void setPlayerTurn(int p_Turn) {
		playerTurn = p_Turn;
	}

	/**
	 * @return the difficulty
	 */
	// NumOfTiles X NumOfTiles = size of Board
	public int getNumOfTiles() {
		switch (getDifficulty()) {
		case "Easy":
			return 7;
		case "Medium":
			return 10;
		case "Hard":
			return 13;
		default:
			return 7;
		}
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(String diff) {
		System.out.println("setDifficulty " + difficulty);
		difficulty = diff;
	}

	/**
	 * @return the numberOfPlayers
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/**
	 * @param numberOfPlayers the numberOfPlayers to set
	 */
	public void setNumberOfPlayers(int num) {
		numberOfPlayers = num;
	}

//	/**
//	 * @return the difficulty
//	 */
//	public static int getDifficulty() {
//		return difficulty;
//	}
//
//	/**
//	 * @param difficulty the difficulty to set
//	 */
//	public static void setDifficulty(int difficulty) {
//		GameData.difficulty = difficulty;
//	}

	/**
	 * @return the questions
	 */
	public HashMap<Integer, Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(HashMap<Integer, Question> questions) {
		this.questions = questions;
	}

	/**
	 * @return the history
	 */
	public HistoryControl getHistory() {
		return History;
	}

	/**
	 * @param history the history to set
	 */
	public void setHistory(HistoryControl history) {
		History = history;
	}

	/**
	 * @return the players
	 */
	public LinkedList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(LinkedList<Player> p) {
		players = p;
	}

	public void addPlayers(Player player) {
		players.add(player);
	}

	/**
	 * @return the sankes
	 */
	public LinkedList<Snake> getSankes() {
		return sankes;
	}

	/**
	 * @param sankes the sankes to set
	 */
	public void setSankes(LinkedList<Snake> s) {
		sankes = s;
	}

	public void addSnakes(Snake s) {
		sankes.add(s);
	}

	/**
	 * @return the ladders
	 */
	public LinkedList<Ladder> getLadders() {
		return ladders;
	}

	/**
	 * @param ladders the ladders to set
	 */
	public void setLadders(LinkedList<Ladder> l) {
		ladders = l;
	}

	public boolean addLadders(Ladder l) {
		if (l != null) {
			System.out.println("Added ladder:" + l.toString());
			ladders.add(l);
			return true;
		}
		return false;
	}

	/**
	 * @return the specialTiles
	 */
	public LinkedList<Tile> getSpecialTiles() {
		return specialTiles;
	}

	/**
	 * @param specialTiles the specialTiles to set
	 */
	public void setSpecialTiles(LinkedList<Tile> st) {
		specialTiles = st;
	}

	public void addSpecialTiles(Tile st) {
		specialTiles.add(st);
	}

	public void to_json() {
	}

	public Question getRandQuestion(int diff) {
		return null;

	}

}
