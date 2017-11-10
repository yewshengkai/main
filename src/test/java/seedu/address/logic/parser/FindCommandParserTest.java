package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Homepage;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_HOMEPAGE = "http://www.google.com";
    private static final String VALID_TAG_1 = "friend";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@author yewshengkai
    @Test
    @OverridingMethodsMustInvokeSuper
    public void parse_validArgs_returnsFindCommand() throws IllegalValueException {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD, Arrays.asList("Alice", "Bob"), false));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        FindCommand expectedAddressFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_ADDRESS, Arrays.asList("wall", "michegan"), false));
        assertParseSuccess(parser, "wall michegan", expectedAddressFindCommand);

        FindCommand expectedEmailFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_EMAIL, Arrays.asList(
                                "heinz@example.com", "werner@example.com"), false));
        assertParseSuccess(parser, "heinz@example.com werner@example.com", expectedEmailFindCommand);

        FindCommand expectedPhoneFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_PHONE, Arrays.asList("95352563", "9482224"), false));
        assertParseSuccess(parser, "95352563 9482224", expectedPhoneFindCommand);

        Collection<String> keywordsList = new ArrayList<String>();
        keywordsList.add(("Bob").toLowerCase());
        ArrayList<String> arrayList = ParserUtil.parseAllDetail(keywordsList, FindCommand.COMMAND_WORD_ANY);
        FindCommand expectedPerson = new FindCommand(
                new PersonContainsKeywordsPredicate(FindCommand.COMMAND_WORD_ANY, arrayList, false));
        assertParseSuccess(parser, "bob", expectedPerson);

        //@@author
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
        assertParseSuccess(parser, " \n wall \n \t michegan  \t", expectedAddressFindCommand);
        assertParseSuccess(parser, " \n heinz@example.com \n \t werner@example.com  \t", expectedEmailFindCommand);
        assertParseSuccess(parser, " \n 95352563 \n \t 9482224  \t", expectedPhoneFindCommand);
    }

    //@@author yewshengkai
    @Test
    public void parse_parseAllDetails() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        ArrayList<String> addressList = ParserUtil.parseAllDetail(
                Arrays.asList(VALID_ADDRESS), FindCommand.COMMAND_WORD_ADDRESS);
        assertEquals(expectedAddress.value, addressList.toString().replaceAll(
                "['\\[\\],']", ""));
        ParserUtil.parseAllDetail(addressList, FindCommand.COMMAND_WORD_ADDRESS);

        Email expectedEmail = new Email(VALID_EMAIL);
        ArrayList<String> emailList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_EMAIL), FindCommand.COMMAND_WORD_EMAIL);
        assertEquals(expectedEmail.value, emailList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(emailList, FindCommand.COMMAND_WORD_EMAIL);

        //@@author karrui
        Homepage expectedHomepage = new Homepage(VALID_HOMEPAGE);
        ArrayList<String> homepageList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_HOMEPAGE), FindCommand.COMMAND_WORD_HOMEPAGE);
        assertEquals(expectedHomepage.value, homepageList.toString().replaceAll(
                "['\\[\\]']", ""));

        //@@author yewshengkai
        ParserUtil.parseAllDetail(homepageList, FindCommand.COMMAND_WORD_HOMEPAGE);
        Phone expectedPhone = new Phone(VALID_PHONE);
        ArrayList<String> phoneList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_PHONE), FindCommand.COMMAND_WORD_PHONE);
        assertEquals(expectedPhone.value, phoneList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(phoneList, FindCommand.COMMAND_WORD_PHONE);

        Tag expectedTag = new Tag(VALID_TAG_1);
        ArrayList<String> tagList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_TAG_1), FindCommand.COMMAND_WORD_TAG);
        assertEquals(expectedTag.tagName, tagList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(tagList, FindCommand.COMMAND_WORD_TAG);
    }
}
