package seedu.address.model.person.height;

import seedu.address.model.person.Attribute;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's height in the address book.
 * Guarantees: immutable; is always valid.
 */
public class HeightEntry extends Attribute<Map.Entry<LocalDateTime, Height>> {

    /**
     * Constructs a {@code HeightEntry}.
     *
     * @param heightEntry A height entry containing the date of the recorded height.
     */
    public HeightEntry(Map.Entry<LocalDateTime, Height> heightEntry) {
        super(heightEntry);
        requireNonNull(heightEntry);
    }

    /**
     * Returns a {@code LocalDateTime} that is rounded down to the nearest second.
     */
    public static LocalDateTime getTimeOfExecution() {
        return LocalDateTime.now().withNano(0);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
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
        if (!(other instanceof HeightEntry)) {
            return false;
        }

        HeightEntry otherHeight = (HeightEntry) other;

        // Use the equals() method of the underlying attribute to compare values
        return this.getValue().equals(otherHeight.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
