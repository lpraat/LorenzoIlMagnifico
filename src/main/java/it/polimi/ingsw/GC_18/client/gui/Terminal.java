package it.polimi.ingsw.GC_18.client.gui;

import java.util.Scanner;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.client.View;
import it.polimi.ingsw.GC_18.client.connection.ConnectionHandler;

/**
 * Handler of the console application interface.
 */
public class Terminal implements Runnable {

    /**
     * Waits for input from the user and sends it to the controller.
     */
    @Override
    public void run() {
        Scanner consoleIn = new Scanner(System.in);
        while (consoleIn.hasNextLine()) {
            handleInputFromUser(consoleIn.nextLine());
        }
        consoleIn.close();
    }

    /**
     * Parses the input from the user.
     * @param input - user's input
     */
    public static void handleInputFromUser(String input) {
        // parsing input from the server!
        String[] inputParts = input.split(" - ", 2);
        String command = inputParts[0].toUpperCase();
        String description = inputParts.length == 2 ? inputParts[1] : "";
        if ("LOGIN".equalsIgnoreCase(command)) {
            String[] userParts = description.split(" - ");
            if (userParts.length == 4) {
                Controller.login(userParts[0], userParts[1], "RMI".equalsIgnoreCase(userParts[2]),
                        "GUI".equalsIgnoreCase(userParts[3]));
            } else {
                MessageNotifier.notifyUser("INVALID LOGIN CREDENTIALS!");
            }
        } else if ("SET_USER_INTERFACE".equalsIgnoreCase(command)) {
            User.setUsingGuiInterface(!User.isUsingGuiInterface());
        } else if ("MENU".equalsIgnoreCase(command)) {
            View.enterMenu();
        } else if ("SHOW_STATS".equalsIgnoreCase(command)) {
            View.showStats();
        } else if ("FIND_GAME".equalsIgnoreCase(command)) {
            View.enterRoom(false);
        } else if ("SAVE".equalsIgnoreCase(command)) {
            Controller.save();
        } else if ("PLAYERS_STATUS".equalsIgnoreCase(command) || "BOARD_STATUS".equalsIgnoreCase(command) || "CHAT".equalsIgnoreCase(command) || "CHOOSE".equalsIgnoreCase(command) || "PLACE".equalsIgnoreCase(command) ||"LEADER".equalsIgnoreCase(command)) {
            ConnectionHandler.outputToServer(input);
        } else if ("PASS".equalsIgnoreCase(command)) {
            Controller.pass();
        } else if ("EXIT".equalsIgnoreCase(command)) {
            Controller.exit();
        } else if ("HELP".equalsIgnoreCase(command)) {
            Controller.help(description);
        } else {
            MessageNotifier.notifyUser("This is not a valid command");
        }
    }

}
