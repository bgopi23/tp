package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.weight.Weight;

public class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void equals() {
        Weight weight = new Weight(182f);

        // same values -> returns true
        assertTrue(weight.equals(new Weight(182f)));

        // same object -> returns true
        assertTrue(weight.equals(weight));

        // null -> returns false
        assertFalse(weight.equals(null));

        // different types -> returns false
        assertFalse(weight.equals("hello"));

        // different values -> returns false
        assertFalse(weight.equals(new Weight(69.5f)));
    }


    @Test
    public void isMatch() {
        Weight weight = new Weight(182f);

        // Exact match -> returns true
        assertTrue(weight.isMatch(new Weight(182f)));

        // Just above match -> returns false
        assertFalse(weight.isMatch(new Weight(183f)));

        // Just below match -> returns false
        assertFalse(weight.isMatch(new Weight(181f)));

        // Negative input -> returns false
        assertFalse(weight.isMatch(new Weight(-182f)));

        // Incorrect type -> returns false
        assertFalse(weight.isMatch("hello"));
    }
}
