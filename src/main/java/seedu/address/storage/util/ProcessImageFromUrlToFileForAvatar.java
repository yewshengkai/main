package seedu.address.storage.util;

import static seedu.address.model.person.Avatar.DEFAULT_AVATAR_FILE_LOCATION;
import static seedu.address.model.person.Avatar.MESSAGE_IMAGE_CONSTRAINTS;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

//@@author karrui
/**
 * Utility class to write an image from an URL to a file for the Avatar class
 */
public class ProcessImageFromUrlToFileForAvatar {
    public static final String MESSAGE_FILE_NOT_FOUND = "%s: no such" + " file or directory%n";
    private static final Logger logger = LogsCenter.getLogger(ProcessImageFromUrlToFileForAvatar.class);

    /**
     * Writes the image URL path provided into an image file
     */
    public static String writeImageToFile(String path) throws IllegalValueException {
        String standardPath = path.replace('\\', '/');
        if ("".equals(standardPath) || standardPath.startsWith(DEFAULT_AVATAR_FILE_LOCATION)) {
            return standardPath;
        }
        try {
            int i = 1;
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);

            // Using hashCode() + checking if file exists assures uniqueness of name of created file
            File file = new File(DEFAULT_AVATAR_FILE_LOCATION + path.hashCode() + ".jpg");
            while (file.exists()) {
                file = new File(DEFAULT_AVATAR_FILE_LOCATION + (path.hashCode() + ++i) + ".jpg");
            }
            logger.fine("Attempting to write image to file: " + file.getName());
            ImageIO.write(image, "jpg", file);
            return file.getPath().replace('\\', '/');
        } catch (IOException ioe) {
            logger.info("Failed to create image from path: " + path);
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
    }

    /**
     * Removes the image file currently in storage
     */
    public static void removeImageFromStorage(String path) {
        File file = new File(path);
        logger.info("File at path: " + path + " will be deleted from disk on application exit");
        file.deleteOnExit();    // so as to allow undoable command
    }
}
