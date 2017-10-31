package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.FindHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Lists all the persons searched by user from the start of app launch.
 */
public class RecentCommand extends Command {
    public static final String COMMAND_WORD = "recent";
    public static final String COMMAND_ALIAS = "rc";
    public static final String MESSAGE_SUCCESS = "Searched persons (from most recent to earliest) listed :\n%1$s";
    public static final String MESSAGE_NO_RECENT = "You have not yet searched for any persons.";



    @Override
    public CommandResult execute() {
        List<String> previousFinds = findHistory.getHistory();

        if (previousFinds.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RECENT);
        }

        Collections.reverse(previousFinds);

        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(FindCommand.COMMAND_WORD, previousFinds, true);
        requireNonNull(predicate);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
        //return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousFinds)));
    }

    @Override
    public void setData(Model model, CommandHistory history, FindHistory findHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(findHistory);
        this.findHistory = findHistory;
    }
}
