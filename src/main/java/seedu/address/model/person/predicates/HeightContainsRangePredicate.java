package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;

import javafx.util.Pair;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Person.PersonAttribute;

/**
 * Tests that a {@code Person}'s {@code Height} falls within a range specified by a Pair.
 */
public class HeightContainsRangePredicate extends SearchPredicate<Pair<Float, Float>> {
    /**
     * Construct a predicate to test that a {@code Person}'s {@code Height} falls within a range specified by a Pair.
     *
     * @param range range to test against
     */
    public HeightContainsRangePredicate(Pair<Float, Float> range) {
        super(range, PersonAttribute.HEIGHT);
        requireNonNull(range);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("height", this.getSearchValue()).toString();
    }

    @Override
    public boolean test(Person person) {
        Pair<Float, Float> searchRange = getSearchValue();

        if (searchRange.getKey() == 0f && searchRange.getValue() == 0f) {
            return !person.getAttribute(PersonAttribute.HEIGHT).isMatch(getSearchValue());
        } else {
            return person.getAttribute(PersonAttribute.HEIGHT).isMatch(getSearchValue());
        }
    }
}
