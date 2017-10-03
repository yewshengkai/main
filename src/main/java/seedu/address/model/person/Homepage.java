package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

public class Homepage {
    public static final String MESSAGE_HOMEPAGE_CONSTRAINTS =
            "Person homepage should be a valid URL";
    public static final String HOMEPAGE_VALIDATION_REGEX =
            "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
    public final String value;

    /**
     * Validates given homepage.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Homepage(String homepage) throws IllegalValueException {
        requireNonNull(homepage);
        String trimmedHomepage = homepage.trim();
        if (!isValidHomepage(trimmedHomepage)) {
            throw new IllegalValueException(MESSAGE_HOMEPAGE_CONSTRAINTS);
        }
        this.value = trimmedHomepage;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidHomepage(String test) {
        return test.matches(HOMEPAGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && this.value.equals(((Email) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
