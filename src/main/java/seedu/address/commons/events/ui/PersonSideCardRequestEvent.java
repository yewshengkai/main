package seedu.address.commons.events.ui;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author yewshengkai
/**
 * Indicates a request to jump to the list of persons
 */
public class PersonSideCardRequestEvent extends BaseEvent {

    public final boolean isVisible;
    public final ReadOnlyPerson targetPerson;

    public PersonSideCardRequestEvent(boolean isVisible, ReadOnlyPerson targetPerson) {
        this.targetPerson = targetPerson;
        this.isVisible = isVisible;
    }

    public PersonSideCardRequestEvent(ReadOnlyPerson targetPerson) {
        this.isVisible = true;
        this.targetPerson = targetPerson;
    }

    public PersonSideCardRequestEvent(boolean isVisible) throws ParseException {
        this.isVisible = isVisible;
        this.targetPerson = null;

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
