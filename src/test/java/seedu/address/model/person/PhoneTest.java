package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("+193121534")); // with '+' symbol
        assertFalse(Phone.isValidPhone("(193)121534")); // with '(' and ')' symbol
        assertFalse(Phone.isValidPhone("193-121534")); // with '-' symbol

        // valid phone numbers
        assertTrue(Phone.isValidPhone("1")); // one digit
        assertTrue(Phone.isValidPhone("12")); // one digit
        assertTrue(Phone.isValidPhone("12345678901234567890")); // twenty digits
        assertTrue(Phone.isValidPhone("123456789012345678901")); // twenty-one digits
    }

    @Test
    public void equals() {
        Phone phone = new Phone("88888888");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("88888888")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("98888888")));
    }

    @Test
    public void isMatch() {
        Phone phone = new Phone("92345678");

        // Exact match -> returns true
        assertTrue(phone.isMatch("92345678"));

        // Substring partial word -> returns true
        assertTrue(phone.isMatch("9234"));

        // Additional whitespace
        assertTrue(phone.isMatch(" 92345678\n"));

        // Substring mismatch
        assertFalse(phone.isMatch("invalid"));

        // Different type
        assertFalse(phone.isMatch(1));
    }
}
