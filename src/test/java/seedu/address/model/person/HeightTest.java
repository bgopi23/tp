package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

public class HeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void equals() {
        Height height = new Height(92.5f);

        // same values -> returns true
        assertTrue(height.equals(new Height(92.5f)));

        // same object -> returns true
        assertTrue(height.equals(height));

        // null -> returns false
        assertFalse(height.equals(null));

        // different types -> returns false
        assertFalse(height.equals("hello"));

        // different values -> returns false
        assertFalse(height.equals(new Height(69.5f)));
    }


    @Test
    public void isMatch() {
        Height height = new Height(192.5f);

        // Exact range -> returns true
        assertTrue(height.isMatch(new Pair<Float, Float>(192.5f, 192.5f)));

        // Falls within range -> returns true
        assertTrue(height.isMatch(new Pair<Float, Float>(190f, 200f)));

        // Falls outside of range -> returns false
        assertFalse(height.isMatch(new Pair<Float, Float>(180f, 190f)));

        // Incorrect generic types -> returns false
        assertFalse(height.isMatch(new Pair<Object, Object>("foo", "bar")));
        assertFalse(height.isMatch(new Pair<Float, Object>(180f, "bar")));
        assertFalse(height.isMatch(new Pair<Object, Object>("foo", 180f)));

        // Null pair -> returns false
        assertFalse(height.isMatch(new Pair<Float, Float>(null, null)));

        // Either value is null -> returns false
        assertFalse(height.isMatch(new Pair<Float, Float>(190f, null)));
        assertFalse(height.isMatch(new Pair<Float, Float>(null, 200f)));
    }
}
