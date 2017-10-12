package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Groups;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Changes the group of an existing person in the address book.
 */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";
    public static final String COMMAND_ALIAS = "g";
    public static final String MESSAGE_SUCCESS = "Group success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the group of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing group will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_GROUPS + "[GROUP]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUPS + "Family";

    public static final String MESSAGE_ADD_GROUPS_SUCCESS = "Added groups to Person: %1$s";
    public static final String MESSAGE_DELETE_GROUPS_SUCCESS = "Removed groups from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Groups groups;

    /**
     * @param index  of the person in the filtered person list to edit the groups
     * @param groups of the person
     */
    public GroupCommand(Index index, Groups groups) {
        requireNonNull(index);
        requireNonNull(groups);

        this.index = index;
        this.groups = groups;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), groups, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        //model.updateFilteredListToShowAll();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Success message if group added or deleted
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!groups.value.isEmpty()) {
            return String.format(MESSAGE_ADD_GROUPS_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_GROUPS_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCommand)) {
            return false;
        }

        // state check
        GroupCommand e = (GroupCommand) other;
        return index.equals(e.index)
                && groups.equals(e.groups);
    }
}
