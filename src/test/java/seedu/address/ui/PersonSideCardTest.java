package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonSideCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author yewshengkai-reused
public class PersonSideCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonSideCard personCard = new PersonSideCard();
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personCard = new PersonSideCard();
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTags, 2);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(personCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonSideCard personCard = new PersonSideCard();

        // same person, same index -> returns true
        PersonSideCard copy = new PersonSideCard();
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonSideCard()));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonSideCard()));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonSideCard personCard, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonSideCardHandle personSideCardHandle = new PersonSideCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personSideCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personSideCardHandle);
    }
}
