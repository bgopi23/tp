package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents an Exercise in the address book.
 */
public class Exercise {

    public static final String NAME_CONSTRAINT = "Exercise name should not be empty";
    public static final String SETS_CONSTRAINT = "Number of sets should be a number greater than 0";
    public static final String REPS_CONSTRAINT = "Number of repetitions should be a number greater than 0";
    public static final String BREAK_CONSTRAINT =
        "Break time in seconds between sets should be a number greater or equals to 0";

    public static final Integer DEFAULT_SETS = 1;
    public static final Integer DEFAULT_REPS = 1;
    public static final Integer DEFAULT_BREAK = 0;


    private final String name;
    private final Integer sets;
    private final Integer reps;
    private final Integer breakBetweenSets;

    /**
     * Constructs an {@code Exercise}.
     *
     * @param name             Name of the exercise.
     * @param sets             Number of sets.
     * @param reps             Number of repetitions per set.
     * @param breakBetweenSets Break time in seconds between sets.
     */
    public Exercise(String name, Integer sets, Integer reps, Integer breakBetweenSets) {
        requireNonNull(name);
        requireNonNull(sets);
        requireNonNull(reps);
        requireNonNull(breakBetweenSets);

        checkArgument(isValidName(name), NAME_CONSTRAINT);
        checkArgument(isValidSets(sets), SETS_CONSTRAINT);
        checkArgument(isValidReps(reps), REPS_CONSTRAINT);
        checkArgument(isValidBreakBetweenSets(breakBetweenSets), BREAK_CONSTRAINT);

        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.breakBetweenSets = breakBetweenSets;
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
     * Returns true if the given Integer represents a valid break time between sets.
     *
     * @param breakBetweenSets The break time to be validated.
     * @return true if the break time is valid, false otherwise.
     */
    public static boolean isValidBreakBetweenSets(Integer breakBetweenSets) {
        return breakBetweenSets >= 0;
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
     * Returns the break time in seconds for the exercise.
     *
     * @return The break time in seconds for the exercise.
     */
    public int getBreakBetweenSets() {
        return breakBetweenSets;
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
        return String.format("%s: %d sets of %d reps, %d min break between sets", name, sets, reps, breakBetweenSets);
    }
}
