package control;

import model.cpu_Player;

/**
 * The PressButtonCommand class implements the Command interface
 * and represents a command to press a button by a CPU player.
 */
public class PressButtonCommand implements Command {

	private cpu_Player player;

	/**
	 * Constructs a new PressButtonCommand with the specified CPU player.
	 * @param player The CPU player to which the command will be applied.
	 */
	public PressButtonCommand(cpu_Player player) {
		this.player = player;
	}

	/**
	 * Executes the command by instructing the CPU player to press a button.
	 */
	@Override
	public void execute() {
		player.pressButton();
	}
}
