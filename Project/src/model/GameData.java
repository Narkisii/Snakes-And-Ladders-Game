package model;

import java.util.HashMap;

import control.BoardControl;
import control.HistoryControl;
import control.PlayersControl;

public class GameData {
    private static int numberOfPlayers = 1;
    private static String difficulty = "Easy"; // Need to be int
    private HashMap<Integer,Question> questions;
    private static Player[] players;
    private static Snake[] sankes;
    private static Ladder[] Ladders;
    private static Tile[] specialTiles;
    private static HistoryControl History;
    private static Dice dice;
    

    /**
	 * @return the difficulty
	 */
	public static String getDifficulty() {
		return difficulty;
	}
	
	 /**
		 * @return the difficulty
		 */
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
	 * @return the players
	 */
	public static Player[] getPlayers() {
		return players;
	}

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
	 * @param players the players to set
	 */
	public static void setPlayers(Player[] players) {
		GameData.players = players;
	}

	/**
	 * @return the sankes
	 */
	public static Snake[] getSankes() {
		return sankes;
	}

	/**
	 * @param sankes the sankes to set
	 */
	public static void setSankes(Snake[] sankes) {
		GameData.sankes = sankes;
	}

	/**
	 * @return the ladders
	 */
	public static Ladder[] getLadders() {
		return Ladders;
	}

	/**
	 * @param ladders the ladders to set
	 */
	public static void setLadders(Ladder[] ladders) {
		Ladders = ladders;
	}

	/**
	 * @return the specialTiles
	 */
	public static Tile[] getSpecialTiles() {
		return specialTiles;
	}

	/**
	 * @param specialTiles the specialTiles to set
	 */
	public static void setSpecialTiles(Tile[] specialTiles) {
		GameData.specialTiles = specialTiles;
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
	 * @return the dice
	 */
	public static Dice getDice() {
		return dice;
	}

	/**
	 * @param dice the dice to set
	 */
	public static void setDice(Dice dice) {
		GameData.dice = dice;
	}

    
    public static void to_json() {
    }

    public static Question getRandQuestion(int diff) {
		return null;
    	
    }
    
}
