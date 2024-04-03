package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;

public class ExerciseSetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExerciseSet(null));
    }

    @Test
    public void isMatch() {
        Set<Exercise> exercises = new HashSet<>();
        exercises.add(new Exercise("Exercise 1", 1, 1, 0));
        exercises.add(new Exercise("Exercise 2", 2, 2, 30));
        ExerciseSet exerciseSet = new ExerciseSet(exercises);

        // exact match -> returns true
        assertTrue(exerciseSet.isMatch(exercises));

        // subset match -> returns true
        Set<Exercise> subsetExercises = new HashSet<>();
        subsetExercises.add(new Exercise("Exercise 1", 1, 1, 0));
        assertTrue(exerciseSet.isMatch(subsetExercises));

        // empty set -> returns true
        assertTrue(exerciseSet.isMatch(Collections.emptySet()));

        // superset mismatch -> returns false
        Set<Exercise> supersetExercises = new HashSet<>(exercises);
        supersetExercises.add(new Exercise("Exercise 3", 3, 3, 60));
        assertFalse(exerciseSet.isMatch(supersetExercises));

        // different type -> returns false
        assertFalse(exerciseSet.isMatch(5));
    }

    @Test
    public void equals() {
        Set<Exercise> exercises = new HashSet<>();
        exercises.add(new Exercise("Exercise 1", 1, 1, 0));
        exercises.add(new Exercise("Exercise 2", 2, 2, 30));
        ExerciseSet exerciseSet = new ExerciseSet(exercises);

        // same values -> returns true
        Set<Exercise> sameExercises = new HashSet<>();
        sameExercises.add(new Exercise("Exercise 1", 1, 1, 0));
        sameExercises.add(new Exercise("Exercise 2", 2, 2, 30));
        assertEquals(exerciseSet, new ExerciseSet(sameExercises));

        // same object -> returns true
        assertEquals(exerciseSet, exerciseSet);

        // null -> returns false
        assertNotEquals(null, exerciseSet);

        // different types -> returns false
        assertNotEquals(true, exerciseSet);

        // different exercises -> returns false
        Set<Exercise> differentExercises = new HashSet<>();
        differentExercises.add(new Exercise("Exercise 3", 3, 3, 60));
        assertNotEquals(exerciseSet, new ExerciseSet(differentExercises));
    }
}
