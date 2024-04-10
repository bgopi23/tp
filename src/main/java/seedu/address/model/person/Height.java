package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import javafx.util.Pair;

/**
 * Represents a Person's height in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Height extends Attribute<Float> {
    public static final Float HEIGHT_MAX_VALUE = 5000f;

    public static final String MESSAGE_JSON_EMPTY_HEIGHT = "Height value specified in JSON cannot be an empty string.";
    public static final String MESSAGE_CONSTRAINTS =
            "Height value can only be a number between 0 and 5000 (inclusive).";
    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";

    /**
     * Constructs a {@code height}.
     *
     * @param height A height.
     */
    public Height(Float height) {
        super(height);
        requireNonNull(height);
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(VALIDATION_REGEX) && Float.valueOf(test) <= HEIGHT_MAX_VALUE;
    }

    /**
     * Determine if the height value stored is within the range specified in heightRange.
     * Returns true if specified value is within heightRange.
     *
     * @param heightRange Range of height to check against.
     *
     * @return True if value is falls within heightRange, false otherwise.
     */
    @Override
    public boolean isMatch(Object heightRange) {
        if (!(heightRange instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) heightRange;

        if (!(pair.getKey() instanceof Float) || !(pair.getValue() instanceof Float)) {
            return false;
        }

        Float firstVal = (Float) pair.getKey();
        Float secondVal = (Float) pair.getValue();

        assert (secondVal - firstVal >= 0) : "Range should be more than or equals to zero."
                + "Should have been handled in Parser class";

        // if a client has no height value
        if (this.getValue() == 0f) {
            return false;
        }

        return (this.getValue() >= firstVal && this.getValue() <= secondVal);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    /**
     * Adds a heading for the height field.
     * Empty height values (i.e. 0f) will be formatted as "N/A" for better clarity.
     */
    public String getFormattedHeight() {
        if (this.getValue() == 0f) {
            return "Height: N/A";
        }
        return "Height: " + this.getValue().toString() + " cm";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null types as well.
        if (!(other instanceof Height)) {
            return false;
        }

        Height otherHeight = (Height) other;

        // Use the equals() method of the underlying attribute to compare values
        return this.getValue().equals(otherHeight.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }

    /**
     * Check if the value is valid (i.e. greater than 0).
     */
    public boolean isValid() {
        return this.getValue() != 0f;
    }
}
