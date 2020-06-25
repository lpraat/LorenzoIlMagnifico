package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import it.polimi.ingsw.GC_18.client.Main;
import it.polimi.ingsw.GC_18.client.connection.ConnectionHandler;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * A JPanel containing a chat that shows and prints the last few messages exchanged from clients or received from server.
 */
public class Chat extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(Chat.class.getName());
    private static final long serialVersionUID=4967978584944860624L;
    private int chatSize;//number of messages to be displayed
    private String[] messages=new String[chatSize];
    private JTextPane messagesPane;
    private JScrollPane messagesScrollPane;
    private JTextArea userInputTextArea;

    /**
     * Creates the panel associated to the chat with a nice background, an input form 
     * and a text area supporting HTML for incoming messages.
     */
    Chat() {
        chatSize=Integer.parseInt(Main.getClientProperties().getProperty("CHATSIZE"));
        messages=new String[chatSize];
        setBackground(new Color(238,238,238));//a nice grey background
        setBorder(BorderFactory.createTitledBorder("Room chat"));
        setLayout(new GridLayout(0,1));
        //setting the JTextArea for displaying messages
        messagesPane=new JTextPane();
        messagesPane.setContentType("text/html");
        messagesPane.setVisible(true);
        messagesPane.setFocusable(false);
        for (int i=0;i<messages.length;i++) {
            messages[i]=" ";
        }
        messagesScrollPane=new JScrollPane(messagesPane);
        messagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(messagesScrollPane);
        //setting the JTextArea for user input
        userInputTextArea=new JTextArea("");
        userInputTextArea.setEditable(true);
        userInputTextArea.enableInputMethods(true);
        userInputTextArea.setFocusable(true);
        userInputTextArea.setLineWrap(true);
        userInputTextArea.setWrapStyleWord(true);
        userInputTextArea.setBorder(BorderFactory.createEtchedBorder());
        //setting the enter shortcut to send messages
        KeyStroke keyStroke=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
        Object actionKey=userInputTextArea.getInputMap(JComponent.WHEN_FOCUSED).get(keyStroke);
        userInputTextArea.getActionMap().put(actionKey,new AbstractAction() {
            private static final long serialVersionUID=4481009016809909766L;
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    sendMessage(userInputTextArea.getText());
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "UNABLE TO SEND MSG", e);
                }
            }
        });
        JScrollPane userInputPane=new JScrollPane(userInputTextArea);
        add(userInputPane);
    }

    /**
     * Adds a message to the chat display
     * @param message - the message to add
     * @param log - true if the message is a log/comes from server. Used for special highlighting
     */
    private void addMessage(String message,boolean log) {
        System.out.println(message);
        for (int i=0;i<messages.length-1;i++) {
            messages[i]=messages[i+1];
        }
        messages[messages.length-1]=Utils.formatTextForChat(message,log);
        StringBuilder chat=new StringBuilder("<html>");
        for (int i=0;i<messages.length;i++) {
            chat.append(messages[i]).append("<br>");
        }
        chat.append("</html>");
        messagesPane.setText(chat.toString());
        messagesScrollPane.getVerticalScrollBar().setValue(messagesScrollPane.getVerticalScrollBar().getMaximum());
        repaint();
    }

    /**
     * Appends a message to the chat and notifies server of the action.
     * @param message - the message to send
     */
    private void sendMessage(String message) {
        String msg=message.trim();
        if ("".equals(msg))
            return;
        userInputTextArea.setText("");
        addMessage("YOU :"+msg,false);
        //sending action to server
        String action="CHAT - "+msg;
        ConnectionHandler.outputToServer(action);
    }

    /**
     * Adds the received message to the chat display
     * @param message - the message received
     * @param log - if the message is a log/comes from server. Used for special highlighting
     */
    public void receiveMessage(String message,boolean log) {
        if ("".equals(message))
            return;
        addMessage(message,log);
    }
    
    /**
     * @return the messages in the chat
     */
    public String[] getMessages() {
        return messages;
    }

}
