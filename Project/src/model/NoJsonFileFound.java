package model;

public class NoJsonFileFound extends Exception {

	public NoJsonFileFound() {
		super("Please make sure the Json folder is in the same directory as the jar file,"
				+ "\nMake sure the name of the folder is \"Json\","+"\nMake sure the file names are \n\"History.txt\" \nand \n\"Questions.txt\" ");
	}
}
