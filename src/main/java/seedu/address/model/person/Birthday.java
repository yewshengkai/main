package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday should be DD/MM/YYYY, only contain numbers, separated by '/'";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d{1,2}/\\d{1,2}/\\d{4}";

    public final String value;

    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
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
