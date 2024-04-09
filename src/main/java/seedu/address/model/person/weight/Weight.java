package seedu.address.model.person.weight;

import static java.util.Objects.requireNonNull;

import javafx.util.Pair;
import seedu.address.model.person.Attribute;

/**
 * Represents a Person's weight in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Weight extends Attribute<Float> {

    public static final String MESSAGE_CONSTRAINTS = "Weight value can only be a positive number.";
    public static final String MESSAGE_CONSTRAINTS_DATE = "Date value is invalid. Should follow the format "
        + "YYYY-MM-DDTHH:mm:ss, e.g. 2024-03-27T10:15:30";
    public static final String MESSAGE_JSON_KEY_NOT_FOUND = "Key value not found in JSON file.";
    public static final String MESSAGE_JSON_EMPTY_WEIGHT = "Weight value specified in JSON cannot be an empty string.";

    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";

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
     * Determine if the weight value stored is within the range specified in weightRange.
     * Returns true if specified value is within weightRange.
     *
     * @param weightRange Range of weight to check against.
     *
     * @return True if value is falls within weightRange, false otherwise.
     */
    @Override
    public boolean isMatch(Object weightRange) {
        if (!(weightRange instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) weightRange;

        if (!(pair.getKey() instanceof Float) || !(pair.getValue() instanceof Float)) {
            return false;
        }

        Float firstVal = (Float) pair.getKey();
        Float secondVal = (Float) pair.getValue();

        assert (secondVal - firstVal >= 0) : "Range should be more than or equals to zero."
                + "Should have been handled in Parser class";

        return (this.getValue() >= firstVal && this.getValue() <= secondVal);
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
