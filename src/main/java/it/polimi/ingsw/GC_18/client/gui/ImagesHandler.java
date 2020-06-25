package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Handles the images of a screen, holding them in an Map.
 * Provides method to print the images,
 */
class ImagesHandler {

    private Map<String, Image> imagesByName;

    /**
     * Instantiates the map that holds all the images for this instance
     */
    ImagesHandler() {
        imagesByName = new HashMap<>();
    }


    /**
     * Prints a scaled image of all the images contains in the Map
     * @param g - the Graphics to use to draw
     * @param scaleX - the ratio for scaling the images on the x axis
     * @param scaleY - the ratio for scaling the images on the y axis
     */
    void print(Graphics g, double scaleX, double scaleY) {
        for (Image image : imagesByName.values()) {
            Rectangle scaledRect = scaleRectangle(image.getRectangle(), scaleX, scaleY);
            if (image.getImage() != null) {
                g.drawImage(image.getImage(), scaledRect.x, scaledRect.y, scaledRect.width, scaledRect.height, null);
            }
        }
    }

    /**
     * Scales a rectangle using the provided ratios
     * @param rect - the rectangle to scale
     * @param scaleX - the x scale ratio
     * @param scaleY - the y scale ratio
     * @return the scaled rectangle
     */
    private Rectangle scaleRectangle(Rectangle rect, double scaleX, double scaleY) {
        return new Rectangle((int) (rect.x * scaleX), (int) (rect.y * scaleY), (int) (rect.width * scaleX),
                (int) (rect.height * scaleY));
    }

    /**
     * Gets the name of the component at point if it exists in the imagesByName map.
     * @param point - a point where the component is expected to be located
     * @param scaleX - the scaling of the x
     * @param scaleY - the scaling of the y
     * @return null if no component is set to the location at pointClicked, else it returns the name of the place clicked.
     */
    String getComponent(Point point, double scaleX,double scaleY) {
        for (Entry<String,Image> entry:imagesByName.entrySet()) {
            if (scaleRectangle(entry.getValue().getRectangle(),scaleX,scaleY).contains(point)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @return the map containing the images
     */
    Map<String, Image> getImagesByName() {
        return imagesByName;
    }

}
