package model;

import java.util.Arrays;
import java.util.Objects;

public class User {

	private String username;
	private char[] password;
	/**
	 * @param username
	 * @param password
	 */
	public User(String username, char[] password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public char[] getPassword() {
		return password;
	}
	
	public void setPassword(char[] password) {
		this.password = password;
	}

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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + Arrays.toString(password) + "]";
	}
}
