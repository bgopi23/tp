package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exercise.Exercise;

/**
 * Jackson-friendly version of {@link Exercise}.
 */
class JsonAdaptedExercise {

    private final String name;
    private final Integer sets;
    private final Integer reps;
    private final Integer breakBetweenSets;

    /**
     * Constructs a {@code JsonAdaptedExercise} with the given {@code exerciseName}.
     */
    @JsonCreator
    public JsonAdaptedExercise(@JsonProperty("name") String name, @JsonProperty("sets") Integer sets,
                               @JsonProperty("reps") Integer reps,
                               @JsonProperty("breakBetweenSets") Integer breakBetweenSets) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.breakBetweenSets = breakBetweenSets;
    }

    /**
     * Converts a given {@code Exercise} into this class for Jackson use.
     */
    public JsonAdaptedExercise(Exercise source) {
        name = source.getName();
        sets = source.getSets();
        reps = source.getReps();
        breakBetweenSets = source.getBreakBetweenSets();
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
        if (!Exercise.isValidBreakBetweenSets(breakBetweenSets)) {
            throw new IllegalValueException(Exercise.BREAK_CONSTRAINT);
        }
        return new Exercise(name, sets, reps, breakBetweenSets);
    }

}
