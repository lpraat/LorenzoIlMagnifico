package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import it.polimi.ingsw.GC_18.utils.AssetsLoader;

/**
 * This class is a wrapper of the BufferedImage: it handles a buffered image, its name and the rectangle that contains it
 */
class Image {

    private String name;
    private BufferedImage bufferedImage;
    private Rectangle rectangle;

    /**
     * Instantiates an wrapper for a buffered image adding a name and the containing rectangle
     * @param name - the image name
     * @param image - the actual image
     * @param rectangle - the containing rectangle
     */
    Image(String name, BufferedImage image, Rectangle rectangle) {
        this.name = name;
        this.bufferedImage = image;
        this.rectangle = rectangle;
    }

    /**
     * @return the name of this Image
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this image
     * @param name -  the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the actual image
     */
    public BufferedImage getImage() {
        return bufferedImage;
    }

    /**
     * Sets the actual image
     * @param image - the image to set
     */
    public void setImage(String image) {
        this.bufferedImage = AssetsLoader.getAssets().get(image);
        this.name = image;
    }

    /**
     * @return the containing rectangle
     */
    Rectangle getRectangle() {
        return rectangle;
    }
}
