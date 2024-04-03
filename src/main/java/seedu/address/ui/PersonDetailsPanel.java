package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.messages.WeightCommandMessages;
import seedu.address.model.exercise.Exercise;
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


    private Tab weightTab;
    private Tab exerciseTab;
    private LineChart<String, Number> weightChart;
    private NumberAxis weightYAxis;
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
        // Initialize tab pane
        trackableFieldsTabPane.setStyle("-fx-open-tab-animation: NONE; -fx-close-tab-animation: NONE;");

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
        exerciseScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        exerciseScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Add charts and notes scroll pane to respective tabs
        weightTab = getWeightTab();
        weightTab.setContent(weightChart);

        exerciseTab = getExerciseTab();
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
        if (latestWeight.isPresent()) {
            weightDate.setText(WeightCommandMessages.WEIGHT_DATE_HEADER
                + latestWeight.get().getKey().toString());
            weightValue.setText(WeightCommandMessages.WEIGHT_VALUE_HEADER
                + latestWeight.get().getValue().toString() + " kg");
        }

        height.setText(person.getHeight().getFormattedHeight());
        note.setText(person.getNote().toString());
        qrcode.setImage(new Image(person.getQrCodePath().toUri().toString()));

        // Clear tabs
        trackableFieldsTabPane.getTabs().clear();

        // Display weights graph
        if (latestWeight.isPresent()) {
            trackableFieldsTabPane.getTabs().add(0, weightTab);
            XYChart.Series<String, Number> weightSeries = generateWeightSeries(person);

            weightChart.getData().clear();
            weightChart.getData().add(weightSeries);
        }

        // Display exercises
        Label exercisesTitle = new Label("Exercises");
        exercisesTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        exercisesTitle.setMaxWidth(Double.MAX_VALUE);
        exercisesTitle.setAlignment(Pos.CENTER);

        exercisesBox.getChildren().clear();
        exercisesBox.getChildren().add(exercisesTitle);

        Set<Exercise> exercises = person.getExerciseSet().getValue();

        if (!exercises.isEmpty()) {
            trackableFieldsTabPane.getTabs().add(exerciseTab);

            List<Exercise> sortedExercises = exercises.stream()
                .sorted(Comparator.comparing(Exercise::getName))
                .collect(Collectors.toList());

            for (Exercise exercise : sortedExercises) {
                final String exerciseAttrDescStyle = "-fx-text-fill: white; -fx-font-size: 12px;";
                final String exerciseAttrValueStyle =
                    "-fx-background-color: #2E2E2E; -fx-padding: 2 5 2 5; -fx-text-fill: white; -fx-font-size: 12px;";

                Label exerciseName = new Label(StringUtil.capitalizeWords(exercise.getName()));

                exerciseName.setWrapText(true);
                exerciseName.setUnderline(true);
                exerciseName.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
                exerciseName.setPadding(new Insets(10, 0, 0, 0));

                Label setsLabel = new Label("Sets:");
                setsLabel.setStyle(exerciseAttrDescStyle);
                Label setsValue = new Label(String.valueOf(exercise.getSets()));
                setsValue.setStyle(exerciseAttrValueStyle);

                Label repsLabel = new Label("Reps:");
                repsLabel.setStyle(exerciseAttrDescStyle);
                Label repsValue = new Label(String.valueOf(exercise.getReps()));
                repsValue.setStyle(exerciseAttrValueStyle);

                Label breakLabel = new Label("Break between sets:");
                breakLabel.setStyle(exerciseAttrDescStyle);
                Label breakValue = new Label(exercise.getBreakBetweenSets() + " seconds");
                breakValue.setStyle(exerciseAttrValueStyle);

                HBox setsBox = new HBox(10, setsLabel, setsValue);
                HBox repsBox = new HBox(10, repsLabel, repsValue);
                HBox breakBox = new HBox(10, breakLabel, breakValue);

                setsBox.setPadding(new Insets(10, 0, 10, 0));
                repsBox.setPadding(new Insets(10, 0, 10, 0));
                breakBox.setPadding(new Insets(10, 0, 10, 0));

                setsBox.setPrefWidth(130);
                repsBox.setPrefWidth(130);
                breakBox.setPrefWidth(250);

                HBox exerciseBox = new HBox(setsBox, repsBox, breakBox);
                exercisesBox.getChildren().addAll(exerciseName, exerciseBox, new Separator());
            }
        }

        // Bind manageability (presence) of node based on presence of value for optional fields
        address.setVisible(!person.getAddress().getValue().isEmpty());
        email.setVisible(!person.getEmail().getValue().isEmpty());
        note.setVisible(!person.getNote().getValue().isEmpty());
        weightDate.setVisible(latestWeight.isPresent());
        weightValue.setVisible(latestWeight.isPresent());

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
        trackableFieldsTabPane.getTabs().clear();
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
