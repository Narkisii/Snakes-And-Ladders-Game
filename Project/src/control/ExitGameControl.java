package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for handling the exit confirmation pop-up in the game.
 */
public class ExitGameControl {

    @FXML
    private Button exitButton, noExitButton;

    @FXML
    private AnchorPane exitPop_Pane;

    /**
     * Reference to the previous window (BoardControl) that triggered the exit pop-up.
     */
    private BoardControl previousWindow;

    /**
     * Initializes the controller and sets up button event handlers.
     */
    @FXML
    void initialize() {
        setButtons();
    }

    /**
     * Sets up event handlers for the exit and "no exit" buttons.
     */
    private void setButtons() {
        exitButton.setOnAction(event -> {
            // Close the pop-up window after a short delay for visual feedback
            PauseTransition delay = new PauseTransition(Duration.millis(500));
            delay.setOnFinished(event_2 -> {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();

                // Call the previous window's clear_all method (assuming it exists)
                if (previousWindow != null) {
                    previousWindow.clear_all();
                }
            });
            delay.play();
        });

        noExitButton.setOnAction(event -> {
            // Close the pop-up window directly on "no exit" click
        	previousWindow.pause();
            ((Stage) noExitButton.getScene().getWindow()).close();
        });
    }

    /**
     * Sets the reference to the previous window (BoardControl) associated with this pop-up.
     *
     * @param boardControl The BoardControl object representing the previous window.
     */
    public void setPreviousWindow(BoardControl boardControl) {
        this.previousWindow = boardControl;
    }
}
