package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetAvatarCommand;
import seedu.address.model.person.Avatar;

public class SetAvatarCommandParserTest {
    private SetAvatarCommandParser parser = new SetAvatarCommandParser();

    @Test
    public void parse_indexSpecified_noParameters_success() throws Exception {
        final Avatar avatarNoParameters = new Avatar("");

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputEmptyParameter = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarNoParameters.path;

        SetAvatarCommand expectedCommandNoParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarNoParameters);
        assertParseSuccess(parser, userInputEmptyParameter, expectedCommandNoParameters);
    }

    @Test
    public void parse_indexSpecified_withParameters_success() throws Exception {
        final Avatar avatarWithParameters = new Avatar(VALID_AVATAR_IMAGE_URL_ONE);

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputWithParameters = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarWithParameters.path;

        SetAvatarCommand expectedCommandWithParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarWithParameters);
        assertParseSuccess(parser, userInputWithParameters, expectedCommandWithParameters);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAvatarCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, SetAvatarCommand.COMMAND_WORD, expectedMessage);
    }
}
