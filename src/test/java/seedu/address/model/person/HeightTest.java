package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.height.Height;

public class HeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void equals() {
        Height height = new Height(182f);

        // same values -> returns true
        assertTrue(height.equals(new Height(182f)));

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
        Height height = new Height(182f);

        // Exact match -> returns true
        assertTrue(height.isMatch(new Height(182f)));

        // Just above match -> returns false
        assertFalse(height.isMatch(new Height(183f)));

        // Just below match -> returns false
        assertFalse(height.isMatch(new Height(181f)));

        // Negative input -> returns false
        assertFalse(height.isMatch(new Height(-182f)));

        // Incorrect type -> returns false
        assertFalse(height.isMatch("hello"));
    }
}
