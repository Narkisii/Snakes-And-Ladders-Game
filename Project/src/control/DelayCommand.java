package control;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class DelayCommand implements Command {
    private int delayInSeconds;

    public DelayCommand(int delayInSeconds) {
        this.delayInSeconds = delayInSeconds;
    }

    public int getDelayInSeconds() {
        return delayInSeconds;
    }

    @Override
    public void execute() {
        System.out.println("DelayCommand " + delayInSeconds);
    }
}


