package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Set;

import seedu.address.model.person.Attribute;

/**
 * Represents a set of Exercises of a client.
 */
public class ExerciseSet extends Attribute<Set<Exercise>> {
    /**
     * Constructs an {@code ExerciseSet}.
     *
     * @param exercises Set of valid exercises.
     */
    public ExerciseSet(Set<Exercise> exercises) {
        super(exercises);
        requireNonNull(exercises);
    }

    /**
     * Returns an immutable exercise set, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    @Override
    public Set<Exercise> getValue() {
        return Collections.unmodifiableSet(super.getValue());
    }

    @Override
    public boolean isMatch(Object other) {
        // Check that the other is a Set
        if (!(other instanceof Set<?>)) {
            return false;
        }

        Set<?> otherSet = (Set<?>) other;

        if (otherSet.isEmpty()) {
            return true;
        }

        if (!(otherSet.iterator().next() instanceof Exercise)) {
            return false;
        }

        // Already checked that set contains Exercise objects, so it is safe to cast
        @SuppressWarnings("unchecked")
        Set<Exercise> otherExercises = (Set<Exercise>) otherSet;

        return otherExercises.stream().allMatch(this::contains);
    }

    /**
     * Determine if the exercise set contains the specified exercise.
     *
     * @param otherValue Exercise to check against
     * @return True if exercise set contains the specified exercise, false otherwise
     */
    public boolean contains(Object otherValue) {
        if (!(otherValue instanceof Exercise)) {
            return false;
        }

        Exercise other = (Exercise) otherValue;

        return this.getValue().stream().anyMatch(exercise -> exercise.equals(other));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExerciseSet)) {
            return false;
        }

        ExerciseSet otherExerciseSet = (ExerciseSet) other;
        return this.getValue().equals(otherExerciseSet.getValue());
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
