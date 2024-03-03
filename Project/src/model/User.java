package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class representing a user with a username and password.
 */
public class User {

	private String username;
	private char[] password;

	/**
	 * Constructor that initializes the username and password.
	 *
	 * @param username The username of the user.
	 * @param password The password of the user.
	 */
	public User(String username, char[] password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Gets the username of the user.
	 *
	 * @return The username of the user.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username of the user.
	 *
	 * @param username The username of the user.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the password of the user.
	 *
	 * @return The password of the user.
	 */
	public char[] getPassword() {
		return password;
	}
	
	/**
	 * Sets the password of the user.
	 *
	 * @param password The password of the user.
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}

	/**
	 * Overrides the equals method for the User class.
	 *
	 * @param obj The object to compare.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Arrays.equals(password, other.password) && Objects.equals(username, other.username);
	}

	/**
	 * Overrides the toString method for the User class.
	 *
	 * @return The string representation of the user.
	 */
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + Arrays.toString(password) + "]";
	}
}
