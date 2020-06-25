package it.polimi.ingsw.GC_18.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This interface contains useful general methods
 */
public interface Utils {

    /**
     * formats the text so that it displays nice on GUI. NOTE: it uses HTML.
     * @param message - the message to format
     * @param log - true if the message needs to be formatted to underline the
     * fact that it is a log message
     * @return the formatted message
     */
    static String formatTextForChat(String message, boolean log) {
        String[] textParts = message.split(":", 2);
        return log ? "<font color='red'>" + message + "</font>"
                : "<font color='blue'>" + textParts[0] + "</font>:" + textParts[1];
    }

    /**
     * Hashes the stringToHash
     * @param stringToHash - the string to hash
     * @return the parameter hashed
     */
    static String hash(String stringToHash) {
        String hashSalt = "LorenzoIlMagnifico";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringToHash.length(); i++)
            sb.append((char) (stringToHash.charAt(i) ^ hashSalt.charAt(i % hashSalt.length())));
        return sb.toString();
    }

    /**
     * loads the settings to some constants that must be configurable
     * @param path - the path to the properties
     * @return the properties.
     */
    static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            Logger.getLogger(Utils.class.getName()).log(Level.WARNING, "UNABLE TO LOAD PROPERTIES", e);
        }
        return properties;
    }

    /**
     * Returns the content of the file, located at the path passed as parameter,
     * as a string
     * @param path - the path to the file
     * @return the file as a string
     */
    static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try (FileReader fr = new FileReader(path)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            Logger.getLogger(Utils.class.getName()).log(Level.WARNING, "ERROR LOADING FILE AS A STRING", e);
        }
        return builder.toString();
    }

    /**
     * Appends a new line of text to a file if it exists, else it creates the file and all super directories
     * @param path - path to the file to append to
     * @param textToAppend - text to append in a new line to the file
     */
    static void appendToFile(String path, String textToAppend) {
        try {
            File file = new File(path);
            file.getParentFile().mkdirs();
            if (file.createNewFile()) {
                System.out.println("CREATED FILE "+path);
            }
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
                writer.println(textToAppend);
            }
        } catch (IOException e) {
            Logger.getLogger(Utils.class.getName()).log(Level.WARNING, "PROBLEM APPENDING TO FILE", e);
        }
    }

    /**
     * Overwrites the content of a file if it exists, else it creates the file and all super directories and writes to it
     * @param path - path to the file to write to
     * @param text - text to write to the file
     */
    static void overwriteFile(String path, String text) {
        File file = new File(path);
        try (FileWriter fooWriter = new FileWriter(file, false)) {
            fooWriter.write(text);
        } catch (IOException e) {
            Logger.getLogger(Utils.class.getName()).log(Level.WARNING, "PROBLEM WRITING TO FILE", e);
        }
    }


    /**
     * Draws the text in the center of the rectangle using the Graphics object g.
     * The font is ARIAL, bold, 32 and the color is BLACK
     * @param g - graphics object to use to draw the text
     * @param rectangle - rectangle in which the text needs to be written in the center
     * @param text - the text to write in the center
     */
    static void drawCenteredTextInRectangle(Graphics g, Rectangle rectangle, String text) {
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 32);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        double x = rectangle.x +(rectangle.getWidth() - fm.stringWidth(text)) / 2;
        double y = rectangle.y + (rectangle.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, (int) x, (int) y);
    }

    /**
     * Converts seconds to milliseconds
     * @param secondsToBeConverted - the seconds to convert
     * @return the parameter converted to milliseconds
     */
    static Long secondsToMills(long secondsToBeConverted) {
        return secondsToBeConverted * 1000L;
    }

}
