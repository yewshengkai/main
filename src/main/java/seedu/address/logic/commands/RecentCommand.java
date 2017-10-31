package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all the persons searched by user from the start of app launch.
 */
public class RecentCommand extends Command {
    public static final String COMMAND_WORD = "recent";
    public static final String COMMAND_ALIAS = "rc";
    public static final String MESSAGE_SUCCESS = "Searched persons (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_NO_RECENT = "You have not yet searched for any persons.";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
