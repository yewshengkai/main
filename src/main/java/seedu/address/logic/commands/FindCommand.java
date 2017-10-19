package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_ADDRESS = "find a/";
    public static final String COMMAND_WORD_EMAIL = "find e/";
    public static final String COMMAND_WORD_HOMEPAGE = "find h/";
    public static final String COMMAND_WORD_PHONE = "find p/";
    public static final String COMMAND_WORD_TAG = "find t/";
    public static final String COMMAND_ALIAS = "f";

    public static final String COMMAND_WORD_ANY = "findany";
    public static final String COMMAND_ALIAS_ANY = "fa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose contacts contain any of "
            + "the specified keywords (non case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie\n"
            + "Example: " + COMMAND_WORD_ADDRESS + " kingstreet somerse 123456t\n"
            + "Example: " + COMMAND_WORD_EMAIL + " alice@mail.com alice gmail.com\n"
            + "Example: " + COMMAND_WORD_HOMEPAGE + " nus.com github 2103\n"
            + "Example: " + COMMAND_WORD_PHONE + " 91234567 81234567\n"
            + "Example: " + COMMAND_WORD_TAG + " friends family";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
