package control;

import model.cpu_Player;

public class SelectAnswerCommand implements Command {
    private cpu_Player player;

    public SelectAnswerCommand(cpu_Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.selectAnswer();
    }
}
