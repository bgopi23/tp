package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exercise.Exercise;

/**
 * Jackson-friendly version of {@link Exercise}.
 */
class JsonAdaptedExercise {

    private final String name;
    private final Integer sets;
    private final Integer reps;
    private final Integer rest;

    /**
     * Constructs a {@code JsonAdaptedExercise} with the given {@code exerciseName}.
     */
    @JsonCreator
    public JsonAdaptedExercise(@JsonProperty("name") String name, @JsonProperty("sets") Integer sets,
                               @JsonProperty("reps") Integer reps, @JsonProperty("rest") Integer rest) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.rest = rest;
    }

    /**
     * Converts a given {@code Exercise} into this class for Jackson use.
     */
    public JsonAdaptedExercise(Exercise source) {
        name = source.getName();
        sets = source.getSets();
        reps = source.getReps();
        rest = source.getRest();
    }

    /**
     * Converts this Jackson-friendly adapted exercise object into the model's
     * {@code Exercise} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted exercise.
     */
    public Exercise toModelType() throws IllegalValueException {
        if (!Exercise.isValidName(name)) {
            throw new IllegalValueException(Exercise.NAME_CONSTRAINT);
        }
        if (!Exercise.isValidSets(sets)) {
            throw new IllegalValueException(Exercise.SETS_CONSTRAINT);
        }
        if (!Exercise.isValidReps(reps)) {
            throw new IllegalValueException(Exercise.REPS_CONSTRAINT);
        }
        if (!Exercise.isValidRest(rest)) {
            throw new IllegalValueException(Exercise.REST_CONSTRAINT);
        }
        return new Exercise(name, sets, reps, rest);
    }

}
