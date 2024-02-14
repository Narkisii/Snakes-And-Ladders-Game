/**
 * 
 */
package model;

import enums.Colors;

/**
 * @author liorf
 *
 */
public class Player {


	private String color; // the color of the player's token
	private String name; // the name of the player
	private int currentP = 1 ; // Current position of the player start from tile num 1 
	private String token; // **Object type is a temp type** the token of the player
							// (i.e a circle, square, etc...)

	/**
	 * @param color
	 * @param name
	 * @param token
	 */
	public Player(String color, String name, String token) {
		super();
		this.color = color;
		this.name = name;
		this.token = token;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
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
	public Object getToken() {
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
		return "Player [color=" + color + ", name=" + name + ", currentP=" + currentP + ", token=" + token + "]";
	}
}
