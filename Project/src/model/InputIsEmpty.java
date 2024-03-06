package model;

/**
 * Exception class for handling cases where an input is empty.
 */
public class InputIsEmpty extends Exception{

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that takes a string as an argument and appends " field is empty" to it.
	 *
	 * @param str The name of the field that is empty.
	 */
	public InputIsEmpty (String str) {
		super(str + " field is empty");
	}

}
