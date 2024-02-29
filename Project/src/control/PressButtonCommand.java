package control;

import model.cpu_Player;

public class PressButtonCommand implements Command {
    private cpu_Player player;

    public PressButtonCommand(cpu_Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.pressButton();
    }
}
