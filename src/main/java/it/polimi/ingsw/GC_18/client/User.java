package it.polimi.ingsw.GC_18.client;

import it.polimi.ingsw.GC_18.client.gui.AudioHandler;
import it.polimi.ingsw.GC_18.client.gui.Chat;
import it.polimi.ingsw.GC_18.client.gui.Room;
import it.polimi.ingsw.GC_18.model.GameMode;

/**
 * Represents the user of the application in the client side
 */
public final class User {

    private static String username;
    private static String password;
    private static boolean usingGuiInterface;
    private static boolean audioEnabled;
    private static GameMode gameMode=GameMode.ADVANCED;
    private static boolean yourTurn;
    private static Room room;
    private static Chat chat;

    /**
     * Hiding constructor, since this class doen't have to be instantiated
     */
    private User(){
    }

    /**
     * Instantiates a user client side
     * @param user - user name of the user
     * @param pw - password of the user
     * @param gui - true if the user choose to use GUI, false for console
     */
    static void createUser(String user,String pw, boolean gui) {
        username=user;
        password=pw;
        usingGuiInterface=gui;
    }

    /**
     * @return the user name
     */
    public static String getUsername() {
        return username;
    }

    /**
     * @return the user password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @return the flag for the GUI
     */
    public static boolean isUsingGuiInterface() {
        return usingGuiInterface;
    }

    /**
     * Sets the flag for the GUI
     * @param usingGuiInterface boolean indicating if the user is using interface.
     */
    public static void setUsingGuiInterface(boolean usingGuiInterface) {
        User.usingGuiInterface=usingGuiInterface;
        View.getMainFrame().setVisible(usingGuiInterface);
        AudioHandler.setAudioEnabled(false);
    }

    /**
     * @return true if is the user turn, false otherwise
     */
    static boolean isYourTurn() {
        return yourTurn;
    }

    /**
     * Sets the yourTurn variable
     * @param yourTurn boolean indicating if it is the user turn.
     */
    static void setYourTurn(boolean yourTurn) {
        User.yourTurn=yourTurn;
    }

    /**
     * @return the GameMode choose by the user
     */
    static GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @return the Room of this user
     */
    public static Room getRoom() {
        return room;
    }

    /**
     * Sets this user's room.
     * @param room - the room to set
     */
    public static void setRoom(Room room) {
        User.room = room;
    }

    /**
     * @return the chat panel of this user
     */
    public static Chat getChat() {
        return chat;
    }

    /**
     * Sets this user's chat panel.
     * @param chat - the chat panel to set
     */
    public static void setChat(Chat chat) {
        User.chat = chat;
    }

    /**
     * @return whether or not the audio is enabled
     */
    public static boolean isAudioEnabled() {
        return audioEnabled;
    }

    /**
     * Toggles the audio
     * @param audioEnabled - true to enable audio, false to deactivate it
     */
    public static void setAudioEnabled(boolean audioEnabled) {
        User.audioEnabled = audioEnabled;
        AudioHandler.setAudioEnabled(audioEnabled);
    }

}
