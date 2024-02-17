package model;

public class InputIsEmpty extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InputIsEmpty (String str) {
		super(str + " field is empty");
	}

}

