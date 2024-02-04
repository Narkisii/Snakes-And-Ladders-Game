package model;

import java.util.HashMap;

import control.Board;
import control.History;
import control.Players;

public class GameData {
    private static int numberOfPlayers = 1;
    private static String difficulty = "Easy"; // Need to be int
    private HashMap<Integer,Question> questions;
    private static Players[] players;
    private static Snake[] sankes;
    private static Ladder[] Ladders;
    private static Tile[] specialTiles;
    private static Board board;
    private static History History;
    private static Dice dice;
    

    /**
	 * @return the difficulty
	 */
	public static String getDifficulty() {
		return difficulty;
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
	public static Players[] getPlayers() {
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
	public static void setPlayers(Players[] players) {
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
	 * @return the board
	 */
	public static Board getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public static void setBoard(Board board) {
		GameData.board = board;
	}

	/**
	 * @return the history
	 */
	public static History getHistory() {
		return History;
	}

	/**
	 * @param history the history to set
	 */
	public static void setHistory(History history) {
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
