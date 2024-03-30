package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents an Exercise in the address book.
 */
public class Exercise {

    public static final String NAME_CONSTRAINT = "Exercise names should not be empty";
    public static final String SETS_CONSTRAINT = "Number of sets should be a number greater than 0";
    public static final String REPS_CONSTRAINT = "Number of repetitions should be a number greater than 0";
    public static final String REST_CONSTRAINT = "Rest time in minutes should be a number greater or equals to 0";

    public static final Integer DEFAULT_SETS = 1;
    public static final Integer DEFAULT_REPS = 1;
    public static final Integer DEFAULT_REST = 0;


    private final String name;
    private final Integer sets;
    private final Integer reps;
    private final Integer rest;

    /**
     * Constructs an {@code Exercise}.
     *
     * @param name Name of the exercise.
     * @param sets Number of sets.
     * @param reps Number of repetitions per set.
     * @param rest Rest time in minutes between sets.
     */
    public Exercise(String name, Integer sets, Integer reps, Integer rest) {
        requireNonNull(name);
        requireNonNull(sets);
        requireNonNull(reps);
        requireNonNull(rest);

        checkArgument(isValidName(name), NAME_CONSTRAINT);
        checkArgument(isValidSets(sets), SETS_CONSTRAINT);
        checkArgument(isValidReps(reps), REPS_CONSTRAINT);
        checkArgument(isValidRest(rest), REST_CONSTRAINT);

        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.rest = rest;
    }

    /**
     * Returns true if the given string represents a valid exercise name.
     *
     * @param exerciseName The exercise name to be validated.
     * @return true if the exercise name is valid, false otherwise.
     */
    public static boolean isValidName(String exerciseName) {
        return !exerciseName.isEmpty();
    }

    /**
     * Returns true if the given Integer represents a valid number of sets.
     *
     * @param sets The number of sets to be validated.
     * @return true if the number of sets is valid, false otherwise.
     */
    public static boolean isValidSets(Integer sets) {
        return sets > 0;
    }

    /**
     * Returns true if the given Integer represents a valid number of reps.
     *
     * @param reps The number of reps to be validated.
     * @return true if the number of reps is valid, false otherwise.
     */
    public static boolean isValidReps(Integer reps) {
        return reps > 0;
    }

    /**
     * Returns true if the given Integer represents a valid rest time.
     *
     * @param rest The rest time to be validated.
     * @return true if the rest time is valid, false otherwise.
     */
    public static boolean isValidRest(Integer rest) {
        return rest >= 0;
    }

    /**
     * Returns the name of the exercise.
     *
     * @return The name of the exercise.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of sets for the exercise.
     *
     * @return The number of sets for the exercise.
     */
    public int getSets() {
        return sets;
    }

    /**
     * Returns the number of reps for the exercise.
     *
     * @return The number of reps for the exercise.
     */
    public int getReps() {
        return reps;
    }

    /**
     * Returns the rest time in minutes for the exercise.
     *
     * @return The rest time in minutes for the exercise.
     */
    public int getRest() {
        return rest;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Exercise)) {
            return false;
        }

        Exercise otherExercise = (Exercise) other;
        return name.equals(otherExercise.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s: %d sets of %d reps, %d min rest", name, sets, reps, rest);
    }
}
