# karrui
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
    @Test
    public void execute_person_homepageGeneratedSuccessful() {
        Person validPerson = new PersonBuilder().build();
        String defaultHomepage  = GOOGLE_SEARCH_URL_PREFIX + validPerson.getName()
                .fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX;
        String customHomepage = "http://www.google.com";
        Person validPersonWithHomepage = new PersonBuilder().withHomepage(customHomepage).build();

        assertEquals(defaultHomepage, validPerson.getHomepage().value);
        assertEquals(customHomepage, validPersonWithHomepage.getHomepage().value);
    }
```
###### \java\seedu\address\logic\commands\SetAvatarCommandTest.java
``` java
public class SetAvatarCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private ArrayList<String> filesCreated = new ArrayList<>();

    @After
    public void cleanup() {
        for (String path : filesCreated) {
            ProcessImageFromUrlToFileForAvatar.removeImageFromStorage(path);
        }
    }

    @Test
    public void execute_removeAvatar_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar("").build();

        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getAvatar().path);

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_REMOVE_AVATAR_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setAvatar_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();

        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getAvatar().path);

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_SET_AVATAR_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size()  +  1);
        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() throws IllegalValueException {
        final SetAvatarCommand standardCommand = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(standardCommand.getAvatar().path);

        // same values -> returns true (checks if initialURL is equal)
        SetAvatarCommand commandWithSameValues = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(commandWithSameValues.getAvatar().path);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        SetAvatarCommand commandWithDifferentIndex = new SetAvatarCommand(
                INDEX_SECOND_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(commandWithDifferentIndex.getAvatar().path);
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // different descriptor  -> returns false
        SetAvatarCommand commandWithDifferentDescriptor = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_TWO));
        filesCreated.add(commandWithDifferentDescriptor.getAvatar().path);
        assertFalse(standardCommand.equals(commandWithDifferentDescriptor));
    }

    /**
     +    * Returns a {@code SetAvatarCommand}.
     */
    private SetAvatarCommand prepareCommand(Index index, String path) throws IllegalValueException {
        SetAvatarCommand setAvatarCommand = new SetAvatarCommand(index, new Avatar(path));
        setAvatarCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        filesCreated.add(setAvatarCommand.getAvatar().path);
        return setAvatarCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    public static final boolean DESCENDING = true;
    public static final boolean ASCENDING = false;

    private Model model;

    @Test
    public void execute_decendingSort_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortCommand sortCommand = prepareCommand(DESCENDING);
        Model expectedModel = new ModelManager(getTypicalDescendingOrderAddressBook(), new UserPrefs());

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_ascendingSort_success() {
        model = new ModelManager(getTypicalDescendingOrderAddressBook(), new UserPrefs());
        SortCommand sortCommand = prepareCommand(ASCENDING);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    private SortCommand prepareCommand(boolean isDescendingSort) {
        SortCommand sortCommand = new SortCommand(isDescendingSort);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple homepages - last homepage accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HOMEPAGE_DESC_AMY + HOMEPAGE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid homepage
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_HOMEPAGE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                 Homepage.MESSAGE_HOMEPAGE_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " a") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " d") instanceof SortCommand);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_setAvatar() throws Exception {
        //setup
        ArrayList<String> filesCreated = new ArrayList<>();

        final Avatar avatar = new Avatar(VALID_AVATAR_IMAGE_URL_ONE);
        filesCreated.add(avatar.path);

        SetAvatarCommand command = (SetAvatarCommand) parser.parseCommand(SetAvatarCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_AVATAR + " " + avatar.initialUrl);
        filesCreated.add(command.getAvatar().path);

        SetAvatarCommand aliasCommand = (SetAvatarCommand) parser.parseCommand(SetAvatarCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_AVATAR + " " + avatar.initialUrl);
        filesCreated.add(aliasCommand.getAvatar().path);

        assertEquals(new SetAvatarCommand(INDEX_FIRST_PERSON, avatar), command);
        assertEquals(new SetAvatarCommand(INDEX_FIRST_PERSON, avatar), aliasCommand);

        // cleanup
        for (String path : filesCreated) {
            ProcessImageFromUrlToFileForAvatar.removeImageFromStorage(path);
        }
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // homepage
        userInput = targetIndex.getOneBased() + HOMEPAGE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withHomepage(VALID_HOMEPAGE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_resetHomepage_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + HOMEPAGE_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withHomepage("").build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\SetAvatarCommandParserTest.java
``` java
public class SetAvatarCommandParserTest {
    private SetAvatarCommandParser parser = new SetAvatarCommandParser();
    private ArrayList<String> filesCreated = new ArrayList<>();

    @After
    public void cleanup() {
        for (String path : filesCreated) {
            ProcessImageFromUrlToFileForAvatar.removeImageFromStorage(path);
        }
    }

    @Test
    public void parse_noParameters_success() throws Exception {
        final Avatar avatarNoParameters = new Avatar("");

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputEmptyParameter = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarNoParameters.path;

        SetAvatarCommand expectedCommandNoParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarNoParameters);
        assertParseSuccess(parser, userInputEmptyParameter, expectedCommandNoParameters);
    }

    @Test
    public void parse_withParameters_success() throws Exception {
        final Avatar avatarWithParameters = new Avatar(VALID_AVATAR_IMAGE_URL_ONE);

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputWithParameters = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarWithParameters.initialUrl;

        SetAvatarCommand expectedCommandWithParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarWithParameters);
        filesCreated.add(expectedCommandWithParameters.getAvatar().path);
        assertParseSuccess(parser, userInputWithParameters, expectedCommandWithParameters);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAvatarCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, SetAvatarCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "a", new SortCommand(ASCENDING));
        assertParseSuccess(parser, "d", new SortCommand(DESCENDING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "z", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
    @Test
    public void avatarCheck() {
        Person personWithNoAvatar = new PersonBuilder().withAvatar("").build();
        PersonCard personCard = new PersonCard(personWithNoAvatar, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoAvatar, 1);

        Person personWithAvatar = new PersonBuilder().withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();
        personCard = new PersonCard(personWithAvatar, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithAvatar, 1);
    }

```
