# yewshengkai-reused
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "re";
    public static final String MESSAGE_SUCCESS = "Remark success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Family";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index  of the person in the filtered person list to edit the remarks
     * @param remark of the person
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getAvatar(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Success message if remark added or deleted
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return new RemarkCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses the given {@code String} of arguments in the context of the RemarkCommand
 * and returns an RemarkCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }
}
```
###### \java\seedu\address\model\person\Remark.java
``` java
import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Groups in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remark can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Open a file
     */
    @FXML
    private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import from...");
        fileChooser.showOpenDialog(primaryStage);
    }

    /**
     * Save file to specific format
     */
    @FXML
    private void handleExport() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML Data (*.xml)", "*.xml");
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(excelFilter);
        fileChooser.getExtensionFilters().add(pdfFilter);
        fileChooser.getExtensionFilters().add(xmlFilter);
        fileChooser.getExtensionFilters().add(textFilter);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Export to..");
        fileChooser.setInitialFileName("iungoAB");
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            String content = "Prep_data_save_to_excel";
            try {
                FileWriter fileWriter = null;
                fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        }
    }
```
###### \java\seedu\address\ui\PersonSideCard.java
``` java
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
        homepage.textProperty().bind(Bindings.convert(person.homepageProperty()));

        initImage(person);
    }

    /**
     * Binds the correct image to the person.
     * If url is "", default display picture will be assigned, else image from URL will be assigned
     */
    private void initImage(ReadOnlyPerson person) {
        String path = person.getAvatar().toString();
        Image image;
        if (!"".equals(path)) {   // not default image
            File file = new File(path);
            image = new Image(file.toURI().toString());
            avatar.setImage(image);
            avatar.setFitHeight(90);
            avatar.setPreserveRatio(true);
            avatar.setCache(true);
        } else {
            image = new Image("images/default_avatar.png");
            avatar.setImage(image);
            avatar.setFitHeight(90);
            avatar.setPreserveRatio(true);
            avatar.setCache(true);
        }
    }

    /**
     * Tags coloring
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
