package it.polimi.ingsw.GC_18.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.State;

/**
 * Panel for showing login input form and to send command to server.
 */
public class Login extends JPanel {

    private static final long serialVersionUID=9212270280536333923L;
    private static final String ACTIONS_THAT_CAN_BE_DONE="LOGIN - alphanumeric username - alphanumeric password - choose between rmi or sockets - choose between gui or console";
    private JTextField usernameTextField;
    private JPasswordField passwordField;

    /**
     * Sets up a panel for displaying an input form for getting user's name and password, the connection and the GUI types
     */
    public Login() {
        System.out.println("PLEASE LOG IN TO START PLAYING");
        Controller.setState(State.LOGGING_IN);
        GridBagLayout gridBagLayout=new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gbc=new GridBagConstraints();
        Insets insets=new Insets(5,10,5,10);
        JLabel lblUsername=new JLabel("Username:");
        gbc.anchor=GridBagConstraints.EAST;
        gbc.insets=insets;
        gbc.gridx=0;
        gbc.gridy=0;
        add(lblUsername,gbc);
        usernameTextField=new JTextField();
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=insets;
        gbc.gridx=1;
        gbc.gridy=0;
        add(usernameTextField,gbc);
        usernameTextField.setColumns(10);
        JLabel lblPassword=new JLabel("Password:");
        gbc.anchor=GridBagConstraints.EAST;
        gbc.insets=insets;
        gbc.gridx=0;
        gbc.gridy=1;
        add(lblPassword,gbc);
        passwordField=new JPasswordField();
        passwordField.setColumns(10);
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=insets;
        gbc.gridx=1;
        gbc.gridy=1;
        add(passwordField,gbc);
        JLabel lblConnectionType=new JLabel("SELECT CONNECTION TYPE:");
        gbc.gridwidth=2;
        gbc.insets=insets;
        gbc.gridx=0;
        gbc.gridy=2;
        add(lblConnectionType,gbc);
        JComboBox<String> connectionComboBox=new JComboBox<>();
        connectionComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"SOCKET","RMI"}));
        gbc.gridwidth=2;
        gbc.insets=insets;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=3;
        add(connectionComboBox,gbc);
        JLabel lblGuiType=new JLabel("SELECT INTERFACE:");
        gbc.insets=insets;
        gbc.gridwidth=2;
        gbc.gridx=0;
        gbc.gridy=4;
        add(lblGuiType,gbc);
        JComboBox<String> guiComboBox=new JComboBox<>();
        guiComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"GUI", "CONSOLE"}));
        gbc.insets=insets;
        gbc.gridwidth=2;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=5;
        add(guiComboBox,gbc);
        JButton btnLogin=new JButton("LOGIN!");
        gbc.gridwidth=2;
        gbc.insets=insets;
        gbc.gridx=0;
        gbc.gridy=6;
        add(btnLogin,gbc);
        btnLogin.setFocusable(false);
        btnLogin.addActionListener(e ->  Controller.login(usernameTextField.getText(),new String(passwordField.getPassword()),"RMI".equals(connectionComboBox.getSelectedItem()), "GUI".equals(guiComboBox.getSelectedItem())));
    }

    /**
     * @return a String that states the actions that a user can do from this state
     */
    public static String getActionsThatCanBeDone() {
        return ACTIONS_THAT_CAN_BE_DONE;
    }

}
