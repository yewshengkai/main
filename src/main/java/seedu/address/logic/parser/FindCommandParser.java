package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEPAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.personContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_HOMEPAGE);

        try {
            if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_ADDRESS));
                return new FindCommand(new personContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_ADDRESS, keywordsList));
            } else if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_EMAIL));
                return new FindCommand(new personContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_EMAIL, keywordsList));
            } else if (arePrefixesPresent(argMultimap, PREFIX_HOMEPAGE)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_HOMEPAGE));
                return new FindCommand(new personContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_HOMEPAGE, keywordsList));
            } else if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_PHONE));
                return new FindCommand(new personContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_PHONE, keywordsList));
            } else if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_TAG));
                return new FindCommand(new personContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_TAG, keywordsList));
            } else if (arePrefixesPresent(argMultimap, PREFIX_NONE)) {
                String trimmedArgs = args.trim();
                if (trimmedArgs.isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                String[] keywords = null;
                keywords = trimmedArgs.toString().split("\\s+");
                return new FindCommand(new personContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD, Arrays.asList(keywords)));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
