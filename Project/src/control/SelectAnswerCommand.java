package control;

import model.cpu_Player;

/**
 * The SelectAnswerCommand class implements the Command interface.
 * This class is used to encapsulate the action of a CPU player selecting an answer.
 */
public class SelectAnswerCommand implements Command {
    // The player who will be selecting an answer.
    private cpu_Player player;

    /**
     * The constructor for the SelectAnswerCommand class.
     *
     * @param player The CPU player who will be selecting an answer.
     */
    public SelectAnswerCommand(cpu_Player player) {
        this.player = player;
    }

    /**
     * The execute method causes the player to select an answer.
     */
    @Override
    public void execute() {
        player.selectAnswer();
    }
}
