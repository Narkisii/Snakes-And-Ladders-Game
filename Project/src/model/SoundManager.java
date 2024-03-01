package model;

import Intrefaces.GameEventObserver;
import enums.GameEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class SoundManager implements GameEventObserver {
    @Override
    public void onEventTriggered(GameEvent event) {
        switch (event) {
            case PLAYER_HIT_SNAKE:
                playSnakeSound();
                break;
            case PLAYER_HIT_LADDER:
                playLadderSound();
                break;
            case PLAYER_WINS:
                playWinSound();
                break;
            case PLAYER_ANSWERS_QUESTION:
                playQuestionSound();
                break;
            case CORRECT_ANSWER:
                playCorrectAnswerSound();
                break;
            case INCORRECT_ANSWER:
                playIncorrectAnswerSound();
                break;
            case PLAYER_MISSES_TURN:
                playMissTurnSound();
                break;
            case DICE_ROLL:
                playDiceRollSound();
                break;
            default:
                // Optionally handle any unhandled cases
                break;
        }
    }

    private void playSnakeSound() {
        // Code to play snake sound
    }

    private void playLadderSound() {
    	  try {
    			System.out.println("sound1");

    	        // Adjust the path to where your sound file is located
    	        URL soundFile = this.getClass().getResource("/sounds/UpTheLadder.wav");
    	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
    	        Clip clip = AudioSystem.getClip();
    	        clip.open(audioIn);
    	    	System.out.println("sound2");
    	        clip.start();
    	    	System.out.println("sound3");
    	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
    	        e.printStackTrace();
    	    }    }

    private void playWinSound() {
        // Code to play win sound
    }

    private void playQuestionSound() {
        // Code to play question sound (trivia loop)
    }

    private void playCorrectAnswerSound() {
        // Code to play correct answer sound
    }

    private void playIncorrectAnswerSound() {
        // Code to play incorrect answer sound
    }

    private void playMissTurnSound() {
        // Code to play miss turn sound
    }

    private void playDiceRollSound() {
        // Code to play dice roll sound
    }

    // You can implement the sound-playing methods using javax.sound.sampled or any other Java sound API
}
