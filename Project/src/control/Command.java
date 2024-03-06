package control;

/**
 * The Command interface is a part of the Command design pattern.
 * This pattern is used to encapsulate a request as an object, thereby letting users
 * parameterize clients with queues, requests, and operations.
 */
public interface Command {
	/**
	 * The execute method is used to apply the command.
	 * 
	 */
	void execute();
}//