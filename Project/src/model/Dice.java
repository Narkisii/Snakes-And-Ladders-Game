/**
 * 
 */
package model;

import java.util.Random;

/**
 * @author liorf
 *
 */
public class Dice {

	private int Difficulty; // 0 if game difficulty is easy, 1 if mid, 2 if hard

	/**
	 * @param Difficulty
	 */
	public Dice(int Difficulty) {
		super();
		this.Difficulty = Difficulty;
	}

	/**
	 * @return the Difficulty
	 */
	public int getDifficulty() {
		return Difficulty;
	}

	/**
	 * @param Difficulty the Difficulty to set
	 */
	public void setDifficulty(int Difficulty) {
		this.Difficulty = Difficulty;
	}

	public int getResult() {
		Random rand = new Random();
		int randomNumber;
		if (this.Difficulty == 0) // if game mode is easy
			return rand.nextInt(8);
		if (this.Difficulty == 1) { // if game mode is mid
			randomNumber = rand.nextInt(13);
			switch (randomNumber) {
			case 7:
			case 8:
				return 7;
			case 9:
			case 10:
				return 8;

			case 11:
			case 12:
				return 9;
			default:
				return randomNumber;
			}
		} else { // if game mode is hard
			randomNumber = rand.nextInt(15);
			switch (randomNumber) {
			case 7:
			case 8:
				return 7;
			case 9:
			case 10:
				return 8;

			case 11:
			case 12:
			case 13:
			case 14:
				return 9;
			default:
				return randomNumber;
			}
		}
	}

}
