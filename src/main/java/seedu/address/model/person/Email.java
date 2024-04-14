package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.messages.EmailMessages.MESSAGE_CONSTRAINTS;
import static seedu.address.model.person.messages.EmailMessages.VALIDATION_REGEX;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email extends Attribute<String> {

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        super(email);
        requireNonNull(email);
        if (!email.isEmpty()) {
            checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Determines if the email value stored is a match with a specified string.
     * Returns true if specified value is a substring of the email value stored.
     *
     * @param otherValue Other value to check against
     *
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return this.getValue().equals(otherEmail.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
