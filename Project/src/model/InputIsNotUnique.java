package model;

public class InputIsNotUnique extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputIsNotUnique(String str) {
		super (str + " is a duplicae answer");
	}
}
