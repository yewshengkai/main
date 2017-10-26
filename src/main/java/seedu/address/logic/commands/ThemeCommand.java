package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

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
