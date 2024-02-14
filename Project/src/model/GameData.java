package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import control.HistoryControl;

public class GameData {
    private static int numberOfPlayers = 1;
    private static String difficulty = "Easy"; // Need to be int
    private HashMap<Integer,Question> questions;
    private static LinkedList <Player> players;
    private static LinkedList <Snake> sankes;
    private static LinkedList <Ladder> ladders;
    private static LinkedList <Tile> specialTiles;
    private static HistoryControl History;
//    private static Dice dice;
    

    /**
	 * @return the difficulty
	 */
	public static String getDifficulty() {
		return difficulty;
	}
	
	 /**
		 * @return the difficulty
		 */
	// NumOfTiles X NumOfTiles = size of Board
	public static int getNumOfTiles() {
	    switch (difficulty) {
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
	public static void setDifficulty(String difficulty) {
		GameData.difficulty = difficulty;
	}

	/**
	 * @return the numberOfPlayers
	 */
	public static int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/**
	 * @param numberOfPlayers the numberOfPlayers to set
	 */
	public static void setNumberOfPlayers(int numberOfPlayers) {
		GameData.numberOfPlayers = numberOfPlayers;
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
	public static HistoryControl getHistory() {
		return History;
	}

	/**
	 * @param history the history to set
	 */
	public static void setHistory(HistoryControl history) {
		History = history;
	}

	
	
	
    /**
	 * @return the players
	 */
	public static LinkedList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public static void setPlayers(LinkedList<Player> players) {
		GameData.players = players;
	}

	/**
	 * @return the sankes
	 */
	public static LinkedList<Snake> getSankes() {
		return sankes;
	}

	/**
	 * @param sankes the sankes to set
	 */
	public static void setSankes(LinkedList<Snake> sankes) {
		GameData.sankes = sankes;
	}

	/**
	 * @return the ladders
	 */
	public static LinkedList<Ladder> getLadders() {
		return ladders;
	}

	/**
	 * @param ladders the ladders to set
	 */
	public static void setLadders(LinkedList<Ladder> ladders) {
		GameData.ladders = ladders;
	}

	/**
	 * @return the specialTiles
	 */
	public static LinkedList<Tile> getSpecialTiles() {
		return specialTiles;
	}

	/**
	 * @param specialTiles the specialTiles to set
	 */
	public static void setSpecialTiles(LinkedList<Tile> specialTiles) {
		GameData.specialTiles = specialTiles;
	}

	public static void to_json() {
    }

    public static Question getRandQuestion(int diff) {
		return null;
    	
    }
    
}
