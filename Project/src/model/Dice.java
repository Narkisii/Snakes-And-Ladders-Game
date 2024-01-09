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

	private int type; // 0 if game difficulty is easy, 1 if mid, 2 if hard

	/**
	 * @param type
	 */
	public Dice(int type) {
		super();
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	public int getResult() {
		Random rand = new Random();
		int randomNumber;
		if (this.type == 0) // if game mode is easy
			return rand.nextInt(8);
		if (this.type == 1) { // if game mode is mid
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
