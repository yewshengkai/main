package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class personContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private final String commandWord;

    public personContainsKeywordsPredicate(String commandWord, List<String> keywords) {
        this.keywords = keywords;
        this.commandWord = commandWord;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        switch (commandWord){
            case FindCommand.COMMAND_WORD:
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
            case FindCommand.COMMAND_WORD_ADDRESS:
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
            case FindCommand.COMMAND_WORD_EMAIL:
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
            case FindCommand.COMMAND_WORD_HOMEPAGE:
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getHomepage().value, keyword));
            case FindCommand.COMMAND_WORD_PHONE:
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
            case FindCommand.COMMAND_WORD_TAG:
                return keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTags().toString().replaceAll("['\\[\\]']",""), keyword));

        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof personContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((personContainsKeywordsPredicate) other).keywords)); // state check
    }

}
