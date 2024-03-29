package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
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
    private Label weight;
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

        height.setText(person.getLatestHeight().getValue().getFormattedHeight());
        weight.setText(person.getWeight().getFormattedWeight());
        note.setText(person.getNote().toString());
        qrcode.setImage(new Image(person.getQrCodePath().toUri().toString()));

        // Bind manageability (presence) of node based on presence of value for optional fields
        address.setVisible(!person.getAddress().getValue().isEmpty());
        email.setVisible(!person.getEmail().getValue().isEmpty());
        height.setVisible(person.getLatestHeight().getValue().getValue() != 0f);
        weight.setVisible(person.getWeight().getValue() != 0f);
        note.setVisible(!person.getNote().getValue().isEmpty());

        address.managedProperty().bind(address.visibleProperty());
        email.managedProperty().bind(email.visibleProperty());
        height.managedProperty().bind(height.visibleProperty());
        weight.managedProperty().bind(weight.visibleProperty());
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
        weight.setText("");
        tags.getChildren().clear();
        qrcode.setImage(null);
    }
}
