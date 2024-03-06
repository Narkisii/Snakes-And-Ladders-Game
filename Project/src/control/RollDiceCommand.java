package control;

import model.cpu_Player;

/**
 * The RollDiceCommand class implements the Command interface
 * and represents a command to roll the dice for a CPU player.
 */
public class RollDiceCommand implements Command {

	private cpu_Player player;

	/**
	 * Constructs a new RollDiceCommand with the specified CPU player.
	 * @param player The CPU player for which the dice will be rolled.
	 */
	public RollDiceCommand(cpu_Player player) {
		this.player = player;
	}

	/**
	 * Executes the command by instructing the CPU player to roll the dice.
	 */
	@Override
	public void execute() {
		player.executeDice_roll();
	}

}
