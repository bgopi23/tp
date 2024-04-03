package seedu.address.model.person.predicates;

import seedu.address.model.person.Person;

/**
 * Tests that always returns true
 */
public class AlwaysTruePredicate extends SearchPredicate<String> {
    /**
     * Constructs a predicate to that is always true
     *
     */
    public AlwaysTruePredicate() {
        super("", null);
    }

    @Override
    public boolean test(Person person) {
        return true;
    }
}
