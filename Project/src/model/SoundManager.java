package model;

import Intrefaces.GameEventObserver;
import enums.GameEvent;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Class for managing sound effects in the game. Implements the GameEventObserver interface for the observer pattern.
 */
public class SoundManager implements GameEventObserver {

    private double gain = 0.75; // 0.75 = 75%
    float dB = (float)(Math.log(gain)/Math.log(10.0)*20.0);

    /**
     * Plays a sound given a sound file name.
     *
     * @param soundFileName The name of the sound file.
     */
    private void playSound(String soundFileName) {
    	if(!GameData.getInstance().getSoundFX())
    		return;
        try {
            URL soundFile = this.getClass().getResource(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            // Set volume to 75%
            FloatControl gainControl = 
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Convert percentage to gain (decibels)
            gainControl.setValue(dB);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a sound based on the triggered game event.
     *
     * @param event The game event.
     */
    @Override
    public void onEventTriggered(GameEvent event) {
        switch (event) {
            case PLAYER_HIT_SNAKE:
                playSound("/sounds/Movement/PLAYER_HIT_SNAKE.wav");
                break;
            case PLAYER_HIT_LADDER:
                playSound("/sounds/Movement/PLAYER_HIT_LADDER.wav");
                break;
            case PLAYER_WINS:
                playSound("/sounds/Win/PLAYER_WINS.wav");
                break;
            case CORRECT_ANSWER:
                playSound("/sounds/Questions/CORRECT_ANSWER.wav");
                break;
            case INCORRECT_ANSWER:
                playSound("/sounds/Questions/INCORRECT_ANSWER.wav");
                break;
            case QUESTION_POP:
                playSound("/sounds/Questions/QUESTION_POP.wav");
                break;
            case PLAYER_TURN:
                playSound("/sounds/Movement/PLAYER_TURN.wav");
                break;
            case DICE_ROLL:
                playSound("/sounds/Dice/DICE_ROLL.wav");
                break;
            case PLAYER_MOVE:
                playSound("/sounds/Movement/PLAYER_MOVE.wav");
                break;
            case GOOD_SURPRISE:
                playSound("/sounds/Movement/PLAYER_HIT_LADDER.wav");
                break;
            case BAD_URPRISE:
                playSound("/sounds/Movement/PLAYER_HIT_SNAKE.wav");
                break;
            case RED_SNAKE:
                playSound("/sounds/Movement/PLAYER_HIT_SNAKE.wav");
                break;
            default:
                break;
        }
    }

}
