# yewshengkai
###### \java\seedu\address\commons\events\ui\ChangeThemeRequestEvent.java
``` java
/**
 * Indicates a request to jump to the list of themes
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final int targetIndex;

    public ChangeThemeRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\MapToListRequestEvent.java
``` java
/**
 * Indicates a request to jump to the list of persons
 */
public class MapToListRequestEvent extends BaseEvent {

    public final ReadOnlyPerson targetPerson;

    public MapToListRequestEvent(ReadOnlyPerson targetPerson) {
        this.targetPerson = targetPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\PersonSideCardRequestEvent.java
``` java
/**
 * Indicates a request to jump to the list of persons
 */
public class PersonSideCardRequestEvent extends BaseEvent {

    public final boolean isVisible;

    public PersonSideCardRequestEvent(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowAboutRequestEvent.java
``` java
/**
 * An event requesting to view the About page.
 */
public class ShowAboutRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word, boolean isCaseIgnored) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (isCaseIgnored && wordInSentence.toLowerCase().contains(preppedWord.toLowerCase())) {
                return true;
            } else if (!isCaseIgnored && wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\address\logic\commands\AboutCommand.java
``` java
/**
 * Format full help instructions for every command for display.
 */
public class AboutCommand extends Command {

    public static final String COMMAND_WORD = "about";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows iungo developers.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_ABOUT_MESSAGE = "Opened about window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowAboutRequestEvent());
        return new CommandResult(SHOWING_ABOUT_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
```
###### \java\seedu\address\logic\commands\GmapCommand.java
``` java
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class GmapCommand extends Command {

    public static final String COMMAND_WORD = "gmap";
    public static final String COMMAND_ALIAS = "gm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects and map the person address to Google Map identified by the index number used in the last "
            + "person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Mapping Person address: %1$s";

    private final Index targetIndex;

    public GmapCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new MapToListRequestEvent(lastShownList.get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GmapCommand // instanceof handles nulls
                && this.targetIndex.equals(((GmapCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to selected theme\n"
            + "1. No theme\n"
            + "2. Blue theme\n"
            + "3. Dark theme\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_THEME_SUCCESS = "Theme updated: %1$s";

    private static Region region;
    private final Index targetIndex;
    public ThemeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<String> themeList = new ArrayList<>();
        themeList.add("NoTheme");
        themeList.add("BlueTheme");
        themeList.add("DarkTheme");

        if (targetIndex.getZeroBased() >= themeList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
        }

        switch (targetIndex.getOneBased()) {
        case 1:
            setTheme(themeList.get(0));
            break;
        case 2:
            setTheme(themeList.get(1));
            break;
        case 3:
            setTheme(themeList.get(2));
            break;
        default:
            break;
        }
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.targetIndex.equals(((ThemeCommand) other).targetIndex)); // state check
    }

    public void setTheme(String args) throws CommandException {
        if (MainApp.class.getResource("/view/" + args + ".css") == null) {
            throw new CommandException(Messages.MESSAGE_UNKNOWN_FILEPATH);
        }
        region.getStylesheets().clear();
        region.getStylesheets().add("/view/" + args + ".css");

    }

    public static void setRegion(Region region) {
        ThemeCommand.region = region;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments, false);

        case FindCommand.COMMAND_WORD_ANY:
        case FindCommand.COMMAND_ALIAS_ANY:
            return new FindCommandParser().parse(arguments, true);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_ALIAS:
            return new ThemeCommandParser().parse(arguments);

        case GmapCommand.COMMAND_WORD:
        case GmapCommand.COMMAND_ALIAS:
            return new GmapCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args, boolean isCaseIgnored) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_HOMEPAGE);

        try {
            if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_ADDRESS),
                        FindCommand.COMMAND_WORD_ADDRESS);
                return new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_ADDRESS, keywordsList, isCaseIgnored));
            } else if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_EMAIL),
                        FindCommand.COMMAND_WORD_EMAIL);
                return new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_EMAIL, keywordsList, isCaseIgnored));
            } else if (arePrefixesPresent(argMultimap, PREFIX_HOMEPAGE)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_HOMEPAGE),
                        FindCommand.COMMAND_WORD_HOMEPAGE);
                return new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_HOMEPAGE, keywordsList, isCaseIgnored));
            } else if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_PHONE),
                        FindCommand.COMMAND_WORD_PHONE);
                return new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_PHONE, keywordsList, isCaseIgnored));
            } else if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
                ArrayList<String> keywordsList = ParserUtil.parseAllDetail(argMultimap.getAllValues(PREFIX_TAG),
                        FindCommand.COMMAND_WORD_TAG);
                return new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD_TAG, keywordsList, isCaseIgnored));
            } else if (arePrefixesPresent(argMultimap, PREFIX_NONE)) {
                String trimmedArgs = args.trim();
                if (trimmedArgs.isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                String[] keywords = trimmedArgs.split("\\s+");
                return new FindCommand(new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD, Arrays.asList(keywords), isCaseIgnored));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * For test case
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommand parse(String userInput) throws ParseException {
        return FindCommandParser.this.parse(userInput, true);
    }
```
###### \java\seedu\address\logic\parser\GmapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class GmapCommandParser implements Parser<GmapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GmapCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GmapCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns an ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ThemeCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson>  {
    private final List<String> keywords;
    private final String commandWord;
    private final boolean isCaseIgnored;

    public PersonContainsKeywordsPredicate(String commandWord, List<String> keywords, boolean isCaseIgnored) {
        this.keywords = keywords;
        this.commandWord = commandWord;
        this.isCaseIgnored = isCaseIgnored;
    }
    /**
     * Tests that a {@code ReadOnlyPerson}'s {@code commandWord} matches any of the keywords given.
     * Also check whether if the keyword is case-sensitive or insensitive
     */
    @Override
    public boolean test(ReadOnlyPerson person) {

        switch (commandWord) {

        case FindCommand.COMMAND_WORD:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getName().fullName, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_ADDRESS:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getAddress().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_EMAIL:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getEmail().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_HOMEPAGE:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getHomepage().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_PHONE:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getPhone().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_TAG:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getTags().toString()
                            .replaceAll("['\\[\\]']", ""), keyword, isCaseIgnored));

        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\AboutWindow.java
``` java
/**
 * Controller for a about page
 */
public class AboutWindow extends UiPart<Region> {

    public static final String USERGUIDE_FILE_PATH = "/docs/AboutUs.html";

    private static final Logger logger = LogsCenter.getLogger(AboutWindow.class);
    private static final String ICON = "/images/info_icon.png";
    private static final String FXML = "AboutWindow.fxml";
    private static final String TITLE = "About";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 650;

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public AboutWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        setAboutWindowSize();
        String userGuideUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(userGuideUrl);
    }

    private void setAboutWindowSize() {
        dialogStage.setMinHeight(MIN_HEIGHT);
        dialogStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Shows the about window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing about page.");
        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadPersonMap(ReadOnlyPerson targetPerson) {
        loadPage(GOOGLE_MAP_SEARCH_URL_PREFIX + targetPerson.getAddress().value
                .replaceAll(",", ""));
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelGmapChangedEvent(MapToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonMap(event.targetPerson);
        browser.setOpacity(100);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        personSideCard = new PersonSideCard();

        browserPlaceholder.getChildren().add(browserPanel.getRoot());
        sidePersonPlaceholder.getChildren().add(personSideCard.getRoot());
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the about window.
     */
    @FXML
    public void handleAbout() {
        AboutWindow aboutWindow = new AboutWindow();
        aboutWindow.show();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowAboutEvent(ShowAboutRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleAbout();
    }
}
```
###### \java\seedu\address\ui\PersonSideCard.java
``` java
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonSideCard extends UiPart<Region> {

    private static final String FXML = "PersonSideCard.fxml";
    private static String[] colors = { "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
    private final Logger logger = LogsCenter.getLogger(PersonSideCard.class);

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label homepage;
    @FXML
    private Label remark;
    @FXML
    private ImageView avatar;

    public PersonSideCard() {
        super(FXML);
        registerAsAnEventHandler(this);
        getRoot().setManaged(false);
        getRoot().setOpacity(0);

    }
```
###### \java\seedu\address\ui\PersonSideCard.java
``` java
    /**
     * Make PersonSideCard Panel visible or invisible to save a portion of GUi space for WebView
     */
    private void showSidePanel(boolean isVisible) {
        getRoot().setManaged(isVisible);
        int opacityLevel;
        if (isVisible) {
            opacityLevel =  100;
        } else {
            opacityLevel = 0;
        }
        getRoot().setOpacity(opacityLevel);
    }

    @Subscribe
     private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        bindListeners(event.getNewSelection().person);
    }

    @Subscribe
    private void handlePersonSideCardPanelChangedEvent(PersonSideCardRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showSidePanel(event.isVisible);
    }
}
```
###### \resources\view\BlueTheme.css
``` css
.background {
    -fx-background-color: derive(#dfe3ee, 20%);
    background-color: #dfe3ee; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #dfe3ee;
    -fx-control-inner-background: #dfe3ee;
    -fx-background-color: #dfe3ee;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI";
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: #3b5998;
}

.split-pane:horizontal .split-pane-divider {    /*Divider Color*/
    -fx-background-color: derive(#dfe3ee, 20%);
    -fx-border-color: transparent transparent transparent #f7f7f7;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#dfe3ee, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-background-color: #f7f7f7;
    -fx-padding: 0;
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 2);
    -fx-border-width: 1;
    -fx-border-radius:5px;
    -fx-background-radius:5px;

}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 5 0 0 0;
    -fx-background-color: derive(white, 20%);
    -fx-border-radius:5px;
    -fx-background-radius:5px;
}

.list-cell:filled:even {
    -fx-background-color: white;
    -fx-border-color: white white #dfe3ee white;
}

.list-cell:filled:odd {
    -fx-background-color: white;
    -fx-border-color: white white #dfe3ee white;;
}

.list-cell:filled:selected {
    -fx-background-color: #dfe3ee;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #dfe3ee;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 16px;
    -fx-text-fill: black;

}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: black;
}

.anchor-pane {
     -fx-background-color: derive(#dfe3ee, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#dfe3ee, 20%);
     -fx-border-color: derive(#dfe3ee, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#dfe3ee, 20%);
    -fx-text-fill: black;
}


.result-display {   /*Command result*/
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 2, 2);
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-size:9pt;
    -fx-font-family: "Segoe UI";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#dfe3ee, 30%);
    -fx-border-color: derive(#dfe3ee, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#dfe3ee, 30%);
    -fx-border-color: derive(#dfe3ee, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#dfe3ee, 30%);
}

.context-menu {
    -fx-background-color: derive(#dfe3ee, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#3b5998, 20%);
}

.menu-bar .label {
    -fx-font-size: 10pt;
    -fx-font-family: "Segoe UI";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
    -fx-font-weight:bold;
}

.menu .left-container {
    -fx-background-color: black;
}

.menu:hover {
    -fx-background-color: #8b9dc3;
}

.menu-item .label{
    -fx-text-fill: black;
    -fx-font-weight:normal;
}

.menu-item:hover {
    -fx-background-color: #8b9dc3;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: inherit;
    -fx-border-color: derive(grey,70%);
    -fx-border-width: 1;
    -fx-background-radius: 25px;
    -fx-background-color: #dfe3ee;
    -fx-font-family: "Segoe UI", Helvetica, Segoe UI, sans-serif;
    -fx-font-size: 10pt;
    -fx-text-fill: black;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
    -fx-border-radius: 25px;
}

.button:hover {
    -fx-background-color: #8b9dc3;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: #3b5998;
  -fx-text-fill: #dfe3ee;
}

.button:focused {
    -fx-border-color: #3b5998, #3b5998;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #dfe3ee;
    -fx-text-fill: #f7f7f7;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(#8b9dc3, 30%);
}

.dialog-pane {
    -fx-background-color: #dfe3ee;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #dfe3ee;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: #f7f7f7;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#dfe3ee, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: #f7f7f7;
    -fx-text-fill: #f7f7f7;
}

.scroll-bar {
    -fx-background-color: rgba(0,0,0,0.1);
}

.scroll-bar .thumb {
    -fx-background-color: rgba(0,0,0,0.3);
    -fx-background-insets: 0;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 2 1 2;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 2 1 2 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: #f7f7f7;
    -fx-background-insets: 0;
    -fx-border-color: #f7f7f7;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
    -fx-border-radius:5px;
    -fx-background-radius:5px;
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 2, 2);
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #f7f7f7, transparent, #f7f7f7;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: black;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#sidePersonPlaceholder {
    -fx-background-color: #ffffff;
    -fx-background-insets: 0;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-border-radius:5px;
    -fx-background-radius:5px;
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 1, 2);
}


#sidePersonPlaceholder .label{
    -fx-font-family: "Segoe UI";
}

```
###### \resources\view\DarkTheme.css
``` css

#sidePersonPlaceholder {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 0;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-border-radius:5px;
    -fx-background-radius:5px;
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 2, 2);
}

#sidePersonPlaceholder .label{
    -fx-font-family: "Segoe UI";
    -fx-text-fill:white;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
    <MenuBar fx:id="menuBar" minHeight="30.0" prefHeight="25.0" VBox.vgrow="NEVER">
        <Menu mnemonicParsing="false" text="File">
            <MenuItem mnemonicParsing="false" onAction="#handleImport" text="Import" />
            <MenuItem mnemonicParsing="false" onAction="#handleExport" text="Export" />
            <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
        </Menu>
        <Menu mnemonicParsing="false" text="Help">
            <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
            <MenuItem fx:id="aboutMenuItem" mnemonicParsing="false" onAction="#handleAbout" text="About iungo" />
        </Menu>
    </MenuBar>
```
###### \resources\view\MainWindow.fxml
``` fxml
                        <StackPane fx:id="sidePersonPlaceholder">
                           <HBox.margin>
                              <Insets bottom="10.0" left="5.0" top="10.0" />
                           </HBox.margin>
                        </StackPane>
```
###### \resources\view\NoTheme.css
``` css
#sidePersonPlaceholder {
    -fx-background-color: #ffffff;
    -fx-background-insets: 0;
    -fx-border-insets: 0;
    -fx-border-width: 1;
}

```
###### \resources\view\PersonSideCard.fxml
``` fxml
<GridPane alignment="CENTER" minWidth="280.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <columnConstraints>
      <ColumnConstraints fillWidth="false" />
   </columnConstraints>
   <rowConstraints>
      <RowConstraints fillHeight="false" />
   </rowConstraints>
   <children>
      <VBox alignment="CENTER" minWidth="200.0" prefWidth="200.0" GridPane.columnIndex="0">
         <children>
            <ImageView fx:id="avatar" fitHeight="90.0" fitWidth="90.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../images/default_avatar.png" />
               </image>
               <VBox.margin>
                  <Insets />
               </VBox.margin>
            </ImageView>
            <HBox alignment="TOP_CENTER">
               <opaqueInsets>
                  <Insets />
               </opaqueInsets>
               <VBox.margin>
                  <Insets bottom="1.0" />
               </VBox.margin>
               <children>
                  <Label fx:id="id" styleClass="cell_big_label">
                     <minWidth>
                        <Region fx:constant="USE_PREF_SIZE" />
                     </minWidth>
                  </Label>
                  <Label fx:id="name" styleClass="cell_big_label" text="\$first" textAlignment="CENTER" wrapText="true" />
               </children>
            </HBox>
            <FlowPane fx:id="tags" alignment="CENTER">
               <VBox.margin>
                  <Insets bottom="40.0" />
               </VBox.margin>
            </FlowPane>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/phone.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/address.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="address" styleClass="cell_small_label" text="\$address" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/email.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="email" styleClass="cell_small_label" text="\$email" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/homepage.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="homepage" styleClass="cell_small_label" text="\$homepage" underline="true" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
            <HBox alignment="CENTER_LEFT">
               <children>
                  <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="@../docs/images/remark.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" wrapText="true">
                     <HBox.margin>
                        <Insets left="20.0" />
                     </HBox.margin>
                  </Label>
               </children>
               <VBox.margin>
                  <Insets bottom="15.0" />
               </VBox.margin>
            </HBox>
         </children>
      </VBox>
   </children>
</GridPane>
```
