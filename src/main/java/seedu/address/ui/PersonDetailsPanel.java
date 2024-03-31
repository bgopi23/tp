package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private NumberAxis weightYAxis;
//    private LineChart<String, Number> heightChart;
//    private NumberAxis heightYAxis;
    private VBox notesBox;

    private static class HoveredThresholdNode extends StackPane {
        private final Label label = createDataThresholdLabel();
        private final Node point = createDataPoint();

        /**
         * Creates a new HoveredThresholdNode.
         */
        public HoveredThresholdNode(String value, String prefix, String postfix) {
            setPrefSize(10, 10);

            setOnMouseEntered(event -> {
                getChildren().setAll(point, label);
                label.setText(String.format("%s%s%s", prefix, value, postfix));
                toFront();
            });
            setOnMouseExited(event -> {
                getChildren().setAll(point);
            });

            setStyle("-fx-background-color: white; -fx-background-radius: 5px; -fx-padding: 2px;");
            getChildren().setAll(point);
        }

        private Node createDataPoint() {
            final Circle point = new Circle(5);
            point.setFill(Color.TRANSPARENT);
            point.setStroke(Color.TRANSPARENT);
            point.setStrokeWidth(0);
            return point;
        }

        private Label createDataThresholdLabel() {
            final Label label = new Label();
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }

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
        weightYAxis = new NumberAxis();

        xAxis.setAnimated(false); // fixes the collapsed categories bug
        xAxis.setLabel("Date");
        xAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");

        weightYAxis.setAnimated(false);
        weightYAxis.setAutoRanging(false);
        weightYAxis.setLabel("Weight (kg)");
        weightYAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");

        weightChart = new LineChart<>(xAxis, weightYAxis);
        weightChart.setAnimated(false);
        weightChart.setHorizontalGridLinesVisible(false);
        weightChart.setVerticalGridLinesVisible(false);
        weightChart.setTitle("Weight Tracking");
        weightChart.setLegendVisible(false);
        weightChart.lookup(".chart-title").setStyle("-fx-text-fill: white;");
        weightChart.setPrefHeight(200);
        weightChart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: white;");
        weightChart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: white;");
        weightChart.requestLayout();

        // Initialize height chart
//        CategoryAxis hxAxis = new CategoryAxis();
//        heightYAxis = new NumberAxis();
//
//        hxAxis.setAnimated(false);
//        hxAxis.setLabel("Date");
//        hxAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");
//
//        heightYAxis.setAnimated(false);
//        heightYAxis.setAutoRanging(false);
//        heightYAxis.setLabel("Height (cm)");
//        heightYAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");
//
//        heightChart = new LineChart<>(hxAxis, heightYAxis);
//        heightChart.setAnimated(false);
//        heightChart.setHorizontalGridLinesVisible(false);
//        heightChart.setVerticalGridLinesVisible(false);
//        heightChart.setTitle("Height Tracking");
//        heightChart.setLegendVisible(false);
//        heightChart.lookup(".chart-title").setStyle("-fx-text-fill: white;");
//        heightChart.setPrefHeight(200);
//        heightChart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: white;");
//        heightChart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: white;");
//        heightChart.requestLayout();

        // Initialize notes box
        notesBox = new VBox();
        notesBox.setSpacing(5);
        notesBox.setPrefHeight(200);

        // Create a scroll pane and set the notes box as its content
        ScrollPane notesScrollPane = new ScrollPane(notesBox);
        notesScrollPane.setFitToWidth(true);
        notesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Add charts and notes scroll pane to respective tabs
        Tab weightTab = trackableFieldsTabPane.getTabs().get(0);
        weightTab.setContent(weightChart);

//        Tab heightTab = trackableFieldsTabPane.getTabs().get(1);
//        heightTab.setContent(heightChart);

        Tab notesTab = trackableFieldsTabPane.getTabs().get(1);
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

        weightYAxis.setLowerBound(50);
        weightYAxis.setUpperBound(110);

        XYChart.Data<String, Number> weightData = new XYChart.Data<>("Jan 2024", 60);
        weightData.setNode(new HoveredThresholdNode("", "60", " kg"));
        weightSeries.getData().add(weightData);

        weightData = new XYChart.Data<>("Feb 2024", 100);
        weightData.setNode(new HoveredThresholdNode("", "100", " kg"));
        weightSeries.getData().add(weightData);

        weightData = new XYChart.Data<>("Mar 2024", 80);
        weightData.setNode(new HoveredThresholdNode("", "80", " kg"));
        weightSeries.getData().add(weightData);

        weightChart.getData().clear();
        weightChart.getData().add(weightSeries);

        // Set height
//        XYChart.Series<String, Number> heightSeries = new XYChart.Series<>();
//
//        heightYAxis.setLowerBound(160);
//        heightYAxis.setUpperBound(190);
//
//        XYChart.Data<String, Number> heightData = new XYChart.Data<>("Mar 2024", 170);
//        heightData.setNode(new HoveredThresholdNode("", "170", " cm"));
//        heightSeries.getData().add(heightData);
//
//        heightData = new XYChart.Data<>("Apr 2024", 175);
//        heightData.setNode(new HoveredThresholdNode("", "175", " cm"));
//        heightSeries.getData().add(heightData);
//
//        heightData = new XYChart.Data<>("May 2024", 178);
//        heightData.setNode(new HoveredThresholdNode("", "178", " cm"));
//        heightSeries.getData().add(heightData);
//
//        heightChart.getData().clear();
//        heightChart.getData().add(heightSeries);

        // Set notes
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yy HH:mm:ss");
//        Label noteLabel = new Label((LocalDateTime.now()).format(dateTimeFormatter) + " - " + "Random text");

        Label noteLabel1 = new Label("Burpees   -  Sets: 3, Reps: 10, Rest between sets: 2 min");
        Label noteLabel2 = new Label("Lunges    -   Sets: 5, Reps: 10, Rest between sets: 1 min");
        Label noteLabel3 = new Label("Squats    -   Sets: 5, Reps: 10, Rest between sets: 3 min");

        noteLabel1.setStyle("-fx-text-fill: white; -fx-font-size: 14");
        noteLabel2.setStyle("-fx-text-fill: white; -fx-font-size: 14");
        noteLabel3.setStyle("-fx-text-fill: white; -fx-font-size: 14");

        notesBox.getChildren().clear();
        notesBox.getChildren().addAll(noteLabel1, noteLabel2, noteLabel3);

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
