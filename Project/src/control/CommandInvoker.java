package control;

import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * A class that manages and executes a queue of commands, including support for delayed execution.
 */
public class CommandInvoker {

	/**
	 * Queue for storing the commands to be executed.
	 */
	private Queue<Command> commandQueue;

	/**
	 * Constructor to initialize the command queue.
	 */
	public CommandInvoker() {
		commandQueue = new LinkedList<>();
	}

	/**
	 * Initiates the execution of commands in the queue.
	 */
	public void executeCommands() {
		executeNextCommand();  // Start processing the queue
	}

	/**
	 * Adds a command to the queue for later execution.
	 *
	 * @param command The command to be added.
	 */
	public void addCommand(Command command) {
		commandQueue.add(command);
	}

	/**
	 * Executes the next command in the queue, handling delays and recursion.
	 */
	private void executeNextCommand() {
		if (!commandQueue.isEmpty()) {
			Command command = commandQueue.poll();  // Fetch the first command
			if (command instanceof DelayCommand) {
				// Handle a DelayCommand by pausing for the specified duration
				PauseTransition pause = new PauseTransition(
						Duration.seconds(((DelayCommand) command).getDelayInSeconds()));
				pause.setOnFinished(event -> executeNextCommand());  // Continue execution after the delay
				pause.play();
			} else {
				// Execute regular commands immediately
				command.execute();
				executeNextCommand();  // Recursively process the next command
			}
		}
	}
}
