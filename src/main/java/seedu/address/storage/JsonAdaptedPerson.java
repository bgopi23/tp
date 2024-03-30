package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedWeight> weights = new ArrayList<>();
    private final String height;
    private final String note;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedExercise> exercises = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("weights") List<JsonAdaptedWeight> weights,
                             @JsonProperty("height") String height,
                             @JsonProperty("note") String note, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("exercises") List<JsonAdaptedExercise> exercises) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (weights != null) {
            this.weights.addAll(weights);
        }
        this.height = height;
        this.note = note;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (exercises != null) {
            this.exercises.addAll(exercises);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().getValue();

        phone = source.getPhone().getValue();

        email = source.getEmail().getValue();

        address = source.getAddress().getValue();

        weights.addAll(source.getWeights().entrySet().stream()
            .map(WeightEntry::new)
            .map(JsonAdaptedWeight::new)
            .collect(Collectors.toList()));

        height = source.getHeight().getValue().toString();

        note = source.getNote().getValue();

        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));

        exercises.addAll(source.getExercises().getValue().stream()
            .map(JsonAdaptedExercise::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's
     * {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : this.tags) {
            personTags.add(tag.toModelType());
        }

        final List<WeightEntry> personWeights = new ArrayList<>();
        for (JsonAdaptedWeight weight : this.weights) {
            personWeights.add(weight.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!email.isEmpty() && !Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!address.isEmpty() && !Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final NavigableMap<LocalDateTime, Weight> modelWeights = new TreeMap<>();
        for (JsonAdaptedWeight jsonAdaptedWeight : weights) {
            WeightEntry weightEntry = jsonAdaptedWeight.toModelType();
            modelWeights.put(weightEntry.getValue().getKey(), weightEntry.getValue().getValue());
        }

        if (height == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!height.isEmpty() && !Height.isValidHeight(height)) {
            throw new IllegalValueException(Height.MESSAGE_CONSTRAINTS);
        }
        final Height modelHeight = new Height(Float.valueOf(height));

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Note modelNote = new Note(note);

        final List<Exercise> personExercises = new ArrayList<>();
        for (JsonAdaptedExercise exercise : exercises) {
            personExercises.add(exercise.toModelType());
        }
        final Set<Exercise> modelExercises = new HashSet<>(personExercises);
        final ExerciseSet modelExerciseSet = new ExerciseSet(modelExercises);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelWeights,
            modelHeight, modelNote, modelTags, modelExerciseSet);
    }

}
