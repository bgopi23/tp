package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's height in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Height extends Attribute<Float> {

    public static final String MESSAGE_CONSTRAINTS = "Heights can only take decimals (float)";
    public static final String VALIDATION_REGEX = "([0-9]+([.][0-9]*)?|[.][0-9]+)";

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
        return test.matches(VALIDATION_REGEX);
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
            return "N/A";
        }
        return "Height: " + this.getValue().toString();
    }

    @Override
    public boolean isMatch(Object heightRange) {
        return (this.equals(heightRange));
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
}
