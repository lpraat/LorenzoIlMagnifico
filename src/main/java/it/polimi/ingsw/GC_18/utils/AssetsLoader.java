package it.polimi.ingsw.GC_18.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * This class handles the loading of game assets for the GUI
 */
public class AssetsLoader implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(AssetsLoader.class.getName());
    // array of supported extensions
    private static final String[] EXTENSIONS = new String[]{"gif", "png", "bmp","jpeg"};
    // filter to identify images based on their extensions
    private static final FilenameFilter IMAGE_FILTER = (File dir,String name)->{
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    };

    private static Map<String, BufferedImage> assets;
    private static boolean doneLoading;
    private File imagesDirectory;

    /**
     * Sets the path for the assets to load
     * @param dirPath- the path to the directory of the assets to load
     */
    public AssetsLoader(String dirPath) {
        this.imagesDirectory = new File(dirPath);
        assets = new HashMap<>();
    }

    @Override
    public void run() {
        loadImagesFromDir(imagesDirectory);
        doneLoading = true;
    }

    /**
     * Loads all images in the assets map that pass the filtering from a directory and all its sub directories.
     * @param dir - the base directory from which the search starts
     */
    private static void loadImagesFromDir(File dir) {
        if (dir==null || dir.listFiles()==null)
            return;
        for (File f : dir.listFiles()) {
            if (f==null)
                return;
            if (IMAGE_FILTER.accept(dir, f.getName())) {
                BufferedImage img = loadImage(f);
                if (img!=null) {
                    assets.put(f.getName(), img);
                }
            } else if (f.isDirectory()) {
                loadImagesFromDir(f);
            }
        }
    }

    /**
     * Loads and image from the specified path
     * @param path - the path at which the image is located
     * @return the image loaded
     */
    private static BufferedImage loadImage(File path) {
        if (path==null)
            return null;
        try {
            return ImageIO.read(path);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,"unable to load image at path=" + path,e);
        }
        return null;
    }

    /**
     * @return the assets loaded
     */
    public static Map<String, BufferedImage> getAssets() {
        return assets;
    }

    /**
     * @return whether or not the assets loading has finished
     */
    public static boolean isDoneLoading() {
        return doneLoading;
    }

}
