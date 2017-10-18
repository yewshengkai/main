package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.testutil.PersonBuilder;

public class personContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        personContainsKeywordsPredicate firstPredicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, firstPredicateKeywordList);
        personContainsKeywordsPredicate secondPredicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        personContainsKeywordsPredicate firstPredicateCopy = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        personContainsKeywordsPredicate predicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        personContainsKeywordsPredicate predicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
