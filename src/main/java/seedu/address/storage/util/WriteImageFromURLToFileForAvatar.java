package seedu.address.storage.util;

import static seedu.address.model.person.Avatar.AVATAR_VALIDATION_PATH;
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
public class WriteImageFromURLToFileForAvatar {
    /**
     * Writes the image URL path provided into an image file
     */
    public static String writeImageToFile(String path) throws IllegalValueException {
        if (path.equals("") || path.startsWith(AVATAR_VALIDATION_PATH)) {
            return path;
        }
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);

            // Using hashCode() assures uniqueness of name of created file
            File file = new File(DEFAULT_AVATAR_FILE_LOCATION + path.hashCode() + ".png");
            ImageIO.write(image, "png", file);
            return file.getPath();
        } catch (IOException ioe) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
    }
}
