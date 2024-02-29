package control;

import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class CommandInvoker {
    private Queue<Command> commandQueue;

    public CommandInvoker() {
        this.commandQueue = new LinkedList<>();
    }

    public void executeCommands() {
        executeNextCommand();
    }

    public void addCommand(Command command) {
        commandQueue.add(command);
    }

    private void executeNextCommand() {
        if (!commandQueue.isEmpty()) {
            Command command = commandQueue.poll();
            if (command instanceof DelayCommand) {
                PauseTransition pause = new PauseTransition(Duration.seconds(((DelayCommand) command).getDelayInSeconds()));
                pause.setOnFinished(event -> executeNextCommand());
                pause.play();
            } else {
                command.execute();
                executeNextCommand();
            }
        }
    }
}
