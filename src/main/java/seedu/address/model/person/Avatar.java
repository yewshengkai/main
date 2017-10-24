package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.storage.util.ProcessImageFromUrlToFileForAvatar;

/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class Avatar {


    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Image not found / image extension not supported! Only supports \"BMP\", \"GIF\", \"JPEG\", and \"PNG\"";
    public static final String MESSAGE_IMAGESIZE_CONSTRAINTS = "Image is too big! Please keep size to 10KB or lower";
    public static final String DEFAULT_AVATAR_FILE_LOCATION = "src\\\\main\\\\resources\\\\avatars\\\\";
    public static final String AVATAR_VALIDATION_PATH = "src\\main\\resources\\avatars";
    public final String path;

    /**
     * Validates given avatar.
     * Invokes ProcessImageFromUrlToFileForAvatar for processing of storage of avatar
     * @throws IllegalValueException if given path string is invalid.
     */
    public Avatar(String path) throws IllegalValueException {
        requireNonNull(path);
        String trimmedPath = path.trim();
        if (!isValidPath(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        if (!isImageCorrectSize(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_IMAGESIZE_CONSTRAINTS);
        }
        this.path = ProcessImageFromUrlToFileForAvatar.writeImageToFile(trimmedPath);
    }

    /**
     * Returns true if a given string is a valid image filepath
     */
    public static boolean isValidPath(String path) {
        if (path.equals("")) {  // default
            return true;
        }

        if (path.startsWith(AVATAR_VALIDATION_PATH)) {  // could have been put by user in directory
            try {
                BufferedImage image = ImageIO.read(new File(path));
                return image != null;
            } catch (IOException ioe) {
                return false;   // file invalid
            }
        }

        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            return image != null;
        } catch (IOException ioe) {
            return false;   // url invalid
        }
    }

    /**
     * Returns true if image is smaller than 20KB.
     * (This is because if the image is too big, the application will start slowing down)
     */
    public static boolean isImageCorrectSize(String path) {
        if (path.equals("") || path.startsWith(AVATAR_VALIDATION_PATH)) {  // default
            return true;
        }
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            return false;
        }
        int fileSize = getFileSize(url) / 1024;     // filesize in KBs
        return fileSize < 20;
    }

    /**
     * Obtains file size of image using a HEAD http request
     */
    private static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLength();
        } catch (IOException ioe) {
            return -1;
        } finally {
            assert conn != null;
            conn.disconnect();
        }
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.path.equals(((Avatar) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
