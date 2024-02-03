package model;

public class History {
	private String date;
	private String players;
	private int difficulty;
	private String playTime;
	private String winner;

	/**
	 * @param date
	 * @param players
	 * @param difficulty
	 * @param playTime
	 * @param winner
	 */
	public History(String date, String players, int difficulty, String playTime, String winner) {
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
	public String getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(String players) {
		this.players = players;
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
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
