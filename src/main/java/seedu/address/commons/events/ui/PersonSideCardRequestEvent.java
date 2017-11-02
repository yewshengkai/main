package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author yewshengkai
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
