package seedu.address.ui;

import java.util.Comparator;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A UI component that displays the details of a {@code Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    public static final String FXML = "PersonDetailsPanel.fxml";

    @FXML
    private VBox detailsPane;
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
    private ImageView qrcode;
    @FXML
    private Label note;
    @FXML
    private TabPane trackableFieldsTabPane;

    private LineChart<String, Number> weightChart;
    private LineChart<String, Number> heightChart;
    private VBox notesBox;

    /**
     * Creates a new PersonDetailsPanel and clears all fields.
     * Labels in the {@code .FXML} file contain text with the field name for convenience.
     */
    public PersonDetailsPanel() {
        super(FXML);
        clear();
    }

    /**
     * Initializes a new PersonDetailsPanel.
     */
    public void initialize() {
        // Initialize weight chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        weightChart = new LineChart<>(xAxis, yAxis);
        weightChart.setTitle("Weight Tracking");
        weightChart.setLegendVisible(false);
        weightChart.lookup(".chart-title").setStyle("-fx-text-fill: white;");
        weightChart.setPrefHeight(200); // Set a specific height for the weight chart
        xAxis.setLabel("Date");
        xAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");
        yAxis.setLabel("Weight (kg)");
        yAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");

        // Initialize height chart
        CategoryAxis hxAxis = new CategoryAxis();
        NumberAxis hyAxis = new NumberAxis();
        heightChart = new LineChart<>(hxAxis, hyAxis);
        heightChart.setTitle("Height Tracking");
        heightChart.setLegendVisible(false);
        heightChart.lookup(".chart-title").setStyle("-fx-text-fill: white;");
        heightChart.setPrefHeight(200); // Set a specific height for the height chart
        hxAxis.setLabel("Date");
        hxAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");
        hyAxis.setLabel("Height (cm)");
        hyAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");

        // Initialize notes box
        notesBox = new VBox();
        notesBox.setSpacing(5);
        notesBox.setPrefHeight(200); // Set a specific height for the notes box
        notesBox.setStyle("-fx-background-color: white;"); // Set a background color for the notes box

        // Create a scroll pane and set the notes box as its content
        ScrollPane notesScrollPane = new ScrollPane(notesBox);
        notesScrollPane.setFitToWidth(true); // Enable horizontal scrolling if needed
        notesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scroll bar as needed

        // Add charts and notes scroll pane to respective tabs
        Tab weightTab = trackableFieldsTabPane.getTabs().get(0);
        weightTab.setContent(weightChart);

        Tab heightTab = trackableFieldsTabPane.getTabs().get(1);
        heightTab.setContent(heightChart);

        Tab notesTab = trackableFieldsTabPane.getTabs().get(2);
        notesTab.setContent(notesScrollPane);
    }

    /**
     * Set fields with information from the person.
     *
     * @param person the Person object containing the information to update the fields with.
     */
    public void update(Person person) {
        detailsPane.setVisible(true);

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

        note.setText(person.getNote().toString());
        qrcode.setImage(new Image(person.getQrCodePath().toUri().toString()));

        // Set trackable fields

        // Set weight
        XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();
//        for (Weight weight : client.getWeights()) {
//            weightSeries.getData().add(new XYChart.Data<>(weight.getDate().toString(), weight.getValue()));
//        }
        weightSeries.getData().add(new XYChart.Data<>("Jan 2023", 60));
        weightSeries.getData().add(new XYChart.Data<>("Feb 2023", 70));

        weightChart.getData().clear();
        weightChart.getData().add(weightSeries);

        // Set height
        XYChart.Series<String, Number> heightSeries = new XYChart.Series<>();
//        for (Height height : client.getHeights()) {
//            heightSeries.getData().add(new XYChart.Data<>(height.getDate().toString(), height.getValue()));
//        }
        heightSeries.getData().add(new XYChart.Data<>("Mar 2023", 170));
        heightSeries.getData().add(new XYChart.Data<>("Apr 2023", 180));

        heightChart.getData().clear();
        heightChart.getData().add(heightSeries);

        // Set notes
        notesBox.getChildren().clear();
//        for (Note note : client.getNotes()) {
//            Label noteLabel = new Label(note.getDate() + ": " + note.getText());
//            notesBox.getChildren().add(noteLabel);
//        }
        notesBox.getChildren().add(new Label(new Date() + ": " + "Random text"));

        // Bind manageability (presence) of node based on presence of value for optional fields
        address.setVisible(!person.getAddress().getValue().isEmpty());
        email.setVisible(!person.getEmail().getValue().isEmpty());
        note.setVisible(!person.getNote().getValue().isEmpty());

        address.managedProperty().bind(address.visibleProperty());
        email.managedProperty().bind(email.visibleProperty());
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
        tags.getChildren().clear();
        qrcode.setImage(null);
    }
}
