/**
 * 
 */
package model;

import java.util.HashMap;
import java.util.LinkedList;

import enums.Colors;

/**
 * @author liorf
 *
 */
public class Player {

	private String color; // the color of the player's token
	private String name; // the name of the player
	private int currentP; // Current position of the player start from tile num 1
	private String token; // **Object type is a temp type** the token of the player
							// (i.e a circle, square, etc...)
	private int ID;

	private LinkedList<Integer> placment_history;

	/**
	 * @param color
	 * @param name
	 * @param token
	 */
	public Player(int ID, String color, String name, String token) {
		super();
		this.setID(ID);
		this.color = color;
		this.name = name;
		this.token = "/view/Images/tokens/" + token + ".png";
		this.placment_history = new LinkedList<Integer>();
		this.currentP = 1;
		placment_history.add(1);
		System.out.println(token);
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	public void addStep(int placment) {
		placment_history.add(placment);
	}

	public int getPreviousStep() {
		if (placment_history.size() - 2 < 0) {
			return 0;
		}
		return placment_history.get(placment_history.size() - 2);
	}

	/**
	 * @return the placment_history
	 */
	public LinkedList<Integer> getPlacment_history() {
		return placment_history;
	}

	/**
	 * @param placment_history the placment_history to set
	 */
	public void setPlacment_history(LinkedList<Integer> placment_history) {
		this.placment_history = placment_history;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the currentP
	 */
	public int getCurrentP() {
		return currentP;
	}

	/**
	 * @param currentP the currentP to set
	 */
	public void setCurrentP(int currentP) {
		this.currentP = currentP;
	}

	@Override
	public String toString() {
		return "Player [color=" + color + ", name=" + name + ", currentP=" + currentP + ", token=" + token + ", ID="
				+ ID + "]";
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
