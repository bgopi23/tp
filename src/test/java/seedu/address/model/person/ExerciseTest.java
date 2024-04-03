package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.exercise.Exercise;

public class ExerciseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Exercise(null, null, null, null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Exercise("", 1, 1, 0));
    }

    @Test
    public void constructor_invalidSets_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Exercise("Exercise", 0, 1, 0));
    }

    @Test
    public void constructor_invalidReps_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Exercise("Exercise", 1, 0, 0));
    }

    @Test
    public void constructor_invalidBreak_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Exercise("Exercise", 1, 1, -1));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Exercise.isValidName(null));

        // invalid name
        assertFalse(Exercise.isValidName("")); // empty string

        // valid name
        assertTrue(Exercise.isValidName("Push Ups"));
        assertTrue(Exercise.isValidName("Bench Press"));
        assertTrue(Exercise.isValidName("push ups"));
        assertTrue(Exercise.isValidName("bench press"));
        assertTrue(Exercise.isValidName("push-ups"));
        assertTrue(Exercise.isValidName("bench-press"));

    }

    @Test
    public void isValidSets() {
        // invalid sets
        assertFalse(Exercise.isValidSets(0)); // zero sets
        assertFalse(Exercise.isValidSets(-1)); // negative sets

        // valid sets
        assertTrue(Exercise.isValidSets(1));
        assertTrue(Exercise.isValidSets(10));
    }

    @Test
    public void isValidReps() {
        // invalid reps
        assertFalse(Exercise.isValidReps(0)); // zero reps
        assertFalse(Exercise.isValidReps(-1)); // negative reps

        // valid reps
        assertTrue(Exercise.isValidReps(1));
        assertTrue(Exercise.isValidReps(20));
    }

    @Test
    public void isValidBreakBetweenSets() {
        // invalid break
        assertFalse(Exercise.isValidBreakBetweenSets(-1)); // negative break

        // valid break
        assertTrue(Exercise.isValidBreakBetweenSets(0));
        assertTrue(Exercise.isValidBreakBetweenSets(60));
    }

    @Test
    public void equals() {
        Exercise exercise = new Exercise("Exercise", 1, 1, 0);

        // same values -> returns true
        assertEquals(exercise, new Exercise("Exercise", 1, 1, 0));

        // same object -> returns true
        assertEquals(exercise, exercise);

        // null -> returns false
        assertNotEquals(null, exercise);

        // different types -> returns false
        assertNotEquals(true, exercise);

        // different name -> returns false
        assertNotEquals(exercise, new Exercise("Other Exercise", 1, 1, 0));

        // different sets -> returns true (name is used for equality)
        assertEquals(exercise, new Exercise("Exercise", 2, 1, 0));

        // different reps -> returns true (name is used for equality)
        assertEquals(exercise, new Exercise("Exercise", 1, 2, 0));

        // different break -> returns true (name is used for equality)
        assertEquals(exercise, new Exercise("Exercise", 1, 1, 30));
    }
}
