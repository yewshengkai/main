package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Groups in the address book.
 * Guarantees: immutable; is always valid
 */
public class Groups {

    public static final String MESSAGE_GROUPS_CONSTRAINTS =
            "Person Groups can take any values, can even be blank";

    public final String value;

    public Groups(String groups) {
        requireNonNull(groups);
        this.value = groups;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Groups // instanceof handles nulls
                && this.value.equals(((Groups) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
