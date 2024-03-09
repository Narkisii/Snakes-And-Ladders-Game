package exceptions;

public class Questions_empty extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Questions_empty() {
		super("Some question lists are empty, please add more questions");
	}
}
