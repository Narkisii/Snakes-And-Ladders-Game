package model;

/**
 * Exception class for handling cases where an input is not unique.
 */
public class InputIsNotUnique extends Exception {
	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that takes a string as an argument and appends " is a duplicate answer" to it.
	 *
	 * @param str The input that is not unique.
	 */
	public InputIsNotUnique(String str) {
		super (str + " is a duplicate answer");
	}
}
