package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Stores the names of persons returned by FindCommand.
 */
public class FindHistory {
    private LinkedList<ReadOnlyPerson> userFindHistory;

    public FindHistory() {
        userFindHistory = new LinkedList<>();
    }

    /**
     * Appends {@code personName} to the list of persons user found with FindCommand.
     */
    public void add(ReadOnlyPerson person) {
        requireNonNull(person);
        userFindHistory.remove(person); // ensures person is put in correct order
        userFindHistory.add(person);
    }

    public void set(ReadOnlyPerson person, ReadOnlyPerson newPerson) {
        if (userFindHistory.contains(person)) {
            userFindHistory.set(userFindHistory.indexOf(person), newPerson);
        }
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<ReadOnlyPerson> getHistory() {
        return new LinkedList<>(userFindHistory);
    }

}
