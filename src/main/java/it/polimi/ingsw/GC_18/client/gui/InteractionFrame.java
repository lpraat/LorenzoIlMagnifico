package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.client.connection.ConnectionHandler;
import it.polimi.ingsw.GC_18.utils.AssetsLoader;

/**
 * Class that handles the interaction with the user in the choices that he has to make during the game
 */
public class InteractionFrame extends JFrame {

    private static final long serialVersionUID = 8252157765248226346L;
    private static final String ICON_URL = "/GC_18/resources/gameAssets/lorenzo_materiale_grafico_compr/Lorenzo_Leaders_compressed/leaders_b_c_00.jpg";
    private static final int WIDTH = 480;
    private static final List<InteractionFrame> interactionFrames = new ArrayList<>();

    /**
     * Sets up a JFrame that is used during the course of the game to display
     * the action choices to the user.
     * @param helpText - the text to help the user with the choice
     * @param choices - the choices from which the user can choose
     * @param usesLeaderImages - true if the choices have leader images bounded to them
     */
    InteractionFrame(String helpText, String[] choices, boolean usesLeaderImages, String additionalCommand) {
        super("DO YOUR CHOICE");
        System.out.println(helpText + "\n" + String.join("\n", choices));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(ICON_URL).getImage());
        setFocusable(false);
        setVisible(User.isUsingGuiInterface());
        addButtonContent(helpText, choices, usesLeaderImages, additionalCommand);
        pack();
        setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
        setLocationRelativeTo(null);
        toFront();
        interactionFrames.add(this);
    }

    /**
     * Displays a frame that allows the user to pick an integer between MIN and
     * MAX providing the hint of help text.
     * @param helpText - hint for the choice
     * @param min - minimum value accepted
     * @param max - maximum value accepted
     */
    private InteractionFrame(String helpText, int min, int max, String additionalCommand) {
        super("DO YOUR CHOICE");
        System.out.println(helpText + "\nValues accepted in range [" + min + "," + max + "]");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(ICON_URL).getImage());
        setFocusable(false);
        setVisible(User.isUsingGuiInterface());
        addIntegerPicker(helpText, min, max, additionalCommand);
        pack();
        setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
        setLocationRelativeTo(null);
        toFront();
        interactionFrames.add(this);
    }

    /**
     * Sets up a JFrame that is used during the course of the game to display
     * the action choices to the user.
     * @param helpText - the text to help the user with the choice
     * @param choices - the choices from which the user can choose
     * @param min - minimum value accepted
     * @param max - maximum value accepted
     * @param usesImages - true if the choices have images bounded to them
     */
    InteractionFrame(String helpText, String[] choices, int min, int max, boolean usesImages, String additionalCommand) {
        super("DO YOUR CHOICE");
        System.out.println(helpText + "\n" + String.join("\n", choices) + "\nValues accepted in range [" + min + "," + max + "]");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(ICON_URL).getImage());
        setFocusable(false);
        setVisible(User.isUsingGuiInterface());
        addButtonAndIntPickerContent(helpText, choices, min, max, usesImages, additionalCommand);
        pack();
        setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
        setLocationRelativeTo(null);
        toFront();
        interactionFrames.add(this);
    }

    /**
     * Adds the radio buttons for the choices
     * @param choices - the choices a user can take
     * @param usesImages - true if images needs to be displayed
     * @param helpText the help text.
     */
    private void addButtonContent(String helpText, String[] choices, boolean usesImages, String additionalCommand) {
        // creating panel for holding content
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel pickLabel = new JLabel(helpText);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        panel.add(pickLabel,gbc);
        // setting radio buttons
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] optionButtons = new JRadioButton[choices.length];
        for (int i = 0; i < choices.length; i++) {
            String choice = choices[i];
            if (usesImages) {
                choice = choice.replaceAll("\\d+->", "");// replacing digits+"->"
                optionButtons[i] = new JRadioButton(choice);
                JLabel avatar = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(choice+".png")).getImage().getScaledInstance(70, 120, 0)));
                avatar.setPreferredSize(new Dimension(70, 120));
                gbc.gridx=1;
                gbc.gridy=i+1;
                gbc.gridwidth=1;
                panel.add(avatar,gbc);
            } else {
                optionButtons[i] = new JRadioButton(choice);
            }
            optionButtons[i].setActionCommand(choice);
            if (i == 0)
                optionButtons[i].setSelected(true);
            group.add(optionButtons[i]);
            gbc.gridx=0;
            gbc.gridy=i+1;
            gbc.gridwidth=1;
            panel.add(optionButtons[i],gbc);
        }
        // adding pick button
        JButton pickButton = new JButton("PICK");
        pickButton.addActionListener(e -> {
            if (additionalCommand == null) {
                if (usesImages) {
                    ConnectionHandler.outputToServer("CHOOSE - " + (getSelectedButtonIndex(optionButtons)+1));
                } else {
                    ConnectionHandler.outputToServer("CHOOSE - " + getSelectedButtonText(group));
                }
            } else {
                ConnectionHandler.outputToServer(additionalCommand + " - " + getSelectedButtonText(group));
            }
            interactionFrames.remove(this);
            dispose();

        });
        gbc.gridx=0;
        gbc.gridy=choices.length+2;
        gbc.gridwidth=2;
        panel.add(pickButton,gbc);
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
        revalidate();
    }

    /**
     * Adds an integer picker form for letting the user pick 
     * a value ranged between MIN and MAX
     * @param helpText -  hint text for helping the user deciding in the choice
     * @param min - minimum value allowed
     * @param max - maximum value allowed
     */
    private void addIntegerPicker(String helpText, int min, int max, String additionalCommand) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // adding help text
        panel.add(new JLabel(helpText));

        // adding integer input form
        JTextField inputForm = new JTextField(String.valueOf(min));
        inputForm.setEditable(true);
        panel.add(inputForm);

        // adding submit button
        JButton pickButton = new JButton("PICK");
        pickButton.addActionListener(a -> {
            try {
                int input = Integer.parseInt(inputForm.getText());
                if (input >= min && input <= max) {
                    if (additionalCommand == null) {
                        ConnectionHandler.outputToServer("CHOOSE - " + input);
                    } else {
                        ConnectionHandler.outputToServer(additionalCommand + " - " + input);
                    }
                    interactionFrames.remove(this);
                    dispose();
                } else {
                    MessageNotifier.notifyUser("Invalid input");
                }
            } catch (NumberFormatException e) {
                MessageNotifier.notifyUser("Invalid input");
            }
        });
        panel.add(pickButton);

        getContentPane().add(panel);
        revalidate();
    }

    /**
     * Adds an integer picker form for letting the user pick 
     * a value ranged between MIN and MAX and a radio button for each choice
     * @param helpText -  hint text for helping the user deciding in the choice
     * @param choices - the choices from which the user can choose
     * @param min - minimum value allowed
     * @param max - maximum value allowed
     * @param usesImages - true if images needs to be displayed
     * @param additionalCommand - command to be added to the choice done
     */
    private void addButtonAndIntPickerContent(String helpText, String[] choices, int min, int max, boolean usesImages, String additionalCommand) {
        // creating panel for holding the radio buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel pickLabel = new JLabel(helpText);
        panel.add(pickLabel);

        // setting radio buttons
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] optionButtons = new JRadioButton[choices.length];
        for (int i = 0; i < choices.length; i++) {
            String choice = choices[i];
            if (usesImages) {
                optionButtons[i] = new JRadioButton(choice, new ImageIcon(AssetsLoader.getAssets().get(choice)));
            } else {
                optionButtons[i] = new JRadioButton(choice);
            }
            optionButtons[i].setActionCommand(choice);
            if (i == 0)
                optionButtons[i].setSelected(true);
            group.add(optionButtons[i]);
            panel.add(optionButtons[i]);
        }

        // adding integer input form
        JTextField inputForm = new JTextField(String.valueOf(min));
        inputForm.setEditable(true);
        panel.add(inputForm);

        // adding submit button
        JButton pickButton = new JButton("PICK");
        pickButton.addActionListener(a -> {
            try {
                String response = (additionalCommand == null ? "CHOOSE - " : additionalCommand + " - ") + optionButtons[getSelectedButtonIndex(optionButtons)].getText();
                if (!"SKIP".equalsIgnoreCase(optionButtons[getSelectedButtonIndex(optionButtons)].getText())) {
                    int input = Integer.parseInt(inputForm.getText());
                    if (input >= min && input <= max) {
                        response += " - "+input;
                    } else {
                        MessageNotifier.notifyUser("Invalid input");
                        return;
                    }
                }
                ConnectionHandler.outputToServer(response);
                interactionFrames.remove(this);
                dispose();
            } catch (NumberFormatException e) {
                MessageNotifier.notifyUser("Invalid input");
            }
        });
        panel.add(pickButton);

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
        revalidate();
    }

    /**
     * Disposes any interaction frame that has been opened
     */
    public static void closeFrames() {
        for (InteractionFrame frame : interactionFrames) {
            frame.dispose();
        }
        interactionFrames.clear();
    }

    /**
     * Parses the parameter for choosing the right kind of frame to show.
     * @param description - the input received from server that has to be parsed.
     */
    public static void handleChoice(String description) {
        String[] descriptionParts = description.split(" - ", 2);
        String type = descriptionParts[0];
        if ("STRING".equalsIgnoreCase(type)) {
            String[] choicesParts = descriptionParts[1].split(" - ", 2);
            new InteractionFrame(choicesParts[0], choicesParts[1].split(" - "), false, null);
        } else if ("LEADER".equalsIgnoreCase(type)) {
            String[] choicesParts = descriptionParts[1].split(" - ", 2);
            new InteractionFrame(choicesParts[0], choicesParts[1].split(" - "), true, null);
        } else if ("INT".equalsIgnoreCase(type)) {
            String[] choicesParts = descriptionParts[1].split(" - ", 3);
            new InteractionFrame(choicesParts[0], Integer.parseInt(choicesParts[1]), Integer.parseInt(choicesParts[2]), null);
        } else if ("EXTRA_PICK".equalsIgnoreCase(type)) {
            String[] choicesParts = descriptionParts[1].split(" - ", 4);
            new InteractionFrame(choicesParts[0], choicesParts[3].split(" - "), Integer.parseInt(choicesParts[1]), Integer.parseInt(choicesParts[2]), false, null);
        }
    }

    /**
     * Retrieves the text associated to the selected button in the group
     * @param buttonGroup - the JRadioButtons group from which the selected element is retrieved 
     * @return the text associated to the selected button
     */
    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    /**
     * Search and returns the index of the first selected button in the parameter list
     * @param buttons - the buttons from which the selected item must be retrieved 
     * @return the index of the first button that is selected, -1 if there's no button selected
     */
    private int getSelectedButtonIndex(JRadioButton[] buttons) {
        for (int i=0;i<buttons.length;i++) {
            if (buttons[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

}
