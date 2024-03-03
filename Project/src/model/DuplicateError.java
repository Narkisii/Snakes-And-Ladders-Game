package model;

// This class extends the Exception class and is used to handle the specific case of a duplicate error.
public class DuplicateError extends Exception {

	// This is a unique identifier for this class that is used during serialization and deserialization.
	private static final long serialVersionUID = 1L;

	// This is the constructor for the DuplicateError class.
	// When an instance of this class is created, it calls the constructor of the superclass (Exception)
	// with a specific error message ("Duplicate question found").
	public DuplicateError () {
		super("Duplicate question found");
	}

}
