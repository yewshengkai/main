package seedu.address.storage.util;

import static seedu.address.model.person.Avatar.DEFAULT_AVATAR_FILE_LOCATION;
import static seedu.address.model.person.Avatar.MESSAGE_IMAGE_CONSTRAINTS;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Utility class to write an image from an URL to a file for the Avatar class
 */
public class ProcessImageFromUrlToFileForAvatar {
    public static final String MESSAGE_FILE_NOT_FOUND = "%s: no such" + " file or directory%n";
    private static String directoryPath = "data\\avatar\\";

    /**
     * Writes the image URL path provided into an image file
     */
    public static String writeImageToFile(String path) throws IllegalValueException {
        if (path.equals("") || path.startsWith(directoryPath)) {
            return path;
        }
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);

            // Using hashCode() assures uniqueness of name of created file
            File file = new File(DEFAULT_AVATAR_FILE_LOCATION + path.hashCode() + ".jpg");
            ImageIO.write(image, "jpg", file);
            return file.getPath();
        } catch (IOException ioe) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
    }

    /**
     * Removes the image file currently in storage
     */
    public static void removeImageFromStorage(String path) {
        File file = new File(path);

        file.deleteOnExit();    // so as to allow undoable command
    }
}
