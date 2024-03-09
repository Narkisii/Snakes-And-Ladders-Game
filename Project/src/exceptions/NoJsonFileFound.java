package exceptions;

/**
 * Exception class for handling cases where the required JSON file is not found.
 */
public class NoJsonFileFound extends Exception {

	/**
	 * Constructor that provides a detailed error message instructing the user to ensure the presence and correct naming of the required JSON files.
	 */
	public NoJsonFileFound() {
		super("Please make sure the Json folder is in the same directory as the jar file,"
				+ "\nMake sure the name of the folder is \"Json\","+"\nMake sure the file names are \n\"History.txt\" \nand \n\"Questions.txt\" ");
	}
}
