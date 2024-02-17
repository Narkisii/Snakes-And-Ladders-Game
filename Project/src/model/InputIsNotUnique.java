package model;

public class InputIsNotUnique extends Exception {
	public InputIsNotUnique(String str) {
		super (str + " is a duplicae answer");
	}
}
