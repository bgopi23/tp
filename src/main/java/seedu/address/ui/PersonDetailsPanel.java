package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.messages.WeightCommandMessages;
import seedu.address.model.person.Person;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.tag.Tag;

/**
 * A UI component that displays the details of a {@code Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    public static final String FXML = "PersonDetailsPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

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
    private Label weightDate;
    @FXML
    private Label weightValue;
    @FXML
    private Label height;
    @FXML
    private Label note;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView qrcode;
    @FXML
    private TabPane trackableFieldsTabPane;

    private LineChart<String, Number> weightChart;
    private NumberAxis weightYAxis;
    // private LineChart<String, Number> heightChart;
    // private NumberAxis heightYAxis;
    private VBox exercisesBox;

    /**
     * Creates a new PersonDetailsPanel and clears all fields.
     * Labels in the {@code .FXML} file contain text with the field name for convenience.
     */
    public PersonDetailsPanel() {
        super(FXML);
        clear();
    }

    private Tab getWeightTab() {
        return trackableFieldsTabPane.getTabs().get(0);
    }

    private Tab getExerciseTab() {
        return trackableFieldsTabPane.getTabs().get(1);
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

        // Initialize notes box
        exercisesBox = new VBox();
        exercisesBox.setPadding(new Insets(10, 10, 10, 10));
        exercisesBox.setPrefHeight(200);

        // Create a scroll pane and set the notes box as its content
        ScrollPane exerciseScrollPane = new ScrollPane(exercisesBox);
        exerciseScrollPane.setFitToWidth(true);
        exerciseScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Add charts and notes scroll pane to respective tabs
        Tab weightTab = getWeightTab();
        weightTab.setContent(weightChart);

        Tab exerciseTab = getExerciseTab();
        exerciseTab.setContent(exerciseScrollPane);
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

        Optional<Map.Entry<LocalDateTime, Weight>> latestWeight = person.getLatestWeight();
        if (latestWeight.isEmpty()) {
            weightDate.setText(WeightCommandMessages.EMPTY_FIELD_WEIGHT_DATE);
            weightValue.setText(WeightCommandMessages.EMPTY_FIELD_WEIGHT_VALUE);
        } else {
            weightDate.setText(WeightCommandMessages.WEIGHT_DATE_HEADER
                    + latestWeight.get().getKey().toString());
            weightValue.setText(WeightCommandMessages.WEIGHT_VALUE_HEADER
                    + latestWeight.get().getValue().toString() + " kg");
        }
        height.setText(person.getHeight().getFormattedHeight());
        note.setText(person.getNote().toString());
        qrcode.setImage(new Image(person.getQrCodePath().toUri().toString()));

        XYChart.Series<String, Number> weightSeries = generateWeightSeries(person);
        weightChart.getData().clear();
        weightChart.getData().add(weightSeries);

//        Label noteLabel1 = new Label("Burpees   -  Sets: 3, Reps: 10, Rest between sets: 2 min");
//        Label noteLabel2 = new Label("Lunges    -   Sets: 5, Reps: 10, Rest between sets: 1 min");
//        Label noteLabel3 = new Label("Squats    -   Sets: 5, Reps: 10, Rest between sets: 3 min");
//
//        noteLabel1.setStyle("-fx-text-fill: white; -fx-font-size: 14");
//        noteLabel2.setStyle("-fx-text-fill: white; -fx-font-size: 14");
//        noteLabel3.setStyle("-fx-text-fill: white; -fx-font-size: 14");
//
//        exercisesBox.getChildren().clear();
//        exercisesBox.getChildren().addAll(noteLabel1, noteLabel2, noteLabel3);

        // Bind manageability (presence) of node based on presence of value for optional fields
        address.setVisible(!person.getAddress().getValue().isEmpty());
        email.setVisible(!person.getEmail().getValue().isEmpty());
        note.setVisible(!person.getNote().getValue().isEmpty());
        weightDate.setVisible(latestWeight.isPresent());

        address.managedProperty().bind(address.visibleProperty());
        email.managedProperty().bind(email.visibleProperty());
        weightDate.managedProperty().bind(weightDate.visibleProperty());
        weightValue.managedProperty().bind(weightValue.visibleProperty());
        height.managedProperty().bind(height.visibleProperty());
        note.managedProperty().bind(note.visibleProperty());

        logger.info("Displayed details of person: " + person);
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
        weightDate.setText("");
        weightValue.setText("");
        height.setText("");
        tags.getChildren().clear();
        qrcode.setImage(null);
    }

    private XYChart.Series<String, Number> generateWeightSeries(Person p) {
        Float minWeight = Float.MAX_VALUE;
        Float maxWeight = Float.MIN_VALUE;

        XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();

        for (Map.Entry<LocalDateTime, Weight> entry : p.getWeights().entrySet()) {
            LocalDateTime date = entry.getKey();
            Weight weight = entry.getValue();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy");
            String dateString = date.format(formatter);
            Number weightNumber = weight.getValue();
            XYChart.Data<String, Number> weightData = new XYChart.Data<>(dateString, weightNumber);
            weightData.setNode(new HoveredThresholdNode(weightNumber.toString(), "", " kg"));
            weightSeries.getData().add(weightData);

            if (weight.getValue() < minWeight) {
                minWeight = weight.getValue();
            }
            if (weight.getValue() > maxWeight) {
                maxWeight = weight.getValue();
            }
        }

        weightYAxis.setLowerBound(minWeight - 10);
        weightYAxis.setUpperBound(maxWeight + 10);

        return weightSeries;
    }

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
}
