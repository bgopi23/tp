package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;

import javafx.util.Pair;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person.PersonAttribute;

/**
 * Tests that a {@code Person}'s latest weight value in {@code WeightMap}
 * contains a weight within a range specified by a Pair.
 */
public class WeightMapContainsWeightRangePredicate extends SearchPredicate<Pair<Float, Float>> {
    /**
     * Construct a predicate to test that a {@code Person}'s latest weight value in {@code WeightMap}
     *  * contains a weight within a range specified by a Pair.
     *
     * @param range range to test against
     */
    public WeightMapContainsWeightRangePredicate(Pair<Float, Float> range) {
        super(range, PersonAttribute.WEIGHT);
        requireNonNull(range);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("weightmap", this.getSearchValue()).toString();
    }
}
