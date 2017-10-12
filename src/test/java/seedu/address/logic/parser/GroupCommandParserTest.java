package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.model.person.Groups;

public class GroupCommandParserTest {
    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Groups groups = new Groups("Some groups.");

        // have groups
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()  + " "   + PREFIX_GROUPS.toString() +   " " +   groups;
        GroupCommand expectedCommand = new GroupCommand(INDEX_FIRST_PERSON, groups);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no groups
        userInput = targetIndex.getOneBased()   +  " "   +  PREFIX_GROUPS.toString();
        expectedCommand = new GroupCommand(INDEX_FIRST_PERSON, new Groups(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, GroupCommand.COMMAND_WORD, expectedMessage);
    }
}
