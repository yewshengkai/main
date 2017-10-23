package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class Avatar {


    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Image not found / image extension not supported! Only supports \"BMP\", \"GIF\", \"JPEG\", and \"PNG\"";
    public final String path;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given path string is invalid.
     */
    public Avatar(String path) throws IllegalValueException {
        requireNonNull(path);
        String trimmedPath = path.trim();
        if (!isValidPath(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        this.path = trimmedPath;
    }

    /**
     * Returns true if a given string is a valid image filepath.
     */
    public static boolean isValidPath(String path) {
        File file = new File(path);
        try {
            String filetype = Files.probeContentType(file.toPath()).split("/")[0];
            return filetype.equals("image");
        } catch (IOException ioe) {
            return false;
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
