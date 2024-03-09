package model;

/**
 * Exception class for handling cases where the required JSON file is not found.
 */
public class NoJsonFileFound extends Exception {

	/**
	 * Constructor that provides a detailed error message instructing the user to ensure the presence and correct naming of the required JSON files.
	 */
	public NoJsonFileFound() {
		super("No json file in the directory, creating a Json file");
	}
}
