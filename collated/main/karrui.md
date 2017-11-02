# karrui
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        if (updatedHomepage.value.equals(RESET_HOMEPAGE)) {
            return new Person(updatedName, updatedPhone, updatedEmail,
                    updatedAddress, updatedRemark, updatedAvatar, updatedTags);
        }

        if (personToEdit.isHomepageManuallySet() || !(originalHomepage.toString().equals(updatedHomepage.toString()))) {
            return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                    updatedRemark, updatedAvatar, updatedTags, updatedHomepage);
        } else {
            return new Person(updatedName, updatedPhone, updatedEmail,
                    updatedAddress, updatedRemark, updatedAvatar, updatedTags);
        }
```
###### \java\seedu\address\logic\commands\SetAvatarCommand.java
``` java
/**
 * Changes the avatar of an existing person in the address book
 */
public class SetAvatarCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "setavatar";
    public static final String COMMAND_ALIAS = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the avatar of the person identified by the index number used in the last person listing."
            + "The image will be sourced from the path URL parameter provided.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_AVATAR + "[URL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AVATAR + "http://www.examplepictures.com/image.jpg";

    public static final String MESSAGE_SET_AVATAR_SUCCESS = "Set Avatar for Person: %1$s";
    public static final String MESSAGE_REMOVE_AVATAR_SUCCESS = "Removed Avatar for Person: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_URL_INVALID = "The URL is invalid / is not an image."
            + " Avatar has been set to the default avatar";

    private final Index targetIndex;
    private final Avatar avatar;

    public SetAvatarCommand(Index index, Avatar avatar) {
        requireNonNull(index);
        requireNonNull(avatar);

        this.targetIndex = index;
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSetAvatar = lastShownList.get(targetIndex.getZeroBased());
        String personToSetAvatarPath = personToSetAvatar.getAvatar().path;
        Person editedPerson;

        if ("".equals(avatar.path) && !"".equals(personToSetAvatarPath)) { // delete image from storage
            ProcessImageFromUrlToFileForAvatar.removeImageFromStorage(personToSetAvatarPath);
        } else {
            if (!"".equals(personToSetAvatarPath)) {   // has a previously set avatar, remove first before processing
                ProcessImageFromUrlToFileForAvatar.removeImageFromStorage(personToSetAvatarPath);
            }
        }

        if (personToSetAvatar.isHomepageManuallySet()) {
            editedPerson = new Person(personToSetAvatar.getName(), personToSetAvatar.getPhone(),
                    personToSetAvatar.getEmail(), personToSetAvatar.getAddress(), personToSetAvatar.getRemark(),
                    avatar, personToSetAvatar.getTags(), personToSetAvatar.getHomepage());
        } else {
            editedPerson = new Person(personToSetAvatar.getName(), personToSetAvatar.getPhone(),
                    personToSetAvatar.getEmail(), personToSetAvatar.getAddress(), personToSetAvatar.getRemark(),
                    avatar, personToSetAvatar.getTags());
        }

        try {
            model.updatePerson(personToSetAvatar, editedPerson);
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
        if (!avatar.path.isEmpty()) {
            return String.format(MESSAGE_SET_AVATAR_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_REMOVE_AVATAR_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetAvatarCommand)) {
            return false;
        }

        // state check
        SetAvatarCommand e = (SetAvatarCommand) other;
        return targetIndex.equals(e.targetIndex)
                && avatar.equals(e.avatar);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts all persons in the address book by alphabetical order of contact's name and lists to the user.
 * The order can be either ascending or descending.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all persons in the address book as a list"
            + " sorted by ascending/descending alphabetical order of name."
            + "Command without parameters will be defaulted to ascending order."
            + "Optional Parameters: "
            + "[a] ASCENDING / [d] DESCENDING";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final boolean isDescendingSort;

    public SortCommand(boolean isDescendingSort) {
        this.isDescendingSort = isDescendingSort;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        model.sortContactList(isDescendingSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && (isDescendingSort == (((SortCommand) other).isDescendingSort)));
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            if (arePrefixesPresent(argMultimap, PREFIX_HOMEPAGE)) {
                Homepage homepage = ParserUtil.parseHomepage(argMultimap.getValue(PREFIX_HOMEPAGE)).get();
                person = new Person(name, phone, email, address, remark, avatar, tagList, homepage);
            } else {
                person = new Person(name, phone, email, address, remark, avatar, tagList);
            }
```
###### \java\seedu\address\logic\parser\SetAvatarCommandParser.java
``` java
/**
 * Parses the given {@code String} of arguments in the context of the SetAvatarCommand
 * and returns an SetAvatarCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class SetAvatarCommandParser implements Parser<SetAvatarCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetAvatarCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AVATAR);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAvatarCommand.MESSAGE_USAGE));
        }

        String avatar = argMultimap.getValue(PREFIX_AVATAR).orElse("");

        try {
            return new SetAvatarCommand(index, new Avatar(avatar));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            boolean isDescending = ParserUtil.parseSort(args);
            return new SortCommand(isDescending);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\person\Avatar.java
``` java
/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class Avatar {


    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Image not found/ image extension not supported! Only supports \"BMP\", \"GIF\", \"JPEG\", and \"PNG\"\n"
            + "Image might also be too big";
    public static final String MESSAGE_IMAGESIZE_CONSTRAINTS = "Image is too big! Please keep size to 10KB or lower";
    public static final String DEFAULT_AVATAR_FILE_LOCATION = "./data/avatar/";
    public final String path;
    public final String initialUrl;

    /**
     * Validates given avatar.
     * Invokes ProcessImageFromUrlToFileForAvatar for processing of storage of avatar
     * @throws IllegalValueException if given path string is invalid.
     */
    public Avatar(String path) throws IllegalValueException {
        requireNonNull(path);
        String trimmedPath = path.trim();
        if (!isValidPath(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        this.initialUrl = trimmedPath;
        this.path = ProcessImageFromUrlToFileForAvatar.writeImageToFile(trimmedPath);
    }

    /**
     * Returns true if a given string is a valid image filepath
     */
    public static boolean isValidPath(String path) {
        if ("".equals(path)) {  // default
            return true;
        }
        return isImageValid(path) && isImageCorrectSize(path);
    }

    /**
     * Returns true if image can be loaded from path
     */
    public static boolean isImageValid(String path) {
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            return image != null;
        } catch (IOException ioe) {
            // invalid URL, check if is file path
            try {
                BufferedImage image = ImageIO.read(new File(path));
                return image != null;
            } catch (IOException ioe2) {
                return false;
            }
        }
    }

    /**
     * Returns true if image is smaller than 20KB.
     * (This is because if the image is too big, the application will start slowing down)
     */
    public static boolean isImageCorrectSize(String path) {
        if ("".equals(path)) {  // default
            return true;
        }
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            // invalid URL, or is file path, check file size instead
            return ((new File(path).length()) / 1024) < 20;
        }
        int fileSize = getFileSize(url) / 1024;     // file size in KBs
        return fileSize < 20 && fileSize > 0;
    }

    /**
     * Obtains file size of image using a HEAD http request
     */
    private static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLength();
        } catch (IOException ioe) {
            return -1;
        } finally {
            assert conn != null;
            conn.disconnect();
        }
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.initialUrl.equals(((Avatar) other).initialUrl)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Homepage.java
``` java
/**
 * Represents a Person's homepage in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHomepage(String)}
 */
public class Homepage {
    public static final String MESSAGE_HOMEPAGE_CONSTRAINTS = "Person homepage should be a valid URL";
    public static final String RESET_HOMEPAGE = "404";
    public final String value;

    /**
     * Validates given homepage.
     *
     * @throws IllegalValueException if given homepage string is invalid.
     */
    public Homepage(String homepage) throws IllegalValueException {
        requireNonNull(homepage);
        String trimmedHomepage = homepage.trim();
        if (!isValidHomepage(trimmedHomepage)) {
            throw new IllegalValueException(MESSAGE_HOMEPAGE_CONSTRAINTS);
        }
        this.value = trimmedHomepage;
    }

    /**
     * For sole use by {@code ParserUtil} to reset homepage
     */
    public Homepage() {
        this.value = RESET_HOMEPAGE;
    }

    /**
     * Returns if a given string is a valid person homepage.
     */
    public static boolean isValidHomepage(String test) {
        try {
            new URL(test);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Homepage // instanceof handles nulls
                && this.value.equals(((Homepage) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\storage\AvatarStorage.java
``` java
/**
 * A class to access the avatar file directory path on the hard disk.
 */
public class AvatarStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String directoryPath;

    public AvatarStorage(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
        createDirectory(directoryPath);
    }

    /**
     * Creates a directory in specified path if directory does not exist
     */
    public void createDirectory(String directoryPath) {
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            logger.info("Failed to create directory at " + directoryPath);
        }
    }

    @Override
    public String toString() {
        return "Local avatar directory location : " + directoryPath;
    }
}
```
###### \java\seedu\address\storage\util\ProcessImageFromUrlToFileForAvatar.java
``` java
    /**
     * Writes the image URL path provided into an image file
     */
    public static String writeImageToFile(String path) throws IllegalValueException {
        String standardPath = path.replace('\\', '/');
        if ("".equals(standardPath) || standardPath.startsWith(DEFAULT_AVATAR_FILE_LOCATION)) {
            return standardPath;
        }
        try {
            int i = 1;
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);

            // Using hashCode() + checking if file exists assures uniqueness of name of created file
            File file = new File(DEFAULT_AVATAR_FILE_LOCATION + path.hashCode() + ".jpg");
            while (file.exists()) {
                file = new File(DEFAULT_AVATAR_FILE_LOCATION + (path.hashCode() + ++i) + ".jpg");
            }
            ImageIO.write(image, "jpg", file);
            return file.getPath().replace('\\', '/');
        } catch (IOException ioe) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
    }

    /**
     * Removes the image file currently in storage
     */
    public static void removeImageFromStorage(String path) {
        File file = new File(path);

        file.deleteOnExit();    // so as to allow undoable command
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
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
        }
    }
```
