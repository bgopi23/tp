package seedu.address.model.person.predicates;

import javafx.util.Pair;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person.PersonAttribute;

import static java.util.Objects.requireNonNull;

/**
 * Tests that a {@code Person}'s {@code HeightMap} contains a height within a range specified by a Pair.
 */
public class HeightMapContainsHeightRangePredicate extends SearchPredicate<Pair<Float, Float>> {
    /**
     * Construct a predicate to test that a {@code Person}'s {@code HeightMap}
     * contains a height within a range specified by a Pair.
     *
     * @param range range to test against
     */
    public HeightMapContainsHeightRangePredicate(Pair<Float, Float> range) {
        super(range, PersonAttribute.HEIGHT);
        requireNonNull(range);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("heightmap", this.getSearchValue()).toString();
    }
}
