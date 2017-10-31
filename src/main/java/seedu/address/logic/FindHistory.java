package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores the names of persons returned by FindCommand.
 */
public class FindHistory {
    private LinkedList<String> userFindHistory;

    public FindHistory() {
        userFindHistory = new LinkedList<>();
    }

    /**
     * Appends {@code personName} to the list of persons user found with FindCommand.
     */
    public void add(String personName) {
        requireNonNull(personName);
        if (userFindHistory.contains(personName)) {
            userFindHistory.remove(personName); // ensures person is put in correct order
        }
        userFindHistory.add(personName);
    }

    public void set(String personName, String personNewName) {
        requireNonNull(personNewName);
        if (userFindHistory.contains(personName)) {
            userFindHistory.set(userFindHistory.indexOf(personName), personNewName);
        }
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new LinkedList<>(userFindHistory);
    }

}
