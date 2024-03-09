package model;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class representing a user with a username and password.
 */
public class User {

    @JsonProperty("username")
	private String username;
    
    @JsonProperty("password")
	private String password;

	private String path = "/Json/Admin.txt";
    
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
		System.out.println("getUsername");

		return username;
	}

	/**
	 * Sets the username of the user.
	 *
	 * @param username The username of the user.
	 */
	public void setUsername(String username) {
		System.out.println("setUsername");

		this.username = username;
	}
	/**
	 * Gets the username of the user.
	 *
	 * @return The username of the user.
	 */
	@JsonIgnore
	public String getDecryptedUsername() {
		System.out.println("getDicryptedUsername");
		return decrypt(username);
	}

	/**
	 * Gets the username of the user.
	 *
	 * @return The username of the user.
	 */
	@JsonIgnore
	public String getDecryptedPassword() {
		System.out.println("getDicryptedPassword");
		return decrypt(password);
	}


	/**
	 * Gets the password of the user.
	 *
	 * @return The password of the user.
	 */
	public String getPassword() {
		System.out.println("getPassword");

		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password The password of the user.
	 */
	public void setPassword(String password) {
		System.out.println("setPassword");
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
    public void writeAdminJson(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Convert object to JSON string and save into a file directly
        try {
            mapper.writeValue(new File(path), user);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException
        }
    }
    
    public String encrypt(String str) {
        // This is a very basic encryption method known as the Caesar Cipher.
        // In a production environment, please use a more secure encryption method.
        String shifted = str.chars().mapToObj(c -> (char)(c + 3)).map(String::valueOf).collect(Collectors.joining());
        return Base64.getEncoder().encodeToString(shifted.getBytes());
    }
    
    public String decrypt(String str) {
        String decoded = new String(Base64.getDecoder().decode(str));
        return decoded.chars().mapToObj(c -> (char)(c - 3)).map(String::valueOf).collect(Collectors.joining());
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
