package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NavigableMap;

import javafx.util.Pair;

/**
 * Represents a map of date-height values in the address book belonging to a Person.
 */
public class HeightMap extends Attribute<NavigableMap<LocalDateTime, Height>> {
    /**
     * Constructs a {@code HeightMap}
     *
     * @param heightMap Set of valid heights
     */
    public HeightMap(NavigableMap<LocalDateTime, Height> heightMap) {
        super(heightMap);
        requireNonNull(heightMap);
    }

    /**
     * Returns a {@code LocalDateTime} that is rounded down to the nearest second.
     */
    public static LocalDateTime getTimeOfExecution() {
        return LocalDateTime.now().withNano(0);
    }

    /**
     * Returns an immutable height map, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    @Override
    public NavigableMap<LocalDateTime, Height> getValue() {
        return Collections.unmodifiableNavigableMap(super.getValue());
    }

    /**
     * Determines if the height values stored falls within the range specified.
     * Returns true if any values in the height map falls within the specified range.
     *
     * @param otherValue Other value to check against
     *
     * @return True if there exists a height value that falls within the range, false otherwise.
     */
    @Override
    public boolean isMatch(Object otherValue) {
        if (!(otherValue instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) otherValue;

        if (!(pair.getKey() instanceof Float) || !(pair.getValue() instanceof Float)) {
            return false;
        }

        Pair<Float, Float> heightRange = (Pair<Float, Float>) otherValue;

        assert (heightRange.getValue() - heightRange.getKey() >= 0)
                : "Range should be more than or equals to zero. Should have been handled in Parser class";

        Float latestHeight = this.getValue().lastEntry().getValue().getValue();
        return (latestHeight >= heightRange.getKey()) && (latestHeight <= heightRange.getValue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HeightMap)) {
            return false;
        }

        HeightMap otherMap = (HeightMap) other;
        return this.getValue().equals(otherMap.getValue());
    }

    public String toString() {
        return this.getValue().toString();
    }
}
