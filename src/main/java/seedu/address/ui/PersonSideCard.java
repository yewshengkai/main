package seedu.address.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonSideCardRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonSideCard extends UiPart<Region> {

    private static final String FXML = "PersonSideCard.fxml";
    private static String[] colors = { "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
    private final Logger logger = LogsCenter.getLogger(PersonSideCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
