package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.utils.AssetsLoader;

/**
 * Abstract class that provides useful features for the GUI handling of the game GUI components,
 * like painting the background image, handling the images and the clicks of a game screen and
 *  the tool tips of the sub-components
 */
abstract class GameGuiComponent extends JPanel {

    private static final long serialVersionUID = 7801982898448314938L;

    private transient BufferedImage backgroundImage;
    transient ImagesHandler imageHandler;
    private String backgroundImageName;

    /**
     * Sets up the back ground image, adds the mouse listener, sets the tool tip manager
     * and sets up the image handler for a game screen
     * @param backgroundImageName - the name of the back ground image to retrieve from the asset loader
     */
    GameGuiComponent(String backgroundImageName) {
        ToolTipManager.sharedInstance().registerComponent(this);
        this.backgroundImageName = backgroundImageName;
        imageHandler = new ImagesHandler();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // thanks to this tooltip can open even on clicks.
                ToolTipManager.sharedInstance().mouseMoved(e);
                String component = imageHandler.getComponent(e.getPoint(), getScaleX(), getScaleY());
                if (component != null) {
                    if (SwingUtilities.isLeftMouseButton(e) && component.contains("leader") && imageHandler.getImagesByName().get(component).getImage() != null) {
                        LeaderAndResources leaderAndResources = (LeaderAndResources) e.getComponent();
                        if (leaderAndResources.getViewPlayer().equals(User.getUsername())) {
                            ActionSender.sendLeaderCommand(component, leaderAndResources);
                        }
                    } else {
                        ActionSender.sendPlaceCommand(component);
                    }
                }
            }
        });
        loadBackGround();
        fillImageHandler();
    }

    /**
     * Opens a new tooltip with left click on development cards and right click on leader cards.
     * Thanks to this the user can zoom cards for a better view of them.
     * @param event the mouse event.
     * @return the string representing the tooltip to show.
     */
    @Override
    public String getToolTipText(MouseEvent event) {
        String componentStr = imageHandler.getComponent(event.getPoint(), getScaleX(), getScaleY());
        Image image = imageHandler.getImagesByName().get(componentStr);
        if (image == null) {
            return super.getToolTipText(event);
        }
        if (image.getName() != null) {
            if ((event.getID() == MouseEvent.MOUSE_CLICKED && componentStr != null && (isACard(componentStr) && SwingUtilities.isLeftMouseButton(event))) 
                    || (event.getID() == MouseEvent.MOUSE_CLICKED && componentStr != null && (componentStr.contains("leader") && SwingUtilities.isRightMouseButton(event)))) {
                return "<html><img width=\"300\" height=\"400\" src=\"" + "file:" + "images/cards/" + image.getName() + "\">";
            } else {
                return super.getToolTipText(event);
            }
        }
        return  super.getToolTipText(event);
    }

    /**
     * Parses the name of a component to decide whether or not if it's a card
     * @param component - name of the component to parse
     * @return true if the component is the name of a card
     */
    private boolean isACard(String component) {
        return component.contains("floor") || component.contains("character") || component.contains("building") ||
                component.contains("venture") || component.contains("territory");
    }

    /**
     * Gets the back ground image from the assets loader
     */
    private void loadBackGround() {
        backgroundImage = AssetsLoader.getAssets().get(backgroundImageName);
    }

    /**
     * Sets up all the rectangles.
     */
    protected abstract void fillImageHandler();

    /**
     * Calculates the view port ratio of the x axis
     * @return the view port ratio for the x axis
     */
    double getScaleX() {
        return (double) getWidth() / backgroundImage.getWidth();
    }

    /**
     * Calculates the view port ratio of the y axis
     * @return the view port ratio for the y axis
     */
    double getScaleY() {
        return (double) getHeight() / backgroundImage.getHeight();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        imageHandler.print(g, getScaleX(), getScaleY());
    }

}
