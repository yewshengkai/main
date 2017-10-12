package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;


/**
 * Panel containing all the buttons.
 */
public class ButtonsPanel extends UiPart<Region> {
    private static final String FXML = "ButtonsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ButtonsPanel.class);

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button refreshButton;

    public ButtonsPanel() {
        super(FXML);
        setButtonsImage();
        registerAsAnEventHandler(this);
    }

    public void setButtonsImage() {
        addButton.setGraphic(new ImageView("/images/buttons/addIcon.png"));
        deleteButton.setGraphic(new ImageView("/images/buttons/deleteIcon.png"));
        editButton.setGraphic(new ImageView("/images/buttons/editIcon.png"));
        clearButton.setGraphic(new ImageView("/images/buttons/clearIcon.png"));
        redoButton.setGraphic(new ImageView("/images/buttons/redoIcon.png"));
        undoButton.setGraphic(new ImageView("/images/buttons/undoIcon.png"));
        refreshButton.setGraphic(new ImageView("/images/buttons/refreshIcon.png"));
    }

}
