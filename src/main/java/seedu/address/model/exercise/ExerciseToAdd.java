package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents an exercise to be added to a client.
 */
public class ExerciseToAdd {
    private final String name;
    private final Optional<Integer> sets;
    private final Optional<Integer> reps;
    private final Optional<Integer> breakBetweenSets;

    /**
     * Constructs an {@code Exercise}.
     *
     * @param name Name of the exercise.
     * @param sets             Optional number of sets.
     * @param reps Optional number of repetitions per set.
     * @param breakBetweenSets Optional break time in seconds between sets.
     */
    public ExerciseToAdd(String name, Optional<Integer> sets, Optional<Integer> reps,
                         Optional<Integer> breakBetweenSets) {
        requireNonNull(name);
        requireNonNull(sets);
        requireNonNull(reps);
        requireNonNull(breakBetweenSets);

        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.breakBetweenSets = breakBetweenSets;
    }

    /**
     * Returns the optional name of the exercise to be added.
     *
     * @return The optional name of the exercise to be added.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the optional number of sets for the exercise to be added.
     *
     * @return The optional number of sets for the exercise to be added.
     */
    public Optional<Integer> getSets() {
        return this.sets;
    }

    /**
     * Returns the optional number of reps for the exercise to be added.
     *
     * @return The optional number of reps for the exercise to be added.
     */
    public Optional<Integer> getReps() {
        return this.reps;
    }

    /**
     * Returns the optional break time in seconds for the exercise to be added.
     *
     * @return The optional break time in seconds for the exercise to be added.
     */
    public Optional<Integer> getBreakBetweenSets() {
        return this.breakBetweenSets;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseToAdd)) {
            return false;
        }

        ExerciseToAdd otherExerciseToAdd = (ExerciseToAdd) other;
        return this.name.equals(otherExerciseToAdd.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
