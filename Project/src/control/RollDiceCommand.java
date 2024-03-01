package control;

import model.cpu_Player;

public class RollDiceCommand implements Command {
    private cpu_Player player;

	public RollDiceCommand(cpu_Player player) {
        this.player = player;

	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		player.executeDice_roll();
	}

}
