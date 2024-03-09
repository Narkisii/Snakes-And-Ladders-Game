package model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a user with a username and password.
 */
public class User {

    @JsonProperty("username")
	private String username;
    
    @JsonProperty("password")
	private String password;
    
    public User() {
	
	}

	/**
	 * Constructor that initializes the username and password.
	 *
	 * @param username The username of the user.
	 * @param password The password of the user.
	 */
	public User(String username, String password) {
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
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password The password of the user.
	 */
	public void setPassword(String password) {
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
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	/**
	 * Overrides the toString method for the User class.
	 *
	 * @return The string representation of the user.
	 */
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
}
