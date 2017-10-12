package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GroupsTest {

    @Test
    public void equals() {
        Groups Groups = new Groups("Hello");

        // same object -> returns true
        assertTrue(Groups.equals(Groups));

        // same values -> returns true
        Groups GroupsCopy = new Groups(Groups.value);
        assertTrue(Groups.equals(GroupsCopy));

        // different types -> returns false
        assertFalse(Groups.equals(1));

        // null -> returns false
        assertFalse(Groups.equals(null));

        // different person -> returns false
        Groups differentGroups = new Groups("Bye");
        assertFalse(Groups.equals(differentGroups));
    }
}
