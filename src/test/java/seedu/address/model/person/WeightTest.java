package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

public class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new seedu.address.model.person.weight.Weight(null));
    }

    @Test
    public void equals() {
        seedu.address.model.person.weight.Weight weight = new seedu.address.model.person.weight.Weight(182f);

        // same values -> returns true
        assertTrue(weight.equals(new seedu.address.model.person.weight.Weight(182f)));

        // same object -> returns true
        assertTrue(weight.equals(weight));

        // null -> returns false
        assertFalse(weight.equals(null));

        // different types -> returns false
        assertFalse(weight.equals("hello"));

        // different values -> returns false
        assertFalse(weight.equals(new seedu.address.model.person.weight.Weight(69.5f)));
    }


    @Test
    public void isMatch() {
        Weight weight = new Weight(92.5f);

        // Exact range -> returns true
        assertTrue(weight.isMatch(new Pair<Float, Float>(92.5f, 92.5f)));

        // Falls within range -> returns true
        assertTrue(weight.isMatch(new Pair<Float, Float>(90f, 100f)));

        // Falls outside of range -> returns false
        assertFalse(weight.isMatch(new Pair<Float, Float>(80f, 90f)));

        // Incorrect generic types -> returns false
        assertFalse(weight.isMatch(new Pair<Object, Object>("foo", "bar")));
        assertFalse(weight.isMatch(new Pair<Float, Object>(80f, "bar")));
        assertFalse(weight.isMatch(new Pair<Object, Object>("foo", 80f)));

        // Null pair -> returns false
        assertFalse(weight.isMatch(new Pair<Float, Float>(null, null)));

        // Either value is null -> returns false
        assertFalse(weight.isMatch(new Pair<Float, Float>(90f, null)));
        assertFalse(weight.isMatch(new Pair<Float, Float>(null, 100f)));
    }
}
