package model;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.HistoryControl;

public class GameData {
	private int numberOfPlayers;
	private String difficulty;
	private HashMap<Integer, LinkedList<Question>> questions_Map;
	private LinkedList<Player> player_list;
	private LinkedList<Snake> snake_list;
	private LinkedList<Ladder> ladders;
	private LinkedList<Tile> specialTiles_list;
	private HistoryControl History;
	private int playerTurn;
	private Board board;
	private boolean in_game;
//    private static Dice dice;

	// Singleton instance
	private static GameData instance = null;

	// Private constructor
	private GameData() {
		// Initialization code here
//		this.questions = new HashMap<Integer, Question>();
		this.player_list = new LinkedList<Player>();
		this.snake_list = new LinkedList<Snake>();
		this.ladders = new LinkedList<Ladder>();
		this.specialTiles_list = new LinkedList<Tile>();
		this.questions_Map = new HashMap<Integer, LinkedList<Question>>();
		this.difficulty = "Easy";
		this.playerTurn = 0;
		this.numberOfPlayers = 1;
//		this.board = new Board(player_list);
		this.in_game = false;
	}

	// Static method to get the singleton instance
	public static GameData getInstance() {
		if (instance == null) {
			instance = new GameData();
		}
		return instance;
	}

	public void init_board() {
		this.board = new Board(player_list);
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
			return 0;
		}
	}

	public Question get_Question(int diff) {//7-easy, 8- med, 9 - hard
//		LinkedList<Question> q_list = questions_Map.get(diff);
//		Random rand = new Random();
//		int generator = rand.nextInt(q_list.size());
		
//		return q_list.get(generator);
		return new Question();
	}
	
	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(String diff) {
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

	/**
	 * @return the questions
	 */
	public HashMap<Integer, LinkedList<Question>> getQuestions() {
		return questions_Map;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(HashMap<Integer, LinkedList<Question>> questions) {
		this.questions_Map = questions;
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
	 * @return the player_list
	 */
	public LinkedList<Player> getplayer_list() {
		return player_list;
	}

	public Player get_Player(int index) {
		if(player_list.get(index) != null) {
			return player_list.get(index);
		}
		return null;
	}
	
	/**
	 * @param player_list the player_list to set
	 */
	public void setPlayer_list(LinkedList<Player> p) {
		player_list = p;
	}

	public boolean addPlayer(Player player) {
		if (player != null) {
			player_list.add(player);
			return true;
		}
		else {
			return false;
		}
	}

	public Player getPlayer(Player player) {
		for (Player p : player_list) {
			if (p.equals(player)) {
				return p;
			}
		}
		return null;
	}
	public Player getPlayer(int player) {
		
		return player_list.get(player);
	}

	/**
	 * @return the snake_list
	 */
	public LinkedList<Snake> getSnake_list() {
		return snake_list;
	}

	/**
	 * @param snake_list the snake_list to set
	 */
	public void setSnake_list(LinkedList<Snake> s) {
		snake_list = s;
	}

	public boolean addSnake(Snake s) {
		if (s != null) {
			snake_list.add(s);
			return true;
		}
		return false;
	}

	public Snake getPlayer(Snake snake) {
		for (Snake s : snake_list) {
			if (s.equals(snake)) {
				return s;
			}
		}
		return null;
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
//			System.out.println("Added ladder:" + l.toString());
			ladders.add(l);
			return true;
		}
		return false;
	}

	/**
	 * @return the specialTiles_list
	 */
	public LinkedList<Tile> getspecialTiles_list() {
		return specialTiles_list;
	}

	/**
	 * @param specialTiles_list the specialTiles_list to set
	 */
	public void setspecialTiles_list(LinkedList<Tile> st) {
		specialTiles_list = st;
	}

	public void addspecialTiles_list(Tile st) {
		specialTiles_list.add(st);
	}
	public boolean addSpecialtile(Tile t) {
		if(t != null) {
			return (specialTiles_list.add(t));
		}
		return false;
	}
	public void to_json() {
	}

	public Question getRandQuestion(int diff) {
		return null;

	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return the in_game
	 */
	public void set_In_game(boolean bool) {
		this.in_game = bool;
	}

	/**
	 * @param in_game the in_game to set
	 */
	public boolean get_isIngame() {
		return in_game;
	}

	@Override
	public String toString() {
		return "GameData [numberOfPlayers=" + numberOfPlayers + ", difficulty=" + difficulty + ", questions_Map="
				+ questions_Map + ", player_list=" + player_list + ", snake_list=" + snake_list + ", ladders=" + ladders
				+ ", specialTiles_list=" + specialTiles_list + ", History=" + History + ", playerTurn=" + playerTurn
				+ ", board=" + board + ", in_game=" + in_game + "]";
	}
	


}
