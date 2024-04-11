package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.messages.PhoneMessages.EXPECTED_FORMAT_REGEX;
import static seedu.address.model.person.messages.PhoneMessages.MESSAGE_CONSTRAINTS;
import static seedu.address.model.person.messages.PhoneMessages.REQUIRED_REGEX;
/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone extends Attribute<String> {

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        super(phone);
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(REQUIRED_REGEX);
    }

    /**
     * Determines if the phone value stored is of an expected format.
     *
     * @return true if the phone number is of an expected format, false otherwise.
     */
    public boolean isExpectedFormat() {
        String phoneNumber = this.getValue();

        if (!phoneNumber.isEmpty()) {
            return phoneNumber.matches(EXPECTED_FORMAT_REGEX);
        } else {
            return false;
        }
    }

    /**
     * Determine if the phone value stored is a match with a specified string.
     * Returns true if specified value is a substring of the phone value stored.
     *
     * @param otherValue Other value to check against
     * @return True if specified value is a match, False otherwise
     */
    @Override
    public boolean isMatch(Object otherValue) {
        if (!(otherValue instanceof String)) {
            return false;
        }

        String other = (String) otherValue;

        return this.getValue().trim().toLowerCase().contains(other.trim().toLowerCase());
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return this.getValue().equals(otherPhone.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }

}
