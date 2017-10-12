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
    private Button editButton;

    @FXML
    private Button deleteButton;

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
        //addButton.setGraphic(new ImageView("/images/addIcon"));
       // deleteButton.setGraphic(new ImageView("/images/addIcon"));
        //editButton.setGraphic(new ImageView("/images/addIcon"));
        //clearButton.setGraphic(new ImageView("/images/addIcon"));
        //redoButton.setGraphic(new ImageView("/images/addIcon"));
        //undoButton.setGraphic(new ImageView("/images/addIcon"));
    }

}
