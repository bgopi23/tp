package seedu.address.model.height;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.function.Predicate;

import javafx.util.Pair;
import seedu.address.model.person.Attribute;

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
     * Returns an immutable height map, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    @Override
    public NavigableMap<LocalDateTime, Height> getValue() {
        return Collections.unmodifiableNavigableMap(super.getValue());
    }

    /**
     * Determine if the height values stored falls within the range specified.
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

        if (!(pair.getKey() instanceof Height) || !(pair.getValue() instanceof Height)) {
            return false;
        }

        Pair<Height, Height> heightRange = (Pair<Height, Height>) otherValue;

        assert (heightRange.getKey().getValue() - heightRange.getValue().getValue() >= 0)
                : "Range should be more than or equals to zero. Should have been handled in Parser class";

        return this.getValue().values().stream().anyMatch(isInRange(heightRange));
    }

    private static Predicate<Height> isInRange(Pair<Height, Height> range) {
        return value -> (value.getValue() >= range.getKey().getValue())
                && (value.getValue() <= range.getValue().getValue());
    }

    /**
     * Determine if the heightMap contains the specified height.
     *
     * @param otherValue Height to check against
     * @return True if heightMap contains the specified height, false otherwise
     */
    public boolean contains(Object otherValue) {
        if (!(otherValue instanceof Height)) {
            return false;
        }

        Height height = ((Height) otherValue);

        return this.getValue().containsValue(height);
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
