package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Person.PersonAttribute;

/**
 * Defines a Predicate in which a {@code Person}'s is to be searched by.
 */
public abstract class SearchPredicate<T extends Object> implements Predicate<Person> {
    private final T searchValue;
    private final PersonAttribute attribute;

    protected SearchPredicate(T searchValue, PersonAttribute attribute) {
        this.searchValue = searchValue;
        this.attribute = attribute;
    }

    /**
     * Gets the search value of this predicate
     *
     * @return Search value of this predicate
     */
    protected T getSearchValue() {
        return this.searchValue;
    }

    @Override
    public boolean test(Person person) {
        String attributeString = person.getAttribute(this.attribute).toString();

        if (searchValue.toString().isEmpty()) {
            return !attributeString.isEmpty();
        } else {
            return person.getAttribute(this.attribute).isMatch(this.searchValue);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchPredicate<?>)) {
            return false;
        }

        SearchPredicate<?> otherSearchPredicate = (SearchPredicate<?>) other;
        return this.searchValue.equals(otherSearchPredicate.searchValue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchValue", this.searchValue).toString();
    }
}
