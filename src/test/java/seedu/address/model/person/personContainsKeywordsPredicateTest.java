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
                FindCommand.COMMAND_WORD, firstPredicateKeywordList, false);
        personContainsKeywordsPredicate secondPredicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, secondPredicateKeywordList, false);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        personContainsKeywordsPredicate firstPredicateCopy = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, firstPredicateKeywordList, false);
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
                FindCommand.COMMAND_WORD, Collections.singletonList("Alice"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        personContainsKeywordsPredicate predicate2 = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_ADDRESS, Collections.singletonList("Jurong"), false);
        assertTrue(predicate2.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        personContainsKeywordsPredicate predicate3 = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_EMAIL, Collections.singletonList("alice@example.com"), false);
        assertTrue(predicate3.test(new PersonBuilder().withEmail("alice@example.com").build()));

        personContainsKeywordsPredicate predicate4 = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_PHONE, Collections.singletonList("85355255"), false);
        assertTrue(predicate4.test(new PersonBuilder().withPhone("85355255").build()));

        // Multiple keywords
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Alice", "Bob"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_ADDRESS, Arrays.asList("Jurong", "West"), false);
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        //@TODO: More test case to be added..
        // Only one matching keyword
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Bob", "Carol"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("aLIce", "bOB"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        personContainsKeywordsPredicate predicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Collections.emptyList(), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new personContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Carol"), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new personContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Arrays.asList("12345", "alice@email.com", "Main", "Street"), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
