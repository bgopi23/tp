package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.messages.AddressMessages.MESSAGE_CONSTRAINTS;
import static seedu.address.model.person.messages.AddressMessages.VALIDATION_REGEX;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidAddress(String)}
 */
public class Address extends Attribute<String> {

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        super(address);
        requireNonNull(address);
        if (!address.isEmpty()) {
            checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Determine if the address value stored is a match with a specified string.
     * Returns true if specified value is a substring of the address value stored.
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
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return this.getValue().equals(otherAddress.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
