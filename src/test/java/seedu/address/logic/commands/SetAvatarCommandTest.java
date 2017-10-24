package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SetAvatarCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeAvatar_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar("").build();

        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getAvatar().path);

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_REMOVE_AVATAR_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setAvatar_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();

        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getAvatar().path);

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_SET_AVATAR_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size()  +  1);
        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() throws IllegalValueException {
        final SetAvatarCommand standardCommand = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));

        // same values -> returns false (different file name should be created when duplicating)
        SetAvatarCommand commandWithSameValues = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        assertFalse(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SetAvatarCommand(
                INDEX_SECOND_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE))));

        // different descriptor  -> returns false
        assertFalse(standardCommand.equals(new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_TWO))));
    }

    /**
     +    * Returns a {@code SetAvatarCommand}.
     */
    private SetAvatarCommand prepareCommand(Index index, String path) throws IllegalValueException {
        SetAvatarCommand setAvatarCommand = new SetAvatarCommand(index, new Avatar(path));
        setAvatarCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setAvatarCommand;
    }
}
