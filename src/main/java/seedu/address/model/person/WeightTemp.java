package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import javafx.util.Pair;

/**
 * Represents a Person's weightTemp in the address book.
 * Guarantees: immutable; is always valid.
 */
public class WeightTemp extends Attribute<Float> {

    public static final String MESSAGE_CONSTRAINTS = "WeightTemps can only take decimals (float)";
    public static final String VALIDATION_REGEX = "([0-9]+([.][0-9]*)?|[.][0-9]+)";

    /**
     * Constructs a {@code weightTemp}.
     *
     * @param weightTemp A weightTemp.
     */
    public WeightTemp(Float weightTemp) {
        super(weightTemp);
        requireNonNull(weightTemp);
    }

    /**
     * Returns true if a given string is a valid weightTemp.
     */
    public static boolean isValidWeightTemp(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Determine if the weightTemp value stored is within the range specified in weightTempRange.
     * Returns true if specified value is within weightTempRange.
     *
     * @param weightTempRange Range of weightTemp to check against.
     *
     * @return True if value is falls within weightTempRange, false otherwise.
     */
    @Override
    public boolean isMatch(Object weightTempRange) {
        if (!(weightTempRange instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) weightTempRange;

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
    public String toString() {
        return this.getValue().toString();
    }

    /**
     * Adds a heading for the weightTemp field.
     * Empty weightTemp values (i.e. 0f) will be formatted as "N/A" for better clarity.
     */
    public String getFormattedWeightTemp() {
        if (this.getValue() == 0f) {
            return "N/A";
        }
        return "WeightTemp: " + this.getValue().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null types as well.
        if (!(other instanceof WeightTemp)) {
            return false;
        }

        WeightTemp otherWeightTemp = (WeightTemp) other;

        // Use the equals() method of the underlying attribute to compare values
        return this.getValue().equals(otherWeightTemp.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
