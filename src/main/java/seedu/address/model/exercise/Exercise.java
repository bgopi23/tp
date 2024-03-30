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
    public static final String REPS_CONSTRAINT = "Number of reps should be a number greater than 0";
    public static final String REST_CONSTRAINT = "Rest time in minutes should be a number greater or equals to 0";


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
        checkArgument(isValidSets(reps), REPS_CONSTRAINT);
        checkArgument(isValidSets(rest), REST_CONSTRAINT);

        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.rest = rest;
    }

    /**
     * Returns true if the given string represents a valid exercise name.
     */
    public static boolean isValidName(String exerciseName) {
        return !exerciseName.isEmpty();
    }

    /**
     * Returns true if the given Integer represents a valid number of sets.
     */
    public static boolean isValidSets(Integer sets) {
        return sets > 0;
    }

    /**
     * Returns true if the given Integer represents a valid number of reps.
     */
    public static boolean isValidReps(Integer reps) {
        return reps > 0;
    }

    /**
     * Returns true if the given Integer represents a valid rest time.
     */
    public static boolean isValidRest(Integer rest) {
        return rest >= 0;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

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
        return name.equals(otherExercise.getName())
            && sets == otherExercise.getSets()
            && reps == otherExercise.getReps()
            && rest == otherExercise.getRest();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sets, reps, rest);
    }

    @Override
    public String toString() {
        return String.format("%s: %d sets of %d reps, %d min rest", name, sets, reps, rest);
    }
}
