package seedu.address.model.person.weight;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Attribute;

/**
 * Represents a Person's weight in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Weight extends Attribute<Float> {

    public static final String MESSAGE_CONSTRAINTS = "Weights can only take decimals (float)";
    public static final String VALIDATION_REGEX = "^([0-9]+([.][0-9]*)?|[.][0-9]+)?$";

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A weight.
     */
    public Weight(Float weight) {
        super(weight);
        requireNonNull(weight);
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    /**
     * Adds a heading for the weight field.
     * Empty weight values (i.e. 0f) will be formatted as "N/A" for better clarity.
     */
    public String getFormattedWeight() {
        if (this.getValue() == 0f) {
            return "N/A";
        }
        return "Weight: " + this.getValue().toString();
    }

    @Override
    public boolean isMatch(Object weightRange) {
        return (this.equals(weightRange));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null types as well.
        if (!(other instanceof Weight)) {
            return false;
        }

        Weight otherWeight = (Weight) other;

        // Use the equals() method of the underlying attribute to compare values
        return this.getValue().equals(otherWeight.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
