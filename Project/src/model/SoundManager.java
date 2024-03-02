package model;

import Intrefaces.GameEventObserver;
import enums.GameEvent;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager implements GameEventObserver {

    // A single method to play sounds, given a URL
    private void playSound(String soundFileName) {
        try {
            URL soundFile = this.getClass().getResource(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            // Set volume to 75%
            FloatControl gainControl = 
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Convert percentage to gain (decibels)
            double gain = 0.75; // 0.75 = 75%
            float dB = (float)(Math.log(gain)/Math.log(10.0)*20.0);
            gainControl.setValue(dB);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEventTriggered(GameEvent event) {
        switch (event) {
            case PLAYER_HIT_SNAKE:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case PLAYER_HIT_LADDER:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case PLAYER_WINS:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case CORRECT_ANSWER:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case INCORRECT_ANSWER:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case PLAYER_MISSES_TURN:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case DICE_ROLL:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case PLAYER_MOVE:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case GOOD_SURPRISE:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case BAD_URPRISE:
                playSound("/sounds/UpTheLadder.wav");
                break;
            case RED_SNAKE:
                playSound("/sounds/UpTheLadder.wav");
                break;
            default:
                break;
        }
    }

}
