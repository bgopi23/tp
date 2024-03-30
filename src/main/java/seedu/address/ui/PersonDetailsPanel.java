package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.height.Height;
import seedu.address.model.tag.Tag;

/**
 * A UI component that displays the details of a {@code Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    public static final String FXML = "PersonDetailsPanel.fxml";

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
    private Label height;
    @FXML
    private Label weightTemp;
    @FXML
    private Label note;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView qrcode;


    /**
     * Creates a new PersonDetailsPanel and clears all fields.
     * Labels in the {@code .FXML} file contain text with the field name for convenience.
     */
    public PersonDetailsPanel() {
        super(FXML);
        clear();
    }

    /**
     * Set fields with information from the person.
     *
     * @param person the Person object containing the information to update the fields with.
     */
    public void update(Person person) {
        // Set fields with information from the person
        name.setText(person.getName().toString());
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());

        // Clear tags and set new ones
        tags.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(Tag::toString))
                .forEach(tag -> tags.getChildren().add(new Label(tag.toString())));

        Optional<Map.Entry<LocalDateTime, Height>> latestHeight = person.getLatestHeight();
        height.setText(latestHeight.isPresent() ? latestHeight.get().getValue().getFormattedHeight() : "");
        weightTemp.setText(person.getWeightTemp().getFormattedWeightTemp());
        note.setText(person.getNote().toString());
        qrcode.setImage(new Image(person.getQrCodePath().toUri().toString()));

        // Bind manageability (presence) of node based on presence of value for optional fields
        address.setVisible(!person.getAddress().getValue().isEmpty());
        email.setVisible(!person.getEmail().getValue().isEmpty());
        height.setVisible(latestHeight.isPresent());
        weightTemp.setVisible(person.getWeightTemp().getValue() != 0f);
        note.setVisible(!person.getNote().getValue().isEmpty());

        address.managedProperty().bind(address.visibleProperty());
        email.managedProperty().bind(email.visibleProperty());
        height.managedProperty().bind(height.visibleProperty());
        weightTemp.managedProperty().bind(weightTemp.visibleProperty());
        note.managedProperty().bind(note.visibleProperty());
    }

    /**
     * Clears all fields
     */
    public void clear() {
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        note.setText("");
        height.setText("");
        weightTemp.setText("");
        tags.getChildren().clear();
        qrcode.setImage(null);
    }
}
