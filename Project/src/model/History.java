package model;

import java.util.List;

public class History {
	private String date;
	private List<String> players;
	private String difficulty;
	private String playTime;
	private String winner;

	public History() {
	}

	/**
	 * @param date
	 * @param players
	 * @param difficulty
	 * @param playTime
	 * @param winner
	 */
	public History(String date, List<String> players, String difficulty, String playTime, String winner) {
		super();
		this.date = date;
		this.players = players;
		this.difficulty = difficulty;
		this.playTime = playTime;
		this.winner = winner;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the players
	 */
	public List<String> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<String> players) {
		this.players = players;
	}

	/**
	 * @return the difficulty
	 */
	public String getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the playTime
	 */
	public String getPlayTime() {
		return playTime;
	}

	/**
	 * @param playTime the playTime to set
	 */
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	/**
	 * @return the winner
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(String winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "History [date=" + date + ", players=" + players + ", difficulty=" + difficulty + ", playTime="
				+ playTime + ", winner=" + winner + "]";
	}

}
