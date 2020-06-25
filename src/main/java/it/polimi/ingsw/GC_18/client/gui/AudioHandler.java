package it.polimi.ingsw.GC_18.client.gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.Main;
import it.polimi.ingsw.GC_18.client.State;
import it.polimi.ingsw.GC_18.client.User;

/**
 * Handler for the game audio part. Starts at the opening of a client application with the INTRO_CLIP,
 * then loops until the game start with the INTO_LOOP, then loops with GAME_LOOP until game end, then
 * back to the start.
 * NOTE: the audio files located in sounds must be of wave type for this to work.
 */
public class AudioHandler {

    private static final Logger LOGGER = Logger.getLogger(AudioHandler.class.getName());
    private static final String INTRO_CLIP="resources/sounds/RainingBloodIntro.wav";
    private static final String INTRO_LOOP="resources/sounds/RainingBloodLoop.wav";
    private static final String GAME_LOOP="resources/sounds/GameSoundTrack.wav";

    private static Clip clip;

    /**
     * hiding constructor, since this class doen't have to be instantiated
     */
    private AudioHandler(){
    }

    /**
     * Updates the audio currently reproduced based on the state making it non blocking.
     * @param state - the client current state
     * @throws LineUnavailableException 
     * @throws IOException 
     * @throws UnsupportedAudioFileException 
     */
    public static void updateAudio(State state) {
        stopSound();
        Main.getMainThreadPool().submit(() -> {
            if (!User.isAudioEnabled())
                return;
            try {
                switch (state) {
                case LOGGING_IN:
                case IN_MENU:
                case IN_ROOM:
                    playThenLoopSounds(INTRO_CLIP, INTRO_LOOP);
                    break;
                case GAME_ENDED:
                case IN_GAME:
                default:
                    loopSound(GAME_LOOP);
                    break;
                }
            } catch (UnsupportedAudioFileException e) {
                LOGGER.log(Level.WARNING, "AUDIO FILE IS NOT SUPPORTED", e);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "PROBLEM READING AUDIO FILE", e);
            } catch (LineUnavailableException e) {
                LOGGER.log(Level.WARNING, "CHECK THAT YOUR SYSTEM SUPPORTS THE LINES AND THAT THEY ARE NOT ALREADY IN USE", e);
            }
        });
    }

    /**
     * Turns off and on the audio reproduction.
     * @param enabled - true if the audio has to be activated, false otherwise.
     */
    public static void setAudioEnabled(boolean enabled) {
        if (!User.isUsingGuiInterface() || (clip != null && clip.isRunning() && !enabled)) {
            stopSound();
        } else if (enabled) {
            updateAudio(Controller.getState());
        }
    }

    /**
     * Sets the clip to be reproduced from a file
     * @param fileName - path to the file that contains the clip to reproduce
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private static void setClip(String fileName)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(fileName);
        if (file.exists()) {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
        }
    }

    /**
     * Stops any current reproducing sounds and plays the sound located at fileName
     * @param fileName - the path to the sound to play
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private static void playSound(String fileName)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        stopSound();
        setClip(fileName);
        play();
    }

    /**
     * Rewinds and starts the clip
     */
    private static void play() {
        clip.setFramePosition(0);// Rewinding
        clip.start();
    }

    /**
     * Stops any current reproducing sounds and loops the sound located at fileName
     * @param fileName - the path to the sound to loop
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private static void loopSound(String fileName)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        stopSound();
        setClip(fileName);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops any current reproducing sounds
     * then plays the sound located at suondToPlay
     * then loops the sound located at soundToLoop
     * @param soundToPlay - path to the sound to play
     * @param soundToLoop - path to the sound to loop
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private static void playThenLoopSounds(String soundToPlay, String soundToLoop)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        stopSound();
        playSound(soundToPlay);
        while (clip.getFramePosition() < clip.getFrameLength())
            Thread.yield();
        loopSound(soundToLoop);
    }

    /**
     * Stops the current clip
     */
    private static void stopSound() {
        if (clip != null) {
            clip.stop();
        }
    }

}