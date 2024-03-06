package control;

/**
 * The DelayCommand class implements the Command interface
 * and represents a command to introduce a delay for a specified duration.
 */
public class DelayCommand implements Command {

	private int delayInSeconds;

	/**
	 * Constructs a new DelayCommand with the specified delay duration in seconds.
	 * @param delayInSeconds The duration of the delay in seconds.
	 */
	public DelayCommand(int delayInSeconds) {
		this.delayInSeconds = delayInSeconds;
	}

	/**
	 * Gets the delay duration in seconds.
	 * @return The duration of the delay in seconds.
	 */
	public int getDelayInSeconds() {
		return delayInSeconds;
	}

	/**
	 * Executes the command by printing a message indicating the delay duration.
	 */
	@Override
	public void execute() {
		System.out.println("DelayCommand " + delayInSeconds);
	}
}
